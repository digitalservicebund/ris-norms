FROM node:22.14.0 AS frontend

WORKDIR /frontend
COPY frontend .
RUN npm ci
RUN --mount=type=secret,id=SENTRY_AUTH_TOKEN \
    if [ -f /run/secrets/SENTRY_AUTH_TOKEN ]; then \
        SENTRY_AUTH_TOKEN=$(cat /run/secrets/SENTRY_AUTH_TOKEN); \
    fi; \
    npm run build

FROM gradle:8.13-jdk21 AS backend

COPY backend /backend
COPY LegalDocML.de /LegalDocML.de

WORKDIR /backend

RUN mkdir -p src/main/resources/static
COPY --from=frontend /frontend/dist src/main/resources/static
RUN --mount=type=secret,id=SENTRY_DNS \
    --mount=type=secret,id=SENTRY_AUTH_TOKEN \
    if [ -f /run/secrets/SENTRY_DNS ] && [ -f /run/secrets/SENTRY_AUTH_TOKEN ]; then \
        SENTRY_DNS=$(cat /run/secrets/SENTRY_DNS) \
        SENTRY_AUTH_TOKEN=$(cat /run/secrets/SENTRY_AUTH_TOKEN); \
    fi; \
    ./gradlew build --profile -x integrationTest -x test -x spotlessCheck

FROM cgr.dev/chainguard/jre:latest
COPY --from=backend /backend/build/libs/ris-norms-backend-*.jar /app/app.jar
ENV spring.cloud.bootstrap.enabled=true
EXPOSE 8080
ENTRYPOINT ["java","-Xmx500M","-XX:+HeapDumpOnOutOfMemoryError","-XX:HeapDumpPath=/etc/heap-dumps/heap-dump.bin","-jar","/app/app.jar"]
