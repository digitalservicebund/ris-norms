# System Tests

End-to-end tests of the whole system with user flows via a web-browser.

## Development

### Prerequisites

- [npm](https://docs.npmjs.com/cli/v10/commands/npm) as package manager (see `.node-version` file also)
- [code-server](https://github.com/coder/code-server) (temporarily)

### Setup

```bash
npm install
npm run init
```

Please note that the `init` script will install the web server binaries via
Playwright, including some operation system dependencies. You can skip this part
and do it manually, following the
[documentation](https://playwright.dev/docs/browsers) for more fine grain
control of your local setup.

### Run Tests

```bash
npm run test
```
