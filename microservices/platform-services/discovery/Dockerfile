FROM openjdk:11-jre-slim

WORKDIR /app

#VOLUME /tmp

LABEL version="1.0"
LABEL description="Eureka Disvocery service server."

COPY target/discovery.jar .

EXPOSE 8761

CMD ["java", "-jar", "discovery.jar"]