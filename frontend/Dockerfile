FROM cgr.dev/chainguard/nginx@sha256:3133414624e7849802e99f7579458e95e295b6f28fb80b30b18faec8d296f1e2
EXPOSE 8080
# Set working directory to nginx asset directory
WORKDIR /var/lib/nginx/html
# Remove default nginx static assets
# RUN rm -rf ./*
# Copy static assets from builder stage
COPY ./dist .