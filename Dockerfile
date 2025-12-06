FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace/app

COPY pom.xml ./
RUN mvn -q -B dependency:go-offline

COPY src ./src
RUN mvn -q -B package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
