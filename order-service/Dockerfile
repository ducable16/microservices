
FROM maven:3.9.9-eclipse-temurin-23 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src
# COPY ./src/main/resources/application.properties ./config/application.properties

RUN mvn clean package -DskipTests

FROM eclipse-temurin:23-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
