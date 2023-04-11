#
# Build stage
#
# FROM maven:3.8.6-openjdk-18-slim AS build
# COPY src /home/app/src
# COPY pom.xml /home/app
# RUN mvn -f /home/app/pom.xml clean package -DskipTests

#
# Package stage
#
# FROM eclipse-temurin:18-jdk-alpine
# ARG JAR_FILE=target/*.jar
# COPY --from=build /home/app/${JAR_FILE} /usr/local/lib/app.jar
# ENTRYPOINT ["java","-jar", "-Dspring.datasource.url=jdbc:mysql://${DATABASE_HOST}:${DATABASE_PORT}/${DATABASE_NAME}", "/usr/local/lib/app.jar"]

FROM eclipse-temurin:18-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "-Dspring.datasource.url=jdbc:mysql://${DATABASE_HOST}:${DATABASE_PORT}/${DATABASE_NAME}", "/app.jar"]
