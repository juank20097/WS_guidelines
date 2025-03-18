#Construction of the .jar file

FROM maven:3.8.5-openjdk-17-slim AS build

# Definition of Metadata

LABEL version="1.0"
LABEL name="Spring Boot - Construction"

#Change timezone to server

ENV TZ=America/Guayaquil
USER root
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

#Progress

WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean package -DskipTests

#Execution of the .jar file
FROM openjdk:17-jdk-slim

LABEL version="1.0"
LABEL name="Spring Boot - Deploy"

#Change timezone to server

ENV TZ=America/Guayaquil
USER root
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /app

COPY --from=build /app/target/*.jar /app/ws_lineamientos.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "ws_lineamientos.jar"]