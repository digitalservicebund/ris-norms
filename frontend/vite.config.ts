/// <reference types="vitest" />
import vue from "@vitejs/plugin-vue"
import { fileURLToPath, URL } from "node:url"
import icons from "unplugin-icons/vite"
import { defineConfig } from "vite"

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    icons({
      scale: 1.3333, // ~24px at the current default font size of 18px
    }),
  ],
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
        // not testing the router.ts; if necessary to use e.g. guards, we'll have a router-guards file that then should be tested
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
