# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-24 AS builder

WORKDIR /app
COPY pom.xml /app/
COPY src /app/src
RUN mvn clean package

# Stage 2: Create runtime image
FROM eclipse-temurin:24-jre

WORKDIR /app
COPY --from=builder /app/target/reverse-words-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8090
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

#BUILD
#docker build -t jherzog89/reverse-words-service:v1 .

#RUN in same network
#docker network create my-app-network
#docker run --name postgres-container --network my-app-network -e POSTGRES_USER=testuser -e POSTGRES_PASSWORD=password -e POSTGRES_DB=testdb -p 5432:5432 -d postgres:latest
#docker run --name reverse-words-container --network my-app-network -p 8090:8090 jherzog89/reverse-words-service:v1
