/// <reference types="vitest" />
import vue from "@vitejs/plugin-vue"
import { fileURLToPath, URL } from "node:url"
import icons from "unplugin-icons/vite"
import { defineConfig } from "vite"
import { configDefaults } from "vitest/dist/config"

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    icons({
      scale: 1.3333, // ~24px at the current default font size of 18px
      compiler: "vue3",
    }),
  ],
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
      },
    },
  },
  test: {
    setupFiles: ["src/vitest-setup.ts"],
    globals: true,
    environment: "jsdom",
    exclude: [...configDefaults.exclude, "e2e/**/*.spec.ts"],
    coverage: {
      provider: "v8",
      reporter: ["lcov"],
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

        // App content we're not interested in covering with unit tests. If you
        // add something here, please also add a comment explaining why the
        // exclusion is necessary.

        // Views are too complex too set up and mock in unit tests, we're covering
        // those with E2E test instead. (App is also a view)
        "src/views/**/*",
        "src/App.vue",

        // If necessary to use e.g. guards, we'll have a router-guards file that
        // then should be tested
        "src/router.ts",

        // Just the init file, nothing much to test here.
        "src/main.ts",

        // Stories are just for internal development use and don't need to be
        // tested
        "src/**/*.story.vue",
      ],
    },
  },
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
      "@e2e": fileURLToPath(new URL("./e2e", import.meta.url)),
    },
  },
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
        ws: true,
      },
    },
  },
})
