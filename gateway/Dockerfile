FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the pre-built JAR file into the container
COPY ./*.jar app.jar

# Expose the port your application runs on
EXPOSE 8090

# Command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]