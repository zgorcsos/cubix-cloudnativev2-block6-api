FROM eclipse-temurin:21-jre
ENV JAVA_OPTS=""
RUN mkdir /opt/app && chown 1001 -R /opt/app
USER 1001
WORKDIR /opt/app
ADD --chown=1001 https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.33.0/opentelemetry-javaagent.jar opentelemetry.jar
COPY --chown=1001 target/*.jar app.jar
ENTRYPOINT [ "sh", "-c" ]
CMD ["exec java -javaagent:/opt/app/opentelemetry.jar $JAVA_OPTS -jar app.jar"]
