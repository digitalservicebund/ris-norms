import { sentryVitePlugin } from "@sentry/vite-plugin"
import vue from "@vitejs/plugin-vue"
import { fileURLToPath, URL } from "node:url"
import icons from "unplugin-icons/vite"
import { defineConfig } from "vite"

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
  const enableSentry = mode === "production" && process.env.SENTRY_AUTH_TOKEN

  console.info(
    `Sentry plugin is ${enableSentry ? "enabled" : "disabled"} in ${mode} mode`,
  )

  return {
    base: "/app/",
    build: {
      sourcemap: true,
      target: ["edge127", "firefox115", "chrome127"],
    },
    plugins: [
      vue(),
      icons({
        scale: 1.3333, // ~24px at the current default font size of 18px
        compiler: "vue3",
      }),
      enableSentry &&
        sentryVitePlugin({
          authToken: process.env.SENTRY_AUTH_TOKEN,
          org: "digitalservice",
          project: "ris-norms",
          telemetry: process.env.VITEST !== "true",
        }),
    ],
    server: {
      proxy: {
        "^/(api|environment)": {
          target: "http://localhost:8080",
          changeOrigin: true,
          secure: false,
        },
      },
    },
    resolve: {
      alias: {
        "@": fileURLToPath(new URL("./src", import.meta.url)),
        "@e2e": fileURLToPath(new URL("./e2e", import.meta.url)),
      },
    },
  }
})
