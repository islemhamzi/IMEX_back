# Use official Java image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR
COPY target/Authentication-0.0.1-SNAPSHOT.jar /app/Authentication-0.0.1-SNAPSHOT.jar

# Expose port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "/app/Authentication-0.0.1-SNAPSHOT.jar"]
