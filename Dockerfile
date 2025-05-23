FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY target/*.jar /app/api.jar

EXPOSE 8080

CMD [ "java", "-jar", "api.jar" ]
