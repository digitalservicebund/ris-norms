# Frontend

The frontend is the main entry point for users of _RIS norms_.

# Development

## Prerequisites

- Node.js (with a `.node-version` file) for simplified setup using [`nodenv`](https://github.com/nodenv/nodenv)

## Quick Start

- `npm i` fetches all dependencies
- `npm run dev` starts the application. By default on [local port 5173](http://localhost:5173). You will also need a running [backend](../backend/README.md).
- `npm run test` runs the tests once
- `npm run test:watch` runs the tests and automatically re-runs if something changes
- `npm run test:e2e` runs all browser-based tests (E2E tests, accessibility tests and smoke tests, requires a running frontend and backend)
  - `npm run test:e2e -- --ui` opens the Playwright UI
  - `npm run test:browsers` runs E2E tests in Chrome, Firefox, and Edge (excluding smoke and a11y tests)
  - `npm run test:a11y` runs [accessibility tests](#accessibility-tests-a11y)
  - `npm run test:smoke` runs [smoketests](#smoke-tests)
- `npm run coverage` compiles a coverage report via `v8`
- `npm run typecheck` runs type checking through TypeScript
- `npm run style:check` does linting and formatting
- `npm run style:fix` will try to fix linting and formatting issues
- `npm run build` builds the app
- `npm run preview` previews the app (requires a build first)

## E2E Tests

Make sure the backend and frontend are [running locally](../DEVELOPING.md#how-to-run-locally).

Then, install the browsers:

```bash
npx --yes playwright install --with-deps chromium firefox msedge
```

Let Playwright know where to connect to by using the `.env` file:

```bash
cp .env.local.example .env.local
```

Run E2E tests:

```bash
npm run test:browsers
```

Run just 1 specific test:

```bash
npm run test:browsers -- [testfile]
```

Debug a test:

```bash
npm run test:browsers -- [testfile] --debug
```

Run E2E tests in a specific browser:

```bash
npm run test:e2e -- --project chromium --repeat-each 1
npm run test:e2e -- --project firefox --repeat-each 1
npm run test:e2e -- --project msedge --repeat-each 1
```

Alternatively, the [DEVELOPING.md](../DEVELOPING.md#how-to-run-locally) also explains how to run the E2E tests inside a docker container.

## Accessibility Tests (a11y)

The project includes automated accessibility (a11y) testing using [axe-core](https://github.com/dequelabs/axe-core). Accessibility tests also use Playwright, so everything about E2E tests also applies.

Make sure the frontend is running locally before executing the tests. To run the accessibility tests:

```bash
npm run test:a11y
```

## Smoke Tests

We use smoke testing for superficially checking certain functionality on a deployed version of our system, usually staging. Smoke tests focus on areas where the live system significantly differs from the local or E2E setup such as how the frontend is served (served by Vite locally, but served by Spring in production).

If you need to run smoke tests locally, first set `E2E_SMOKETEST_BASE_URL`, `E2E_SMOKETEST_USERNAME`, and `E2E_SMOKETEST_PASSWORD` in your environment. It will be picked up automatically if you add it to your `.env.local` (also see `.env.local.example`).

Then run:

```
node run test:smoke
```

## Icons

All icons in the [Google Material](https://icon-sets.iconify.design/ic) sets can be used.

To make the icon available in your code:

- Find and select the icon
- Copy the name (e.g. ic/baseline-upload-file)
- Use the copied icon name in your import statement using the `~icons/ic` prefix followed by the name. Example:
  `import UploadFileOutlineRounded from "~icons/ic/baseline-upload-file"`
