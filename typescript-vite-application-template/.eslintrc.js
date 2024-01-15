// See https://github.com/import-js/eslint-plugin-import
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
}

module.exports = {
  root: true,
  env: {
    node: true,
    es6: true,
  },
  parser: "@typescript-eslint/parser",
  parserOptions: {
    ecmaVersion: 2022,
  },
  overrides: [
    // Avoid linting JavaScript config files with TypeScript rules...
    {
      files: ["**/*.ts"],
      excludedFiles: "{a11y,e2e}/**/*.ts",
      extends: [
        "plugin:import/recommended",
        "plugin:import/typescript",
        "plugin:@typescript-eslint/recommended",
        "plugin:@typescript-eslint/recommended-requiring-type-checking",
      ],
      rules: { ...moduleImportRules },
      parserOptions: {
        tsconfigRootDir: __dirname,
        project: ["./tsconfig.json"],
      },
    },
    // ...and avoid linting TypeScript files with ES rules for JavaScript config files!
    {
      files: ["**/*.js"],
      extends: ["eslint:recommended", "plugin:import/recommended"],
      rules: { ...moduleImportRules },
    },
  ],
}
