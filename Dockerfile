#Create a oss docker images
FROM openjdk:8-jre-alpine
MAINTAINER qijunbo <qijb@sunwayworld.com> 
LABEL name="oss" version="1.0"
COPY build/libs/*.jar  /oss.jar
COPY docker-entrypoint.sh /usr/local/bin/
COPY src/main/resources/application.ftl.yml /config/application.yml
ENV CONTXTPATH=oss APP_DB_HOST=db APP_DB_PORT=3306 APP_DB_USER=root APP_DB_PASSWORD=sunway123### APP_DATABASE=oss  
EXPOSE 8080
VOLUME /app-log /config /data
ENV JAVA_OPTS=" -server -Xms256m -Xmx1024m -XX:MaxPermSize=256m "
ENTRYPOINT docker-entrypoint.sh
#CMD  java ${JAVA_OPTS} -jar oss.jar
