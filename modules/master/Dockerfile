

FROM open-liberty:21.0.0.3-full-java11-openj9
ARG VERSION=1.0
ARG REVISION=SNAPSHOT


LABEL name="AUTH"
LABEL version="$VERSION-$REVISION"

LABEL traefik.docker.network=remote_code_net
LABEL traefik.http.routers.master.rule="PathPrefix(`/api/app/`)"


USER root
RUN mkdir -p -m 777  /application_storage/shared_files
RUN chown 1001:0 /application_storage
RUN chown 1001:0 /application_storage/shared_files
USER 1001

COPY --chown=1001:0 src/main/liberty/config /config/
COPY --chown=1001:0 config/server.env /config/
COPY --chown=1001:0 target/resource_jars /opt/ol/wlp/usr/shared/resources/
COPY --chown=1001:0 target/master-1.0.war /config/apps

EXPOSE 9085 9448