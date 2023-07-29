FROM openjdk:11-jre-slim

ENV MODULE_NAME="courier"
ENV VERSION="1.0"
ENV LABEL_DESCRIPTION="Docker image for '${MODULE_NAME}' microservice"
ENV JAR_NAME_TO_RUN="courier-app.jar"
ENV PORT_TO_EXPOSE="8083"
ENV VOLUME_PATH="/tmp"

VOLUME ${VOLUME_PATH}

LABEL version=${VERSION}
LABEL description=${DESCRIPTION}

COPY target/${JAR_NAME_TO_RUN} .

EXPOSE ${PORT_TO_EXPOSE}

#RUN apt-get update && apt-get install -y curl

ENTRYPOINT ["java", "-jar", "courier-app.jar"]