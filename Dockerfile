# Use Maven to build the application, then run the jar with OpenJDK
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/springboot-api-0.0.1-SNAPSHOT.jar app.jar
COPY springboot-api/src/main/resources/application-dev.properties ./application-dev.properties
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]
