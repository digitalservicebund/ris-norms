import { createApp } from "vue"
import "./style.css"
import App from "./App.vue"
import router from "./router"
import * as Sentry from "@sentry/vue"
import { initializeApiService } from "./services/apiService"

initializeApiService(router)

const app = createApp(App)

if (
  import.meta.env.VITE_SENTRY_DSN &&
  import.meta.env.E2E_TESTS_RUNNING !== "true"
) {
  Sentry.init({
    app,
    dsn: import.meta.env.SENTRY_DSN,
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
