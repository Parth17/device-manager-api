FROM maven:3.9.5-amazoncorretto-21 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine-jdk
COPY --from=build /app/target/device-manager-api-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]