# WRK25 Communication Microservice Documentation

This directory contains documentation for the WRK25 Communication Microservice.

## Overview

The WRK25 Communication Microservice is a Spring Boot application designed to handle notifications for an e-commerce system. It processes events from other services (Product Service, Order Service, Cart Service, and User Service) and creates notifications for users about product stock changes, order status updates, cart updates, user account changes, and other important events.

## Architecture Diagrams

The architecture of the system is documented in the following Mermaid diagrams:

- [System Architecture](system-architecture.mmd) - Shows the overall system architecture and component interactions
- [Domain Model](domain-model.mmd) - Shows the domain model and relationships between entities
- [Message Flow](message-flow.mmd) - Shows the sequence of messages in the system

## Setup and Usage

For detailed setup instructions and usage examples, please refer to the [README.md](../README.md) in the project root.

## Cloud Deployment

The application is deployed on Google Cloud Platform using Cloud Run. The deployment is automated through GitHub Actions workflows.

### Production Environment
The production environment is accessible at:
```
https://coms-microservice-899231617905.europe-southwest1.run.app
```

### RabbitMQ Service
The application uses CloudAMQP for RabbitMQ messaging in the cloud environment. The RabbitMQ management interface is accessible at:
```
http://wrk25uwuarios.dpdns.org:27028/#/queues
```

## API Documentation

The microservice exposes a REST API for notification management. The main endpoints are:

Swagger: https://coms-microservice-899231617905.europe-southwest1.run.app/swagger-ui/index.html

- `GET https://coms-microservice-899231617905.europe-southwest1.run.app/api/notifications/{userId}` - Get all notifications for a user
- `DELETE https://coms-microservice-899231617905.europe-southwest1.run.app/api/notifications/{id}` - Delete a notification
- `PATCH https://coms-microservice-899231617905.europe-southwest1.run.app/api/notifications/{id}` - Update a notification's importance status

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
