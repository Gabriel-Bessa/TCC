FROM maven:3.8.3-openjdk-17 as builder
WORKDIR /
COPY . .
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY --from=builder /notify-service-api/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]