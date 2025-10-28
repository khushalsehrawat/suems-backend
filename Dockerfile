# ---------- Build Stage ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml first to leverage Docker cache
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copy source and build application
COPY src ./src
RUN mvn clean package -DskipTests -B

# ---------- Run Stage ----------
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
