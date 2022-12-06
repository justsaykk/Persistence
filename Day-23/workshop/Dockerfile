FROM maven:3-openjdk-18 AS builder
WORKDIR /app
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY src src
RUN mvn package -Dmaven.test.skip=true
FROM openjdk:18-jdk-oracle
WORKDIR /app
# Change the "workshop-0.0.1-SNAPSHOT.jar" and workshop23.jar as appropriate
COPY --from=builder /app/target/workshop-0.0.1-SNAPSHOT.jar workshop23.jar
ENV PORT=8080
ENTRYPOINT java -jar -Dserver.port=${PORT} workshop23.jar