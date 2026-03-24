FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests && \
    cp target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]