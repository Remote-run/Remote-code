FROM open-liberty:21.0.0.3-full-java11-openj9
ARG VERSION=1.0
ARG REVISION=SNAPSHOT


LABEL name="AUTH"
LABEL version="$VERSION-$REVISION"

LABEL traefik.docker.network=fishapp_network
LABEL traefik.http.routers.auth.rule="PathPrefix(`/api/auth/`)"

ENV fishapp_system_url=http://remote-run.uials.no/

ENV http_admin_username="a"
ENV http_admin_password="a"

USER root
RUN mkdir -p -m 777  /aplication_storage/token_key
RUN chown 1001:0 /aplication_storage
RUN chown 1001:0 /aplication_storage/token_key
USER 1001

COPY --chown=1001:0 src/main/liberty/config /config/
COPY --chown=1001:0 ./config/server.env /config/
COPY --chown=1001:0 ./target/resource_jars /opt/ol/wlp/usr/shared/resources/
COPY --chown=1001:0 ./target/core-1.0.war /config/apps

EXPOSE 9085 9448