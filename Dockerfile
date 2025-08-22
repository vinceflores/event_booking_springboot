# Use a JDK base image
FROM eclipse-temurin:24.0.1_9-jdk-ubi9-minimal

LABEL authors="vinceflores"

WORKDIR /app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]