FROM openjdk:17-oracle
WORKDIR /usr/src/app
COPY note/target/note-0.0.1-SNAPSHOT.jar .
COPY note/src/main/resources/application.properties .
RUN sed -i 's/localhost/host.docker.internal/g' application.properties
CMD ["java", "-jar", "note-0.0.1-SNAPSHOT.jar"]
EXPOSE 8010