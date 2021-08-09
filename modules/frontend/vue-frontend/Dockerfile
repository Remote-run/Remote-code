FROM node:16 AS builder

COPY ./ /application
WORKDIR /application
RUN mkdir node_modules

RUN --mount=type=cache,target=/application/node_modules,id=nodemod yarn add @vue/cli-service
RUN --mount=type=cache,target=/application/node_modules,id=nodemod yarn install
RUN --mount=type=cache,target=/application/node_modules,id=nodemod yarn build



FROM nginx AS prod
COPY --from=builder ./application/dist /www/app
COPY nginx.conf /etc/nginx/conf.d/app.conf
