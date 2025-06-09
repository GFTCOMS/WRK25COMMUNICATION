# WRK25 Communication Microservice

## Overview
The WRK25 Communication Microservice is a Spring Boot application designed to handle notifications for an e-commerce system. It processes events from other services and creates notifications for users about product stock changes, order status updates, and other important events...

## Features
- Notification management for users
- Integration with RabbitMQ for event-driven architecture
- Multiple notification types:
  - Low stock notifications
  - Product stock change notifications
  - Product restock notifications
  - Order status change notifications
  - Cart update notifications
  - User account notifications
- REST API for notification management
- Persistence with PostgreSQL database

## Architecture

### System Architecture
```mermaid
flowchart TB
    subgraph "External Services"
        ProductService[Product Service]
        OrderService[Order Service]
        CartService[Cart Service]
        UserService[User Service]
    end

    subgraph "WRK25 Communication Service"
        RabbitMQ[RabbitMQ]
        Consumers[Message Consumers]
        NotificationService[Notification Service]
        DB[(PostgreSQL)]
        API[REST API]
    end

    ProductService -->|Stock Events| RabbitMQ
    OrderService -->|Order Events| RabbitMQ
    CartService -->|Cart Events| RabbitMQ
    UserService -->|User Events| RabbitMQ
    RabbitMQ -->|Events| Consumers
    Consumers -->|Process| NotificationService
    NotificationService -->|Store| DB
    NotificationService -->|Expose| API
    Client[Client Applications] -->|HTTP| API
```

### Domain Model
```mermaid
classDiagram
    class User {
        -UUID id
        -String email
        -String name
    }

    class Cart {
        -UUID id
        -UUID userId
        -List~CartItem~ items
    }

    class CartItem {
        -UUID productId
        -int quantity
        -BigDecimal price
    }

    class Notification {
        -NotificationId id
        -LocalDateTime createdAt
        -UserId userId
        -String message
        -boolean important
    }

    class NotificationId {
        -UUID id
    }

    class UserId {
        -UUID id
    }

    class NotificationDTO {
        +UUID id
        +LocalDateTime createdAt
        +UUID userId
        +String message
        +Boolean important
    }

    class NotificationFactory {
        +createLowStockNotification(UserId, ProductDTO)
        +createOrderStatusChangedNotification(UserId, OrderId, String)
        +createProductChangedNotification(UserId, ProductDTO)
        +createProductRestockNotification(UserId, ProductDTO)
        +createCartUpdateNotification(UserId, CartDTO)
        +createUserAccountNotification(UserId, String)
    }

    class NotificationRepository {
        <<interface>>
        +List~Notification~ findAllByUserId(UserId)
        +boolean existsById(NotificationId)
        +Notification save(Notification)
        +void deleteById(NotificationId)
        +void setImportant(NotificationId, boolean)
    }

    class NotificationCreatedEvent {
        +Notification notification
    }

    class NotificationEventPublisher {
        +publish(NotificationCreatedEvent)
    }

    Notification --> NotificationId
    Notification --> UserId
    NotificationFactory --> Notification : creates
    NotificationRepository --> Notification : manages
    NotificationDTO --> Notification : represents
    NotificationCreatedEvent --> Notification : contains
    NotificationEventPublisher --> NotificationCreatedEvent : publishes
    User --> UserId : has
    Cart --> User : belongs to
    Cart --> CartItem : contains
```

### Message Flow
```mermaid
sequenceDiagram
    participant ProductService as Product Service
    participant OrderService as Order Service
    participant CartService as Cart Service
    participant UserService as User Service
    participant RabbitMQ as RabbitMQ
    participant Consumer as Message Consumer
    participant Factory as Notification Factory
    participant Service as Notification Service
    participant Repository as Notification Repository
    participant DB as Database

    ProductService->>RabbitMQ: Send Stock Event (product.stock.low, etc.)
    OrderService->>RabbitMQ: Send Order Event (order.state.changed, etc.)
    CartService->>RabbitMQ: Send Cart Event (cart.updated, etc.)
    UserService->>RabbitMQ: Send User Event (user.registered, user.updated, etc.)
    RabbitMQ->>Consumer: Deliver Event
    Consumer->>Factory: Create Notification
    Factory->>Service: Save Notification
    Service->>Repository: Persist Notification
    Repository->>DB: Store in Database
    Note over Repository,DB: Event is published after successful save
```

## Setup and Installation

### Prerequisites
- Java 17
- Maven
- Docker and Docker Compose (for containerized deployment)
- PostgreSQL
- RabbitMQ

### Environment Variables
The application requires the following environment variables:

```
# RabbitMQ Configuration
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest
RABBITMQ_VHOST=/

# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/notifications
DB_USERNAME=postgres
DB_PASSWORD=postgres

# Application Configuration
PORT=8080
```

### Running Locally
1. Clone the repository
2. Set up the required environment variables
3. Run the application:
   ```
   ./mvnw spring-boot:run
   ```

### Running with Docker
1. Build the Docker image:
   ```
   docker build -t wrk25-communication .
   ```
2. Run the container:
   ```
   docker run -p 8080:8080 \
     -e RABBITMQ_HOST=rabbitmq \
     -e RABBITMQ_PORT=5672 \
     -e RABBITMQ_USERNAME=guest \
     -e RABBITMQ_PASSWORD=guest \
     -e RABBITMQ_VHOST=/ \
     -e DB_URL=jdbc:postgresql://postgres:5432/notifications \
     -e DB_USERNAME=postgres \
     -e DB_PASSWORD=postgres \
     wrk25-communication
   ```

### Cloud Deployment
The application is deployed on Google Cloud Platform using Cloud Run. The deployment is automated through GitHub Actions workflows.

#### Production Environment
The production environment is accessible at:
```
https://coms-microservice-899231617905.europe-southwest1.run.app
```

#### Deployment Process
1. Changes pushed to the main branch trigger the CI/CD pipeline
2. After successful tests, the application is built as a Docker image
3. The image is pushed to Google Artifact Registry
4. The application is deployed to Cloud Run with public access

#### RabbitMQ Service
The application uses CloudAMQP for RabbitMQ messaging in the cloud environment. The RabbitMQ management interface is accessible at:
```
https://kangaroo-01.rmq.cloudamqp.com
```

## API Documentation

### Endpoints

#### Get Notifications
```
GET https://coms-microservice-899231617905.europe-southwest1.run.app/api/notifications/{userId}
```
Returns all notifications for a specific user.

#### Delete Notification
```
DELETE https://coms-microservice-899231617905.europe-southwest1.run.app/api/notifications/{id}
```
Deletes a notification by its ID.

#### Update Notification Importance
```
PATCH https://coms-microservice-899231617905.europe-southwest1.run.app/api/notifications/{id}
```
Updates the importance status of a notification.

## Monitoring

The application exposes metrics for Prometheus at the `/actuator/prometheus` endpoint. A sample Prometheus configuration is provided in the `prometheus.yml` file in the project root.

The Prometheus configuration is set up to scrape metrics from the Cloud Run deployment at:
```
https://coms-microservice-899231617905.europe-southwest1.run.app/actuator/prometheus
```

To use this configuration:
1. Install Prometheus (https://prometheus.io/download/)
2. Use the provided `prometheus.yml` file
3. Start Prometheus with this configuration
4. Access the Prometheus UI to view metrics

## Development

### Project Structure
The project follows a clean architecture pattern:

- **Domain Layer**: Core business logic and entities
- **Application Layer**: Use cases and application services
- **Infrastructure Layer**: External interfaces, repositories, and messaging

### Testing
Run the tests with:
```
./mvnw test
```

## License
[MIT License](LICENSE)
