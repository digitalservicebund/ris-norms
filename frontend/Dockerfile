FROM node:20.12.2 AS builder
WORKDIR /usr/src/app
COPY . .
RUN npm install && npm run build

FROM cgr.dev/chainguard/nginx@sha256:f705448eb5a9939454a5bfe1a2b6a8afe4da18d8f7191ef62b883200baa4605a
COPY --from=builder /usr/src/app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/nginx.default.conf
