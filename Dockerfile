#Create a oss docker images
FROM openjdk:8-jre-alpine
MAINTAINER qijunbo <qijb@sunwayworld.com> 
LABEL name="oss" version="1.0"
COPY build/libs/*.jar  /oss.jar
COPY src/main/resources/application-docker.yml /config/application-prod.yml
COPY src/main/resources/application.yml /config/application.yml
EXPOSE 8080
VOLUME /app-log /config /data
ENV JAVA_OPTS=" -server -Xms256m -Xmx1024m -XX:MaxPermSize=256m "
#ENTRYPOINT docker-entrypoint.sh
CMD  java ${JAVA_OPTS} -jar oss.jar