# Use an official OpenJDK 21 image as base
FROM eclipse-temurin:21-jdk AS build

# Set the working directory
WORKDIR /app

# Copy the build files
COPY . .

# If using Maven:
RUN ./mvnw clean package -DskipTests

# If using Gradle, comment the line above and uncomment this one:
# RUN ./gradlew build -x test

# Use a smaller runtime image for production
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy only the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar
# If Gradle:
# COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","app.jar"]
