FROM eclipse-temurin:11-jre-alpine as builder
WORKDIR /var/lib/caradvertiser-service
COPY ./target/caradvertiser-server*[^stubs].jar caradvertiser-service.jar
RUN java -Djarmode=layertools -jar caradvertiser-service.jar extract

FROM eclipse-temurin:11-jre-alpine
ARG DEPENDENCY=/var/lib/caradvertiser-service
COPY --from=builder ${DEPENDENCY}/dependencies/ ./
COPY --from=builder ${DEPENDENCY}/spring-boot-loader/ ./
COPY --from=builder ${DEPENDENCY}/snapshot-dependencies/ ./
COPY --from=builder ${DEPENDENCY}/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]