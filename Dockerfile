# build stage
FROM maven:3.9.8-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY src /app/src

RUN mvn clean install -DskipTests


# run stage
FROM openjdk:21-jdk-slim

WORKDIR /app

ARG JAR_FILE=target/*.jar

COPY --from=builder /app/${JAR_FILE} restgourmet.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/restgourmet.jar"]
