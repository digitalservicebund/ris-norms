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

## Vue 3 + TypeScript + Vite (from the vite/vue docs)

This template should help get you started developing with Vue 3 and TypeScript in Vite. The template uses Vue 3 `<script setup>` SFCs, check out the [script setup docs](https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup) to learn more.

### Recommended IDE Setup

- [VS Code](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur) + [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin).

### Type Support For `.vue` Imports in TS

TypeScript cannot handle type information for `.vue` imports by default, so we replace the `tsc` CLI with `vue-tsc` for type checking. In editors, we need [TypeScript Vue Plugin (Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.vscode-typescript-vue-plugin) to make the TypeScript language service aware of `.vue` types.

If the standalone TypeScript plugin doesn't feel fast enough to you, Volar has also implemented a [Take Over Mode](https://github.com/johnsoncodehk/volar/discussions/471#discussioncomment-1361669) that is more performant. You can enable it by the following steps:

1. Disable the built-in TypeScript Extension
   1. Run `Extensions: Show Built-in Extensions` from VSCode's command palette
   2. Find `TypeScript and JavaScript Language Features`, right click and select `Disable (Workspace)`
2. Reload the VSCode window by running `Developer: Reload Window` from the command palette.

## Setup Playwright for E2E Tests

Playbook:

- Make sure the backend runs [here](../DEVELOPING.md#how-to-run-locally).
- Make sure the frontend runs [here](../DEVELOPING.md#how-to-run-locally).
- Install Playwright
- Let Playwright know where to connect to by using the .env file: `cp .env.local.example .env.local`
- Run the E2E Test

## Icons

All icons in the [Google Material](https://icon-sets.iconify.design/ic) sets can be used.

To make the icon available in your code:

- Find and select the icon
- Copy the name (e.g. ic/baseline-upload-file)
- Use the copied icon name in your import statement using the `~icons/ic` prefix followed by the name. Example:
  `import UploadFileOutlineRounded from "~icons/ic/baseline-upload-file"`

## Prepare your system:

```bash
npx --yes playwright install --with-deps chromium firefox msedge
```

Run all tests:

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
npm run test:e2e -- --project edge --repeat-each 1
```
