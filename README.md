<h1 align='center'>
  🥄 REST Gourmet 🔪
</h1>

[![Last Release](https://img.shields.io/github/v/release/bartolek153/restgourmet?logo=github&label=latest&style=flat-square)](https://github.com/bartolek153/rest-gourmet/releases)
[![Build](https://img.shields.io/github/actions/workflow/status/bartolek153/restgourmet/image-build.yml?branch=main&logo=github&style=flat-square)](https://nightly.link/bartolek153/restgourmet/workflows/pipeline/master)

\<description>

1. [Features](#Features)
2. [Installation](#installation)
3. [Running in development](#rundev)
4. [Documentation](#documentation)
5. [TODO](#todo)

## Features

* CRUD Operations
* User Management (with authentication)
* Employees Attendance and Activities Control
* User Management
* Inventory Tracking
* Brazillian NFe Receipts Management

## Security

* IAM
* Auditing
* JWT

## Installation

```bash
```

## Running in development

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

## TODO

* Features
  * Add multitenancy
  * Implement cancellation tokens
* Code Improvement
  * Add unit tests
  * Remove excessive model lombok annotations
  * Test if redis cache is working
  * Use migrations
* CI/CD
  * Create image build pipeline
  * Add image signing
* Docs
  * Add screenshots
    - Dark x Light Theme
  * Work on openapi docs
* Bugs
  * authProvider won't retry request after refreshing token
  * Default Spring Redis serializer won't recognize LocalDateTime values 

## Technologies used

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Vite](https://img.shields.io/badge/Vite-B73BFE?style=for-the-badge&logo=vite&logoColor=FFD62E)
![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/rabbitmq-%23FF6600.svg?&style=for-the-badge&logo=rabbitmq&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white)






## Modules

* Common Data
* Master Data
* Inventory Handling
* Restaurant Management
* User Management
* Employee Management
* Financials

## Usecases of Kafka

* Order Processing Pipeline
* Real-time Order Status Updates
* Payment Processing
* low-stock-alerts
* NFe processing
* Menu generation

## Tools

* Kafka
* Redis (auth, session)
* Postgres
* AWS
* Docker
