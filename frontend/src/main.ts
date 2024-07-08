import { createApp } from "vue"
import "./style.css"
import App from "./App.vue"
import router from "./router"
import * as Sentry from "@sentry/vue"
import { initializeApiService } from "./services/apiService"

initializeApiService(router)

const app = createApp(App)

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

app.use(router).mount("#app")
