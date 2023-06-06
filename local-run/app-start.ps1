.\mvnw package verify
if ($LastExitCode -eq 0) {
    java -Xmx512m "-Dserver.port=8082" `
    "-Dapi.message.url=http://localhost:8081" `
    -jar target\cloudnative-api-1.0.0-SNAPSHOT.jar
}
