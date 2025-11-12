# Stage 1: Build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render sets PORT automatically
ENV PORT=8080

# Expose (not required but nice for docs)
EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]
