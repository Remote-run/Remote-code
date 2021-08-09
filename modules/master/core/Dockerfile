FROM maven:3.8.1-openjdk-11 AS build
COPY ./ /build_dir
WORKDIR /build_dir
RUN --mount=type=cache,id=appm2,target=/root/.m2 mvn clean install


FROM open-liberty:21.0.0.3-full-java11-openj9
ARG VERSION=1.0
ARG REVISION=SNAPSHOT


LABEL name="AUTH"
LABEL version="$VERSION-$REVISION"




USER root
RUN mkdir -p -m 777  /application_storage/shared_files
RUN chown 1001:0 /application_storage
RUN chown 1001:0 /application_storage/shared_files
USER 1001

COPY --chown=1001:0 core/src/main/liberty/config /config/
COPY --chown=1001:0 core/config/server.env /config/
COPY --from=build --chown=1001:0 /build_dir/core/target/resource_jars /opt/ol/wlp/usr/shared/resources/
COPY --from=build --chown=1001:0 /build_dir/core/target/core-1.0.war /config/apps


EXPOSE 9085 9448