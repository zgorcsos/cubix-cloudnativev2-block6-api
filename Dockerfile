FROM eclipse-temurin:21-jre
ENV JAVA_OPTS=""
RUN mkdir /opt/app && chown 1001 -R /opt/app
USER 1001
WORKDIR /opt/app
COPY --chown=1001 target/*.jar app.jar
ENTRYPOINT [ "sh", "-c" ]
CMD ["exec java $JAVA_OPTS -jar app.jar"]
