FROM node:22.15.0 AS frontend

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

# pinning jre:latest from 2025-04-17 - last digest without the error of a missing truststore
FROM cgr.dev/chainguard/jre@sha256:6a20031eae40ce920143d21d8a4374f6a871000df6d53a6b34a063f0c8e29ccd
COPY --from=backend /backend/build/libs/ris-norms-backend-*.jar /app/app.jar
ENV spring.cloud.bootstrap.enabled=true
EXPOSE 8080
ENTRYPOINT ["java","-Xmx500M","-XX:+HeapDumpOnOutOfMemoryError","-XX:HeapDumpPath=/etc/heap-dumps/heap-dump.bin","-jar","/app/app.jar"]
