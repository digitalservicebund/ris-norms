# Frontend

The Norms frontend is a Single Page Application built with [TypeScript](https://www.typescriptlang.org/), [Vue](https://vuejs.org/), and [Tailwind](https://tailwindcss.com/). Most components come from [RIS UI](https://github.com/digitalservicebund/ris-ui), a component library shared with other NeuRIS projects. RIS UI is a theme for [PrimeVue](https://primevue.org/).

The frontend also includes our E2E tests.

Before diving into the code, please get familiar with our 🔒 [code conventions](https://digitalservicebund.atlassian.net/wiki/x/BIC1N).

For the most part, the organization follows a standard Vue CLI setup. If you've worked with Vue before, it will feel very familiar. The most important differences are how we organize our state and API communication, as well as our views:

- API communication: [usage of `useFetch`](https://github.com/digitalservicebund/ris-norms/blob/main/doc/adr/0010-use-fetch.md)
- Views: [Organize feature specific things by page in frontend](https://github.com/digitalservicebund/ris-norms/blob/main/doc/adr/0013-organize-feature-specific-things-by-page-in-frontend.md)

## Running

Make sure your system meets the [prerequisites](../README.md#prerequisites). Then, install the dependencies:

```sh
npm install
```

You can now start the application:

```sh
node --run dev
```

The frontend by itself will not be very useful, so make sure the backend and other required services are [running](../README.md#quickstart), too.

## Testing

### Unit tests

We cover all code outside of `views/` with unit tests (views are too complex for unit testing and are covered in E2E tests instead). We use [Vitest](https://vitest.dev/) and [Vue Testing Library](https://testing-library.com/docs/vue-testing-library/intro/).

To run unit tests once:

```sh
node --run test
```

To run unit tests in watch mode (re-runs tests on code changes and gives you additional options like filtering):

```sh
node --run test:watch
```

### E2E tests

We write end-to-end (E2E) tests using [Playwright](https://playwright.dev/).

Make sure the backend and other required services are [running](../README.md#quickstart) before executing the tests. Then, install the browsers:

```sh
npx playwright install
```

Let Playwright know where to connect to by using the `.env.local` file:

```sh
cp .env.local.example .env.local
```

You then have various ways of running the E2E tests:

```sh
node --run test:browsers                       # Runs all tests in all browsers
node --run test:browsers -- [testfile]         # Runs a specific test
node --run test:browsers -- [testfile] --debug # Debugs a specific test

# Run tests in a specific browser:
node --run test:e2e -- --project chromium --repeat-each 1
node --run test:e2e -- --project firefox --repeat-each 1
```

### Accessibility tests

The project includes automated accessibility (a11y) testing using [axe-core](https://github.com/dequelabs/axe-core). Accessibility tests also use Playwright, so everything about E2E tests also applies.

Make sure the backend and other required services are [running](../README.md#quickstart) before executing the tests. To run the accessibility tests:

```sh
node --run test:a11y
```

### Smoke tests

We use smoke testing for superficially checking certain functionality on a deployed version of our system, usually the staging environment. Smoke tests focus on areas where the live system significantly differs from the local or E2E setup, such as how the frontend is served (served by Vite locally, but served by Spring in production).

If you need to run smoke tests locally, first set `E2E_SMOKETEST_BASE_URL`, `E2E_SMOKETEST_USERNAME`, and `E2E_SMOKETEST_PASSWORD` in your environment. It will be picked up automatically if you add it to your `.env.local` (also see `.env.local.example`).

Then run:

```sh
node --run test:smoke
```

## Code quality and documentation

We use TypeScript, ESLint, and Prettier to support code quality and consistent formatting. To run ESLint and Prettier:

```sh
node --run style:check  # Check if code follows conventions and is formatted
node --run style:fix    # Check + try to fix violations automatically
```

To run type checking:

```sh
node --run typecheck
```

If you need more fine-grained control over which checks are performed, you'll find all available scripts in [`package.json`](./package.json).

In addition to local checks, any code in pull requests and on `main` will be checked by SonarQube Cloud. You can find the reports here: [SonarQube Cloud](https://sonarcloud.io/project/overview?id=digitalservicebund_ris-norms-frontend)

## Icons

All icons in the [Google Material](https://icon-sets.iconify.design/ic) sets can be used. To make the icon available in your code:

- Find and select the icon in the catalog. We usually use the baseline or outline styles, depending on the icon.
- In the icon detail panel, select "Component" as the format on the left, and "Unplugin Icons" as the framework on the top
- Copy the resulting code. It should look something like this:

```js
import IcBaselineAccessAlarms from "~icons/ic/baseline-access-alarms"
```
