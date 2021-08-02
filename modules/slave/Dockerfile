#
#FROM openjdk:18-jdk
#
#RUN mkdir -p -m 777  /application/
#RUN chown 1001:0 /application
#
##RUN mkdir -p -m 777  /aplication_storage/
##RUN chown 1001:0 /aplication_storage
##RUN chown 1001:0 /aplication_storage/
#
#COPY target/*.jar /application
#
#WORKDIR /application
#
#CMD java -jar *.jar

FROM ubuntu:latest

RUN apt update && apt install -y curl
RUN curl -sSL https://get.docker.com/ | sh
RUN apt update && apt install -y openjdk-16-jre

RUN mkdir -p -m 777  /application/
RUN chown 1001:0 /application

COPY target/*.jar /application

WORKDIR /application

CMD java -jar *.jar
