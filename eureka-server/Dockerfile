#FROM openjdk:17-jdk-slim
#WORKDIR /app
#COPY target/eureka-server-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8087
#ENTRYPOINT ["java", "-jar", "app.jar"]


# Stage 1: Build the application
FROM maven:3.9.4-eclipse-temurin-17 as builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/eureka-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "app.jar"]

