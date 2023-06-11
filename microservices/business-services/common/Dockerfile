FROM openjdk:11-jre-slim

VOLUME /tmp

LABEL version="1.0"
LABEL description="Archaius server with configuration for services."

COPY target/common.jar .

#EXPOSE 8080

CMD ["java", "-jar", "common.jar"]


