# TypeScript + Vite Application Template

[![Pipeline](https://github.com/digitalservicebund/typescript-vite-application-template/actions/workflows/pipeline.yml/badge.svg)](https://github.com/digitalservicebund/typescript-vite-application-template/actions/workflows/pipeline.yml)
[![Scan](https://github.com/digitalservicebund/typescript-vite-application-template/actions/workflows/scan.yml/badge.svg)](https://github.com/digitalservicebund/typescript-vite-application-template/actions/workflows/scan.yml)
[![Secrets Check](https://github.com/digitalservicebund/typescript-vite-application-template/actions/workflows/secrets-check.yml/badge.svg)](https://github.com/digitalservicebund/typescript-vite-application-template/actions/workflows/secrets-check.yml)

Bootstrap a TypeScript application with Vite dev server and Continuous Delivery

## Prerequisites

### Node.js

We aim to use the current active [LTS version of nodejs](https://nodejs.dev/en/about/releases/), which is V18 at the time of writing. There is a `.node-version` file to simplify setup using [nodenv](https://github.com/nodenv/nodenv).

### Testing

For E2E and a11y testing with [Playwright](https://playwright.dev/docs/intro) you will need to install the supported browsers:

```bash
npx playwright install
npx playwright install msedge
```

### Git Hooks

For the provided Git hooks you will need to install [lefthook](https://github.com/evilmartians/lefthook/blob/master/docs/full_guide.md) (git hook manager) and [talisman](https://thoughtworks.github.io/talisman/docs) (secrets scanner):

```bash
brew install lefthook talisman
./run.sh init
```

The following hooks are specified in the `lefthook.yml`:

- `commitlint` - write [conventional commit messages](https://chris.beams.io/posts/git-commit/)
- `lint` - avoid commiting code violating linting rules
- `format` - avoid commiting wrongly formatted code

Before pushing, the following checks are additionally ran:

- `licenses-audit` - uses `license-checker` to verify depency licenses
- `secrets-audit` - avoid accidental pushes of [secrets and sensitive information](https://thoughtworks.github.io/talisman/)

### security.txt

This template contains a [security.txt](https://securitytxt.org/), where you probably want to update the expiration date. The following entries may also be added:

```
Policy: https://raw.githubusercontent.com/digitalservicebund/<<REPO_NAME>>/main/SECURITY.md
Canonical: https://<<PROJECT_URL>>/.well-known/security.txt
```

## Development

### Dev server

The project uses [Vite](https://vitejs.dev/guide/) to provide a bundler-less dev server, available via `npm run dev`.

### Testing

The application has

- unit tests (using [Jest](https://jestjs.io/docs/getting-started))
- end-to-end tests (using [Playwright](https://playwright.dev/docs/intro))
- accessibility tests (using [Axe](https://github.com/abhinaba-ghosh/axe-playwright#readme) and [Playwright](https://playwright.dev/docs/intro))

**Test commands**

- Run unit tests: `npm test`
- Run unit tests with watcher: `npm test -- --watch`
- Gather coverage: `npm run coverage`
- Run E2E tests: `npm run test:e2e`
- Run a11y tests: `npm run test:a11y`

### Code quality checks (linting & formatting)

The project uses [ESLint](https://eslint.org/docs/latest/) for linting and [Prettier](https://prettier.io/docs/en/). for formatting.

**Commands**

- Check style: `npm run style:check`
- Autofix style issues: `npm run style:fix`
- Check lint: `npm run lint:check`
- Autofix lint issues: `npm run lint:fix`

## Architecture Decision Records

The `docs/adr` directory contains [architecture decisions](https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions).
For adding new records the [adr-tools](https://github.com/npryce/adr-tools) command-line tool is useful but not strictly necessary:

```bash
brew install adr-tools
```
