FROM node:16 AS builder

COPY ./ /application
WORKDIR /application
RUN yarn install
RUN yarn build

FROM nginx AS prod
COPY ./dist /www/app
COPY nginx.conf /etc/nginx/conf.d/app.conf
#COPY --from=builder ./application/dist /usr/local/apache2/htdocs/
