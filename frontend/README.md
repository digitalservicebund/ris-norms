# Frontend

The frontend is the main entry point for users of _RIS norms_.

# Development

## Prerequisites

- Node.js (with a `.node-version` file) for simplified setup using [`nodenv`](https://github.com/nodenv/nodenv)

## Quick-Start

- `npm i` fetches all dependencies
- `npm run dev` starts the application. By default on [local port 5173](http://localhost:5173). You will also need a running [backend](../backend/README.md).
- `npm run test` runs the tests once
- `npm run test:watch` runs the tests and automatically re-runs if something changes
- `npm run test:e2e` runs the E2E tests (requires a running frontend and backend)
- `npm run coverage` compiles a coverage report via `v8`
- `npm run style:check` does linting and formatting
- `npm run style:fix` will try to fix linting and formatting issues
- `npm run build` builds the app
- `npm run histoire` shows the frontend components in isolation

## E2E Tests

Make sure the backend and frontend run [here](../DEVELOPING.md#how-to-run-locally).

Install Browsers

```bash
npx --yes playwright install --with-deps chromium firefox msedge
```

Let Playwright know where to connect to by using the .env file:

```bash
cp .env.local.example .env.local
```

Run the E2E Test

```bash
npm run test:e2e
```

Run just 1 specific test:

```bash
npm run test:e2e -- [testfile]
```

Debug an e2e test:

```bash
npm run test:e2e -- [testfile] --debug
```

Run a specific browser e2e test:

```bash
npm run test:e2e -- --project chromium --repeat-each 1
npm run test:e2e -- --project firefox --repeat-each 1
npm run test:e2e -- --project msedge --repeat-each 1
```

Alternatively the [DEVELOPING.md](../DEVELOPING.md#how-to-run-locally) also explains how to run the e2e-tests inside a
docker container.

## Icons

All icons in the [Google Material](https://icon-sets.iconify.design/ic) sets can be used.

To make the icon available in your code:

- Find and select the icon
- Copy the name (e.g. ic/baseline-upload-file)
- Use the copied icon name in your import statement using the `~icons/ic` prefix followed by the name. Example:
  `import UploadFileOutlineRounded from "~icons/ic/baseline-upload-file"`
