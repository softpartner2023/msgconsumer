# Step 1 : Maven build
FROM maven:3.9.5-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2 : the image for the app execution
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the JAR from the builder
COPY --from=builder /app/target/*.jar msgconsumer.jar

# Port
EXPOSE 8080

# executing the app
ENTRYPOINT ["java", "-jar", "msgconsumer.jar"]