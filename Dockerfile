FROM eclipse-temurin:21.0.3_9-jdk as jdk-build

WORKDIR /usr/local/src
COPY . .

RUN ./mvnw clean package -DskipTests -Pci

FROM eclipse-temurin:21.0.3_9-jre-alpine

RUN mkdir /opt/app
WORKDIR /opt/app

COPY --from=jdk-build /usr/local/src/backend/target/backend.jar .

CMD ["java", "-jar", "/opt/app/backend.jar"]
