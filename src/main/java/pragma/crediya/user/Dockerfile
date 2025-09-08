# Etapa 1: Construcción con Gradle
FROM gradle:8.5-jdk17 AS build
WORKDIR /app

# Copiamos el código fuente
COPY . .

# Construimos el jar del microservicio user
RUN gradle :bootJar --no-daemon

# Etapa 2: Imagen final ligera
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copiamos el jar generado
COPY --from=build /app/build/libs/*.jar app.jar

# Definimos el puerto dinámico
ENV SERVER_PORT=8081
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${SERVER_PORT}"]
