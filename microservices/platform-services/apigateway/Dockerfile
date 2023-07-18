FROM openjdk:11-jre-slim

ENV MODULE_NAME="apigateway"
ENV VERSION="1.0"
ENV LABEL_DESCRIPTION="Docker image for Zull '${MODULE_NAME}' microservice"
ENV JAR_NAME_TO_RUN="apigateway.jar"
ENV PORT_TO_EXPOSE="8762"
ENV VOLUME_PATH="/tmp"

VOLUME ${VOLUME_PATH}

LABEL version=${VERSION}
LABEL description=${DESCRIPTION}

COPY target/${JAR_NAME_TO_RUN} .

EXPOSE ${PORT_TO_EXPOSE}

ENTRYPOINT ["java", "-jar", "apigateway.jar"]