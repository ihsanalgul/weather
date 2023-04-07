FROM openjdk:19-jdk-slim-buster AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN ./mvnw dependency:resolve

COPY src src

RUN ./mvnw package

FROM openjdk:19-jdk-slim-buster
WORKDIR weather
COPY --from=build target/*.jar weather.jar
ENTRYPOINT ["java", "-jar", "weather.jar"]