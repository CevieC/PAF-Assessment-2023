FROM maven:3-eclipse-temurin-21 AS builder

WORKDIR /app

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21-jdk-bullseye

WORKDIR /app 

COPY --from=builder /app/target/day29bgg-0.0.1-SNAPSHOT.jar bggapp.jar

ENV PORT=8080 
ENV SPRING_DATA_MYSQL_USER=NOT_SET SPRING_DATA_MYSQL_PASSWORD=NOT_SET
ENV SPRING_DATA_MYSQL_DATABASE=NOT_SET SPRING_DATA_MYSQL_PORT=NOT_SET
ENV SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/bgg

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar assessment-0.0.1-SNAPSHOT.jar.jar