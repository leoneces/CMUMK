# Use the official Maven image to build the application
# This image has both Maven and JDK 17 installed
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download the dependencies
COPY pom.xml .

# Download all necessary dependencies (it's more efficient to do this separately)
RUN mvn dependency:go-offline -B

# Copy the entire project
COPY . .

# Package the application
RUN mvn clean package -DskipTests

# Use a new image for running the application, with just JDK 17
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Set the entrypoint to run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]