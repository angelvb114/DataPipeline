FROM openjdk:8
ADD target/pipeline-0.0.1-SNAPSHOT.jar pipeline-0.0.1-SNAPSHOT.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "pipeline-0.0.1-SNAPSHOT.jar"]