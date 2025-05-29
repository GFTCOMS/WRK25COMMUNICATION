# Contributing to WRK25 Communication Microservice

Thank you for considering contributing to the WRK25 Communication Microservice! This document provides guidelines and instructions for contributing to this project.

## Code of Conduct

Please be respectful and considerate of others when contributing to this project. We aim to foster an inclusive and welcoming community.

## How to Contribute

### Reporting Bugs

If you find a bug, please create an issue in the issue tracker with the following information:

- A clear and descriptive title
- Steps to reproduce the bug
- Expected behavior
- Actual behavior
- Screenshots or logs (if applicable)
- Environment details (OS, Java version, etc.)

### Suggesting Enhancements

If you have an idea for an enhancement, please create an issue with the following information:

- A clear and descriptive title
- A detailed description of the enhancement
- Any relevant examples or mockups
- Why this enhancement would be useful

### Pull Requests

1. Fork the repository
2. Create a new branch for your feature or bugfix
3. Make your changes
4. Write or update tests as necessary
5. Ensure all tests pass
6. Submit a pull request

## Development Setup

### Prerequisites

- Java 17
- Maven
- Docker and Docker Compose (for local development)
- PostgreSQL
- RabbitMQ

### Local Development

1. Clone the repository
2. Set up the required environment variables (see README.md)
3. Run the application:
   ```
   ./mvnw spring-boot:run
   ```

### Testing

Run the tests with:
```
./mvnw test
```

## Coding Standards

- Follow the existing code style
- Write clear, descriptive commit messages
- Include comments where necessary
- Write unit tests for new features
- Update documentation as needed

## Architecture

The project follows a clean architecture pattern:

- **Domain Layer**: Core business logic and entities
- **Application Layer**: Use cases and application services
- **Infrastructure Layer**: External interfaces, repositories, and messaging

When making changes, please respect this architecture and place new code in the appropriate layer.

## Documentation

Please update the documentation when making changes:

- Update the README.md if necessary
- Update or create Mermaid diagrams in the docs directory
- Add comments to your code

## Review Process

All pull requests will be reviewed by the maintainers. We may suggest changes or improvements before merging.

Thank you for your contributions!