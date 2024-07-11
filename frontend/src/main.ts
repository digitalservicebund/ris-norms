import { createApp } from "vue"
import "./style.css"
import App from "./App.vue"
import router from "./router"
import * as Sentry from "@sentry/vue"
import { initializeApiService } from "./services/apiService"

initializeApiService(router)

const app = createApp(App)

if (import.meta.env.PROD && import.meta.env.E2E_TESTS_RUNNING !== "true") {
  Sentry.init({
    app,
    environment: "staging",
    dsn: "https://bc002a52fd187905497284bed2d771c1@o1248831.ingest.us.sentry.io/4507543284613120",
    initialScope: {
      tags: { source: "frontend" },
    },
    integrations: [
      Sentry.browserTracingIntegration({ router }),
      Sentry.captureConsoleIntegration(),
    ],
    tracesSampleRate: 1.0,
    attachProps: true,
    logErrors: true,
  })
}

app.use(router).mount("#app")
