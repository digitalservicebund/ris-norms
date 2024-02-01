FROM node:20.10.0 AS builder
WORKDIR /usr/src/app
COPY . .
RUN npm install && npm run build

FROM cgr.dev/chainguard/nginx@sha256:5b3a76533b2a6bac1b836fb72c39de1673b03c65043d8b1cfbf9c3b0e8266b67
COPY --from=builder /usr/src/app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/nginx.default.conf
