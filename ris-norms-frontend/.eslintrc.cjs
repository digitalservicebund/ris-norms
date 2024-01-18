const moduleImportRules = {
  "import/exports-last": 2,
  "import/first": 2,
  "import/newline-after-import": 2,
  "import/no-duplicates": 2,
  "import/order": [
    "error",
    {
      alphabetize: {
        order: "asc",
        caseInsensitive: true,
      },
    },
  ],
  "import/no-unresolved": 0,
  "@typescript-eslint/array-type": "error",
  /*
   * This rules restricts our flexibility to write hierarchically separated
   * components with labels and inputs as it sometimes "can't see" the relation.
   * We decided to rather depend on the end-to-end accessibility tests which test
   * this relation too, but are code independent.
   */
}

module.exports = {
  root: true,
  env: {
    node: true,
    es6: true,
  },
  parser: "@typescript-eslint/parser",
  parserOptions: {
    ecmaVersion: 2020,
    sourceType: "module",
  },
  ignorePatterns: ["dist/", "vite.config.ts"],
  rules: {
    // override/add rules settings here, such as:
    // 'vue/no-unused-vars': 'error'
  },
  overrides: [
    // Avoid linting JavaScript config files with TypeScript rules...
    {
      files: ["**/*.ts"],
      extends: [
        "plugin:import/recommended",
        "plugin:import/typescript",
        "plugin:@typescript-eslint/recommended",
      ],
      rules: {
        ...moduleImportRules,
        "@typescript-eslint/no-floating-promises": ["error"],
        "@typescript-eslint/no-unused-vars": [
          "error",
          { ignoreRestSiblings: true },
        ],
      },
      parserOptions: {
        project: ["./tsconfig.json"],
      },
    },
    {
      files: ["**/*.vue"],
      extends: [
        "plugin:import/recommended",
        "plugin:import/typescript",
        "plugin:vue/vue3-recommended",
        "plugin:vuejs-accessibility/recommended",
        "plugin:vue-scoped-css/vue3-recommended",
        "@vue/typescript/recommended",
        "@vue/prettier",
        "@vue/eslint-config-prettier",
      ],
      rules: {
        ...moduleImportRules,
        "vue/no-static-inline-styles": "error",
        "vue/component-tags-order": [
          "error",
          {
            order: ["script", "template", "style"],
          },
        ],
        "vue/component-name-in-template-casing": [
          "error",
          "PascalCase",
          { registeredComponentsOnly: true },
        ],
        "vue/attributes-order": ["error", { alphabetical: true }],
        "vue/component-api-style": ["error", ["script-setup"]],
        "vue/define-props-declaration": "error",
        "vue/define-emits-declaration": "error",
        "vue/define-macros-order": "error",
        "vue/no-boolean-default": ["error", "default-false"],
        "vue/prefer-true-attribute-shorthand": "error",
        "vue/no-ref-object-destructure": "error",
        "vue/no-restricted-call-after-await": "error",
        "vue/no-undef-properties": "error",
        "vue/no-unused-refs": "error",
        "vue/no-useless-v-bind": "error",
        "vue/prefer-separate-static-class": "error",
        "vue/v-for-delimiter-style": "error",
        "vue/v-on-function-call": "error",
        "vue-scoped-css/enforce-style-type": [
          "warn",
          { allows: ["module", "scoped"] },
        ],
        "@typescript-eslint/no-unused-vars": [
          "error",
          { ignoreRestSiblings: true },
        ],
      },
    },
    {
      files: ["**/*.js"],
      extends: ["eslint:recommended", "plugin:import/recommended"],
      rules: { ...moduleImportRules },
    },
    {
      files: ["**/*.spec.ts"],
      extends: ["plugin:testing-library/vue"],
      rules: { ...moduleImportRules },
    },
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
