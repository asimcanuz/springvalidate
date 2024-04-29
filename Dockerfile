# Base image
FROM adoptopenjdk:17-jre-hotspot

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/securityvalidate.jar /app/securityvalidate.jar

# Expose the port your application runs on
EXPOSE 9090

# Command to run the application
CMD ["java", "-jar", "securityvalidate.jar"]
