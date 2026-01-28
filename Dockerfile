FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml ./
COPY service-client/pom.xml service-client/pom.xml
COPY service-transaction/pom.xml service-transaction/pom.xml
COPY service-transaction-processor/pom.xml service-transaction-processor/pom.xml

RUN mvn -q -DskipTests dependency:go-offline

COPY . .

ARG MODULE=service-client

RUN mvn -q -DskipTests -pl ${MODULE} -am package


FROM eclipse-temurin:17-jre
WORKDIR /app

ARG MODULE=service-client

COPY --from=build /app/${MODULE}/target/*.jar /app/app.jar

EXPOSE 8081

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]