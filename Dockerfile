# Estágio 1: Build (Compilação)
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Estágio 2: Run (Execução)
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copia apenas o JAR gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]