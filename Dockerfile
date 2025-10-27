# ---------- Build stage ----------
FROM openjdk:21-jdk-slim AS build
WORKDIR /app

# Copy Maven wrapper + config first to cache dependencies
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

# Now copy the source and build
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# ---------- Run stage ----------
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
