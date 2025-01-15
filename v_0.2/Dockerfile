# Importación de JDK y copia de los archivos necesarios
FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src src

# Copiar el contenedor Maven
COPY mvnw .
COPY .mvn .mvn

# Establecer el permiso de ejecución para el contenedor Maven
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Etapa 2: Crear la imagen final de Docker usando OpenJDK 19
FROM openjdk:17-jdk-slim
VOLUME /tmp

# Copiar el JAR desde la etapa de compilación
COPY --from=build /app/target/v_0.2-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java" , "-jar" , "/app.jar" ]
EXPOSE 8080
#FROM ubuntu:latest AS build
#RUN apt-get update
#RUN apt-get install openjdk-17-jdk-slim -y
#
#FROM openjdk:17-jdk-slim
#ARG JAR_FILE=target/v_0.2-0.0.1-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]