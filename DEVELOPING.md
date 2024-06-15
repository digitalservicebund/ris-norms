# Development / Tech Notes

Here's what to know from a tech perspective.

**Security note: Do not forget to run the `lefthook` setup below, including the setup of `talisman`. This ensures that secrets are caught before reaching the remote repository.**

# The Modules

Information on the modules can be found in their respective folder's README files:

- [`./frontend/README.md`](./frontend/README.md)
- [`./backend/README.md`](./backend/README.md)
- [`./ldml-extension/README.md`](./backend/README.md)

# The Main Repository

## Architecture Decision Records

[Architecture decisions](https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions)
are kept in the `docs/adr` directory.

For adding new records install the

- [`adr-tools`](https://github.com/npryce/adr-tools) package (e.g. via [`brew`](https://formulae.brew.sh/formula/adr-tools))

See https://github.com/npryce/adr-tools for information on how to use `adr-tools` usage.

## Git Hooks

The repository contains Git hooks which support

- committing only properly formatted source code, not breaking the build
- writing commit messages that follow some convention (wrt. the merits of having a convention , cf. this [article](https://chris.beams.io/posts/git-commit/))
- preventing accidentally pushing secrets and sensitive information

### Git Hooks Setup

In order to make use of the repository's Git hooks,

- [`Lefthook`](https://github.com/evilmartians/lefthook)

needs to be installed, which, in turn, makes use of the following CLI tools:

- [`talisman`](https://thoughtworks.github.io/talisman/docs) - scans for secrets
- [`gh`](https://github.com/cli/cli) - check CI status (optional)

Once these tools are available, install the hooks via

```bash
lefthook install
```

## Slack notifications

Opt in to CI posting notifications for failing jobs to a particular Slack channel by setting a repository secret
with the name `SLACK_WEBHOOK_URL`, containing a url for [Incoming Webhooks](https://api.slack.com/messaging/webhooks).

# How to run locally

Run dependencies from the root of the project:
```bash
docker compose up -d postgres14 redis
```

Run from `./backend`:
```bash
./gradlew bootRun
```
More info on the backend part [here](./backend/README.md).

Run from `./frontend`:
```bash
npm i
npm run dev
```
More info on the frontend part [here](./frontend/README.md).

Visit: [http://localhost:5173](http://localhost:5173)

## Run using Docker

With Docker being installed (Compose Plugin needed) run following to start all containers:
```bash
docker compose up -d
```

Visit: [http://localhost:8080](http://localhost:8080)


Watch the state by either of:
```bash
docker container ls
docker compose ls
```

To stop them:
```bash
docker compose down
```

# Testing

## Unit and Integration tests

Checkout the Frontend section [here](./frontend/README.md#quick-start) and for backend [here](./backend/README.md#tests).

## Run E2E Tests with Playwright inside Docker
Be aware: This wipes your local database:
```bash
docker compose stop

docker container prune -f

docker volume rm ris-norms_postgres14-data

docker compose build

docker compose up -d

cd frontend

docker build -t ris-norms-playwright -f DockerfilePlaywright .

docker run --name ris-norms-playwright -it --rm \
-e E2E_BASE_URL="http://nginx:8080" \
-e TZ="Europe/Berlin" \
--network ris-norms_default \
-v $(pwd)/test-results:/usr/src/app/test-results \
ris-norms-playwright
```
Screenshots for failed tests are stored in `./frontend/test-results/`.
