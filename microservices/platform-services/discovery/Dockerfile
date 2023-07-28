FROM openjdk:11-jre-slim

ENV MODULE_NAME="discovery"
ENV VERSION="1.0"
ENV LABEL_DESCRIPTION="Docker image for Eureka '${MODULE_NAME}' microservice"
ENV JAR_NAME_TO_RUN="discovery.jar"
ENV PORT_TO_EXPOSE="8761"
ENV VOLUME_PATH="/tmp"

VOLUME ${VOLUME_PATH}

LABEL version=${VERSION}
LABEL description=${DESCRIPTION}

COPY target/${JAR_NAME_TO_RUN} .

EXPOSE ${PORT_TO_EXPOSE}

ENTRYPOINT ["java", "-jar", "discovery.jar"]