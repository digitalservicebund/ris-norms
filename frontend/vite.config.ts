/// <reference types="vitest" />
import { defineConfig } from "vite"
import { fileURLToPath, URL } from "node:url"
import vue from "@vitejs/plugin-vue"

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  test: {
    setupFiles: ["src/vitest-setup.ts"],
    globals: true,
    environment: "jsdom",
    coverage: {
      provider: "v8",
      reporter: ["lcov"],
      exclude: [
        "src/App.vue",
        "src/main.ts",
        // testing the real router may not be worth it
        // cf. https://test-utils.vuejs.org/guide/advanced/vue-router.html
        "src/router.ts",
        "**/*.config.js",
        ".eslintrc.cjs",
        "**/*.d.ts",
      ],
    },
  },
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
})
