FROM node:24.4.1 AS frontend

WORKDIR /frontend
COPY frontend .
RUN npm ci
RUN --mount=type=secret,id=SENTRY_AUTH_TOKEN \
    if [ -f /run/secrets/SENTRY_AUTH_TOKEN ]; then \
        export SENTRY_AUTH_TOKEN=$(cat /run/secrets/SENTRY_AUTH_TOKEN); \
    fi; \
    npm run build

FROM gradle:8.14-jdk21 AS backend

COPY backend /backend
COPY LegalDocML.de /LegalDocML.de

WORKDIR /backend

RUN mkdir -p src/main/resources/static
COPY --from=frontend /frontend/dist src/main/resources/static
RUN --mount=type=secret,id=SENTRY_DSN \
        --mount=type=secret,id=SENTRY_AUTH_TOKEN \
        if [ -f /run/secrets/SENTRY_DSN ] && [ -f /run/secrets/SENTRY_AUTH_TOKEN ]; then \
            export SENTRY_DSN=$(cat /run/secrets/SENTRY_DSN) \
                   SENTRY_AUTH_TOKEN=$(cat /run/secrets/SENTRY_AUTH_TOKEN); \
        fi; \
    ./gradlew build --profile -x integrationTest -x test -x spotlessCheck

FROM cgr.dev/chainguard/jre@sha256:71c0dc29e7bf9b8565e2bb69b2ebebc01ecf71dcbed5a9bb4dec530e93cf992d
COPY --from=backend /backend/build/libs/ris-norms-backend-*.jar /app/app.jar
ENV spring.cloud.bootstrap.enabled=true
EXPOSE 8080
ENTRYPOINT ["java","-Xmx1750M","-XX:+HeapDumpOnOutOfMemoryError","-XX:HeapDumpPath=/etc/heap-dumps/heap-dump.bin","-jar","/app/app.jar"]
