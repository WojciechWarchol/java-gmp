FROM openjdk:11-jre-slim

#VOLUME /tmp

WORKDIR /app

LABEL version="1.0"
LABEL description="Simple service application ONE."

COPY target/one.jar .

#EXPOSE 8080

CMD ["java", "-jar", "one.jar"]


