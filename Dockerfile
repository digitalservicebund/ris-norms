FROM gradle:9.5.1-jdk21 AS backend

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

FROM cgr.dev/chainguard/jre@sha256:c8ce958eaff439d84c6f23bb4b0f1ce2911c64f7d2ffadffe8d5be58466cb31f
COPY --from=backend /backend/build/libs/ris-norms-backend-*.jar /app/app.jar
ENV spring.cloud.bootstrap.enabled=true
EXPOSE 8080
ENTRYPOINT ["java","-Xmx1750M","-XX:+HeapDumpOnOutOfMemoryError","-XX:HeapDumpPath=/etc/heap-dumps/heap-dump.bin","-jar","/app/app.jar"]
