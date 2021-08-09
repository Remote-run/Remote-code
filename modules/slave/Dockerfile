FROM maven:3.8.1-openjdk-16 AS build
COPY ./ /build_dir
WORKDIR /build_dir
RUN --mount=type=cache,id=appm2,target=/root/.m2 mvn clean install


FROM ubuntu:latest
RUN apt update && apt install -y curl
RUN curl -sSL https://get.docker.com/ | sh
RUN apt update && apt install -y openjdk-16-jre

RUN mkdir -p -m 777  /application/
RUN chown 1001:0 /application

COPY --from=build /build_dir/target/*.jar /application

WORKDIR /application

CMD java -jar *.jar
