FROM openjdk:11-jre-slim

#VOLUME /tmp

WORKDIR /app

LABEL version="1.0"
LABEL description="Zuul API gateway server"

COPY target/apigateway.jar .

EXPOSE 8762

CMD ["java", "-jar", "apigateway.jar"]


