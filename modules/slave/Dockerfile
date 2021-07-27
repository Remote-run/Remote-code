
FROM openjdk:18-jdk

RUN mkdir -p -m 777  /application/
RUN chown 1001:0 /application

#RUN mkdir -p -m 777  /aplication_storage/
#RUN chown 1001:0 /aplication_storage
#RUN chown 1001:0 /aplication_storage/

COPY target/*.jar /application

WORKDIR /application

CMD java -jar *.jar
