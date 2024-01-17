/// <reference types="vitest" />
import { defineConfig } from "vite"
import vue from "@vitejs/plugin-vue"

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  test: {
    globals: true,
    environment: "jsdom",
    coverage: {
      provider: "v8",
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
        "**/*.spec.*",

        // App content we're not interested in covering with unit tests
        "src/App.vue",
      ],
    },
  },
})
