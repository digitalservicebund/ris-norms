FROM gradle:9.2-jdk21 AS backend

COPY backend /backend
COPY LegalDocML.de /LegalDocML.de
COPY .git /.git

WORKDIR /backend

RUN --mount=type=secret,id=SENTRY_DSN \
        --mount=type=secret,id=SENTRY_AUTH_TOKEN \
        if [ -f /run/secrets/SENTRY_DSN ] && [ -f /run/secrets/SENTRY_AUTH_TOKEN ]; then \
            export SENTRY_DSN=$(cat /run/secrets/SENTRY_DSN) \
                   SENTRY_AUTH_TOKEN=$(cat /run/secrets/SENTRY_AUTH_TOKEN); \
        fi; \
    ./gradlew build --profile -x integrationTest -x test -x spotlessCheck

FROM cgr.dev/chainguard/jre@sha256:21e9aea108246b9c19724e4f6b64d8f8ad73da7219dbfdd88de0be7ae811ac3f
COPY --from=backend /backend/build/libs/ris-norms-backend-*.jar /app/app.jar
ENV spring.cloud.bootstrap.enabled=true
EXPOSE 8080
ENTRYPOINT ["java","-Xmx1750M","-XX:+HeapDumpOnOutOfMemoryError","-XX:HeapDumpPath=/etc/heap-dumps/heap-dump.bin","-jar","/app/app.jar"]
