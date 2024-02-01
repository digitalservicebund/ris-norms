FROM node:21.4.0 AS builder
WORKDIR /usr/src/app
COPY . .
RUN npm install && npm run build

FROM cgr.dev/chainguard/nginx@sha256:486f0c246219d0f643982cf52c4727b65f51b8268274ea38d8f8d46d87bed27f
COPY --from=builder /usr/src/app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/nginx.default.conf
