FROM eclipse-temurin:17-jdk

COPY target/favourite-service-0.0.1-SNAPSHOT.jar favourite-service-0.0.1-SNAPSHOT.jar

ENTRYPOINT [ "java", "-jar", "favourite-service-0.0.1-SNAPSHOT.jar" ]