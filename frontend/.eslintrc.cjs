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
    "import/no-unresolved": 0,

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
      // Make an exception from requiring component names to consist of multiple
      // words for views as they: 1) will not be used as standalone components
      // and therefore do not need to adhere to HTML naming conventions, and 2)
      // can therefore be simpler and match the route names.
      files: ["src/views/**/*.vue"],
      rules: {
        "vue/multi-word-component-names": "off",
      },
    },
  ],
}
