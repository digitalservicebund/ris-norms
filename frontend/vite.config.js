// <reference types="vitest" />
import { fileURLToPath, URL } from "node:url"
import vue from "@vitejs/plugin-vue"
import { defineConfig } from "vite"
// https://vitejs.dev/config/

export default defineConfig({
  plugins: [vue()],
  test: {
    global: true,
    coverage: {
      provider: "v8", // or 'istanbul'
    },
  },
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
})
