FROM eclipse-temurin:11-jdk
ARG JAR_FILE=target/productos-service.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]