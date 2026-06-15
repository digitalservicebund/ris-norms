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

FROM cgr.dev/chainguard/jre@sha256:aec912a20efff9133df2abd4c29f7ad57644db8c1a07a2fccc967967a0926784
COPY --from=backend /backend/build/libs/ris-norms-backend-*.jar /app/app.jar
ENV spring.cloud.bootstrap.enabled=true
EXPOSE 8080
ENTRYPOINT ["java","-Xmx1750M","-XX:+HeapDumpOnOutOfMemoryError","-XX:HeapDumpPath=/etc/heap-dumps/heap-dump.bin","-jar","/app/app.jar"]
