# FASE 1: Construcción - Usamos Maven para compilar el proyecto
FROM maven:3.8.6-jdk-17 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn package

# FASE 2: Imagen final - Solo incluye el JAR y el entorno de ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el .jar desde la fase de construcción
COPY --from=build /workspace/target/WRK25_COMMUNICATION-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Iniciar aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]