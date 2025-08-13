<h1 align='center'>
  🥄 REST Gourmet 🔪
</h1>

[![Last Release](https://img.shields.io/github/v/release/bartolek153/restgourmet?logo=github&label=latest&style=flat-square)](https://github.com/bartolek153/rest-gourmet/releases)
[![Build](https://img.shields.io/github/actions/workflow/status/bartolek153/restgourmet/image-build.yml?branch=main&logo=github&style=flat-square)](https://nightly.link/bartolek153/restgourmet/workflows/pipeline/master)

REST Gourmet is a comprehensive enterprise system designed to streamline operations for small businesses. It provides a robust set of features for inventory tracking, employee management, purchase and sales processing, financial management, and more, all within a modern, user-friendly interface.

## Table of Contents

1. [Features (WIP)](#features)
2. [System Architecture](#system-architecture)
3. [Technologies](#technologies-used)
4. [Modules](#modules)
5. [Installation](#installation)
6. [Running in Development](#running-in-development)
7. [Documentation](#documentation)
8. [Security](#security)
9. [Future Enhancements](#future-enhancements)
10. [Contributing](#contributing)

## Features (WIP)

- **User Management**: Comprehensive user authentication and authorization system
- **Inventory Tracking**: Real-time inventory management
- **Procurement Management**: Manage and control purchases
- **Sales Management**: Manage and control sales
- **Financial Management**: Track expenses, revenue, and generate financial reports
- **Responsive UI**: Modern, responsive interface built with React and Ant Design

## System Architecture

REST Gourmet follows a microservices-inspired architecture with a clear separation of concerns:

- **Backend**: Spring Boot REST API with modular design
- **Frontend**: React application using Refine framework and Ant Design
- **Database**: PostgreSQL for relational data storage
- **Caching**: Redis for performance optimization
- **Containerization**: Docker support for easy deployment

## Technologies Used

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Vite](https://img.shields.io/badge/Vite-B73BFE?style=for-the-badge&logo=vite&logoColor=FFD62E)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/rabbitmq-%23FF6600.svg?&style=for-the-badge&logo=rabbitmq&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white)

### Backend
- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Security
- Spring Cache with Redis
- PostgreSQL
- OpenAPI/Swagger

### Frontend
- React 18
- TypeScript
- Refine Framework
- Ant Design
- Vite

## Modules

REST Gourmet is organized into several functional modules:

### Common Data
Core data structures and shared functionality used across the application.

### Master Data
Management of fundamental business data like products, categories, and business partners.

### Inventory Handling
Comprehensive inventory management with tracking, receipts, and supplier integration.

### Restaurant Management
Table management, order processing, and kitchen operations.

### User Management
User authentication, authorization, and profile management.

### Employee Management
Employee records, attendance tracking, scheduling, and activity management.

### Financials
Financial transactions, reporting, and analysis.

### Project Management
Project tracking, task management, and resource allocation.

## Installation

### Prerequisites
- Java 17 or higher
- Node.js 16 or higher
- Yarn package manager
- PostgreSQL 14+
- Redis 7+
- Docker and Docker Compose (optional, for containerized setup)

### Using Docker Compose (Recommended)
1. Clone the repository:
   ```bash
   git clone https://github.com/bartolek153/restgourmet.git
   cd restgourmet
   ```

2. Start the services using Docker Compose:
   ```bash
   docker-compose up -d
   ```

3. The application will be available at:
   - Backend API: http://localhost:5274
   - Frontend UI: http://localhost:5173

### Manual Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/bartolek153/restgourmet.git
   cd restgourmet
   ```

2. Set up PostgreSQL:
   - Create a database named `app`
   - Configure username and password (default: postgres/password)

3. Set up Redis:
   - Ensure Redis is running on the default port (6379)

4. Build and run the backend:
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
   ```

5. Install and run the frontend:
   ```bash
   cd ui
   yarn install
   yarn dev
   ```

## Running in Development

### Starting REST API on [http://localhost:5274](http://localhost:5274)

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

### Starting UI on [http://localhost:5173/](http://localhost:5173/)

```bash
cd ui
yarn
yarn dev
```

## Documentation

### API Documentation
The REST API documentation is available through Swagger UI when running in development mode:
- http://localhost:5274/swagger-ui.html

### Database Schema
The database schema is defined in DBML format in the `docs/diagram.dbml` file, providing a clear overview of the data model and relationships.

### Architecture Diagram
A system architecture diagram is available at `docs/system_architecture.png`, illustrating the components and their interactions.

## Security

REST Gourmet implements several security features:

### Identity and Access Management (IAM)
- Role-based access control
- Fine-grained permissions
- User authentication and authorization

### JWT Authentication
- Secure token-based authentication
- Token refresh mechanism
- Cookie-based token storage

### Auditing
- Comprehensive audit logging
- Change tracking
- Security event monitoring

## Future Enhancements

The following features are planned for future releases:

### Features
- Multi-tenancy support
- Cancellation tokens implementation
- Langchain integration for AI-powered insights
- WhatsApp/Telegram integration
- Enhanced audit security for actions/changes
- Resizable sidebar in UI

### Code Improvements
- Comprehensive unit tests
- Streamlined Lombok annotations
- Database migrations
- Standardized UI components and patterns
- Improved mapper dependency injection

### CI/CD
- Enhanced image build pipeline
- Image signing for security

### Documentation
- Additional screenshots (Dark/Light Theme)
- Improved OpenAPI documentation

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request
