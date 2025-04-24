# Use an official OpenJDK runtime as the base image
FROM eclipse-temurin:17-jdk

# Set the working directory inside the container
WORKDIR /oandmtg

# Define a build argument for the JAR file
ARG JAR_FILE=target/*.jar

# Copy the JAR file into the container
COPY ${JAR_FILE} oandmtg.jar

# Expose the port
EXPOSE 8098

# Run the Eureka Server
ENTRYPOINT ["java", "-jar", "hrmstg.jar"]
