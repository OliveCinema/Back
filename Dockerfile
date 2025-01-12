# Base JRE 이미지
FROM eclipse-temurin:23-jre-alpine as base
WORKDIR /app

# Build 단계
FROM eclipse-temurin:23-jdk-alpine as build
WORKDIR /app

# New Relic 에이전트 다운로드 및 압축 해제
RUN mkdir -p /app/newrelic && \
    wget -q -O /app/newrelic/newrelic-java.zip https://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic-java.zip && \
    unzip -o /app/newrelic/newrelic-java.zip -d /app/newrelic && \
    rm /app/newrelic/newrelic-java.zip

# New Relic 파일 확인
RUN ls -l /app/newrelic/newrelic.jar || (echo "New Relic jar not found!" && exit 1)

# 소스 코드 복사 및 빌드
COPY . .
RUN chmod +x gradlew && ./gradlew build -x test

# Package 단계
FROM base
WORKDIR /app

# 애플리케이션 및 New Relic 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar
COPY --from=build /app/newrelic/newrelic.jar /app/newrelic.jar
COPY --from=build /app/newrelic/newrelic.yml /app/newrelic.yml

# 애플리케이션 실행 (New Relic 에이전트 포함)
ENTRYPOINT ["java", "-javaagent:/app/newrelic.jar", "-jar", "app.jar"]
