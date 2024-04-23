module.exports = {
  root: true,
  env: { node: true, es6: true },
  parser: "vue-eslint-parser",
  parserOptions: {
    parser: "@typescript-eslint/parser",
    ecmaVersion: 2020,
    sourceType: "module",
    project: ["./tsconfig.json", "./tsconfig.node.json"],
  },
  ignorePatterns: ["dist/", ".eslintrc.cjs"],

  extends: [
    "plugin:import/recommended",
    "plugin:import/typescript",
    "plugin:@typescript-eslint/recommended",
    "plugin:vue/vue3-recommended",
    "plugin:vuejs-accessibility/recommended",
    "@vue/typescript/recommended",
    "@vue/prettier",
    "@vue/eslint-config-prettier",
  ],

  rules: {
    // We generally try to stick with the recommended defaults as much as
    // possible. If you add a rule or an exception, please also add a short
    // explanation for why this is necessary.

    // This doesn't work with aliases
    "import/no-unresolved": "off",

    // If we can't avoid to use styles, they should at least be scoped
    "vue/enforce-style-attribute": ["warn", { allow: ["scoped", "module"] }],

    // We require consistent API styles and TypeScript in all components
    "vue/component-api-style": ["error", ["script-setup"]],
    "vue/block-lang": ["error", { script: { lang: "ts" } }],

    // Component names in template should be the same as the name they're
    // imported as.
    "vue/component-name-in-template-casing": "error",
  },

  overrides: [
    {
      files: ["src/views/**/*.vue"],

      rules: {
        // Make an exception from requiring component names to consist of multiple
        // words for views as they: 1) will not be used as standalone components
        // and therefore do not need to adhere to HTML naming conventions, and 2)
        // can therefore be simpler and match the route names.
        "vue/multi-word-component-names": "off",
      },
    },
    {
      files: ["e2e/**/*.ts"],

      extends: ["plugin:playwright/playwright-test"],

      rules: {
        // It's acceptable to sometimes skip tests if that means we don't have
        // to delete or comment out tests that don't behave as expected and we
        // don't have time to fix them immediately.
        "playwright/no-skipped-test": "off",
      },
    },
    {
      files: ["src/**/*.spec.ts"],

      rules: {
        // Unit tests are allowed to have multiple components per file as we
        // might need to create a bunch of wrapper components / advanced
        // examples to test certain types of behavior (e.g. slots).
        "vue/one-component-per-file": "off",
      },
    },
  ],
}
