FROM openjdk:11-jre-slim

#VOLUME /tmp

WORKDIR /app

LABEL version="1.0"
LABEL description="Simple service application TWO."

COPY target/two.jar .

#EXPOSE 8080

CMD ["java", "-jar", "two.jar"]


