import { RisUiTheme } from "@digitalservicebund/ris-ui/primevue"
import "@digitalservicebund/ris-ui/primevue/style.css"
import "@digitalservicebund/ris-ui/fonts.css"
import * as Sentry from "@sentry/vue"
import PrimeVue from "primevue/config"
import { createApp } from "vue"
import App from "./App.vue"
import router from "./router"
import { initializeApiService } from "./services/apiService"
import "./style.css"

initializeApiService(router)

const app = createApp(App)

app.use(PrimeVue, {
  pt: RisUiTheme,
  unstyled: true,
})

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
    tracesSampleRate: 0.01,
    attachProps: true,
    logErrors: true,
  })
}

app.use(router).mount("#app")
