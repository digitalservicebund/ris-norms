module.exports = {
  extends: ["../.eslintrc.js"],
  overrides: [
    {
      files: ["**/*.ts"],
      excludedFiles: "{a11y,e2e}/**/*.ts",
      extends: ["plugin:jest-dom/recommended"],
    },
    {
      files: ["{a11y,e2e}/**/*.ts"],
      extends: ["plugin:playwright/playwright-test"],
    },
  ],
}
