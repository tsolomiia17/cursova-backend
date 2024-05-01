FROM maven:4.0.0-openjdk-22 AS build
COPY . .
RUN mvn clean package

FROM openjdk:22.0.1-jdk-slim
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]