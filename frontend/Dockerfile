FROM node:21.4.0 AS builder
WORKDIR /usr/src/app
COPY . .
RUN npm install && npm run build

FROM cgr.dev/chainguard/nginx@sha256:3133414624e7849802e99f7579458e95e295b6f28fb80b30b18faec8d296f1e2
COPY --from=builder /usr/src/app/dist /usr/share/nginx/html
## touching file
