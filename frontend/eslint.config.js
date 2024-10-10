import { includeIgnoreFile } from "@eslint/compat"
import js from "@eslint/js"
import prettierConfig from "@vue/eslint-config-prettier"
import vueTsEslintConfig from "@vue/eslint-config-typescript"
import importPlugin from "eslint-plugin-import"
import playwrightPlugin from "eslint-plugin-playwright"
import vuePlugin from "eslint-plugin-vue"
import vueA11yPlugin from "eslint-plugin-vuejs-accessibility"
import globals from "globals"
import { fileURLToPath, URL } from "node:url"
import {
  config as defineConfig,
  configs as tsEslintConfigs,
} from "typescript-eslint"
import vueEslintParser from "vue-eslint-parser"

export default defineConfig(
  // Files
  includeIgnoreFile(fileURLToPath(new URL(".gitignore", import.meta.url))),
  { files: ["**/*.ts", "**/*.js", "**/*.vue"] },

  // Basic rules
  js.configs.recommended,
  ...tsEslintConfigs.recommended,
  importPlugin.flatConfigs.recommended,
  importPlugin.flatConfigs.typescript,
  ...vuePlugin.configs["flat/recommended"],
  ...vueA11yPlugin.configs["flat/recommended"],
  ...vueTsEslintConfig(),

  // Additional rules for E2E tests
  {
    files: ["e2e/**/*.ts"],
    ...playwrightPlugin.configs["flat/recommended"],
  },

  {
    languageOptions: {
      globals: { ...globals.node },
      parser: vueEslintParser,
      ecmaVersion: 2020,
      sourceType: "module",
      parserOptions: {
        parser: "@typescript-eslint/parser",
        tsconfigRootDir: import.meta.dirname,
      },
    },

    settings: {
      "import/resolver": { node: true, typescript: true },
    },

    rules: {
      // We generally try to stick with the recommended defaults as much as
      // possible. If you add a rule or an exception, please also add a short
      // explanation for why this is necessary.

      // Icons are "virtual" modules generated by a Vite plugin
      "import/no-unresolved": ["error", { ignore: ["^~icons\\/"] }],

      // If we can't avoid to use styles, they should at least be scoped
      "vue/enforce-style-attribute": ["warn", { allow: ["scoped", "module"] }],

      // We require consistent API styles and TypeScript in all components
      "vue/component-api-style": ["error", ["script-setup"]],
      "vue/block-lang": ["error", { script: { lang: "ts" } }],

      // Component names in template should be the same as the name they're
      // imported as.
      "vue/component-name-in-template-casing": "error",
    },
  },

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

  prettierConfig,
)