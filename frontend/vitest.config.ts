import { defineConfig, mergeConfig } from "vitest/config"
import { configDefaults } from "vitest/dist/config"
import viteConfig from "./vite.config"

export default defineConfig((context) =>
  mergeConfig(
    viteConfig(context),
    defineConfig({
      test: {
        setupFiles: ["src/vitest-setup.ts"],
        globals: true,
        environment: "jsdom",
        exclude: [...configDefaults.exclude, "e2e/**/*.spec.ts", "a11y/**/*"],
        css: {
          // Needed so we can reliably test for class names for CSS modules.
          // Otherwise scoped CSS classes would have an unreliable hash
          // attached to the class name.
          modules: { classNameStrategy: "non-scoped" },
        },
        coverage: {
          provider: "istanbul",
          reporter: ["lcov"],
          // Changes to this also need to be reflected in the sonar-project.properties
          exclude: [
            // Configuration and generated outputs
            "**/[.]**",
            "coverage/**/*",
            "dist/**/*",
            "**/.*rc.{?(c|m)js,yml}",
            "*.config.{js,ts}",

            // Types
            "**/*.d.ts",

            // Tests
            "test/**/*",
            "e2e/**/*",
            "a11y/**/*",

            // App content we're not interested in covering with unit tests. If you
            // add something here, please also add a comment explaining why the
            // exclusion is necessary.

            // Views are too complex too set up and mock in unit tests, we're covering
            // those with E2E test instead. (App is also a view)
            "**/*.view.vue",
            "src/App.vue",

            // If necessary to use e.g. guards, we'll have a router-guards file that
            // then should be tested
            "src/router.ts",

            // This file only contains styles and can't meaninfully be tested
            "src/lib/theme.ts",

            // Just the init file and global setup, nothing much to test here.
            "src/main.ts",
            "src/plugins/sentry.ts",

            // Stories are just for internal development use and don't need to be
            // tested
            "src/**/*.story.vue",
          ],
        },
      },
    }),
  ),
)
