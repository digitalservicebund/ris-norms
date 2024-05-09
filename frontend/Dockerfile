FROM node:20.13.1 AS builder
WORKDIR /usr/src/app
COPY . .
RUN npm install && npm run build

FROM cgr.dev/chainguard/nginx:latest
COPY --from=builder /usr/src/app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/nginx.default.conf
