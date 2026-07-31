# Pasos que sigue docker-compose para generar una imagen del backend
FROM maven:3.9-eclipse-temurin-17 AS build 
WORKDIR /app
COPY pom.xml .
# descargar dependencias
RUN mvn dependency:go-offline
#copiar codigo
COPY src ./src
#compilar y generar jar
RUN mvn clean package -DskipTests

# Generar imagen con el jar
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
