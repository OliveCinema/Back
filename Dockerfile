# Dockerfile
FROM eclipse-temurin:23-jre-alpine as base
WORKDIR /app

# Build stage
FROM eclipse-temurin:23-jdk-alpine as build
WORKDIR /app
COPY . .
RUN ./gradlew build -x test

# Package stage
FROM base
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
