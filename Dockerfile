# Base JRE 이미지
FROM eclipse-temurin:23-jre-alpine as base
WORKDIR /app

# Build 단계
FROM eclipse-temurin:23-jdk-alpine as build
WORKDIR /app

# 소스 코드 복사 및 gradlew 실행 권한 설정
COPY . .
RUN chmod +x gradlew && ./gradlew build -x test

# Package 단계
FROM base
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
COPY newrelic/newrelic.jar /app/newrelic.jar
ENTRYPOINT ["java", "-javaagent:/app/newrelic.jar", "-jar", "app.jar"]
