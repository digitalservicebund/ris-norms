import "@digitalservicebund/ris-ui/fonts.css"
import { RisUiLocale, RisUiTheme } from "@digitalservicebund/ris-ui/primevue"
import "@digitalservicebund/ris-ui/primevue/style.css"
import * as Sentry from "@sentry/vue"
import PrimeVue from "primevue/config"
import ConfirmationService from "primevue/confirmationservice"
import ToastService from "primevue/toastservice"
import { createApp } from "vue"
import App from "./App.vue"
import { useAuthentication, auth } from "./lib/auth"
import router from "./router"
import "./style.css"

const app = createApp(App)
  .use(PrimeVue, {
    pt: RisUiTheme,
    unstyled: true,
    locale: RisUiLocale.deDE,
  })
  .use(ToastService)
  .use(ConfirmationService)
  .use(router)
const auth = useAuthentication()

await auth.configure({
  url: "http://localhost:8443",
  clientId: "ris-norms-local",
  realm: "ris",
})

const env = detectEnv()

const enableSentry = [
  RisEnvironment.PRODUCTION,
  RisEnvironment.UAT,
  RisEnvironment.STAGING,
].includes(env)

console.info(
  `Sentry reporting is ${enableSentry ? "enabled" : "disabled"} in environment "${env}"`,
)

if (enableSentry) {
  Sentry.init({
    app,
    environment: env,
    dsn: "https://bc002a52fd187905497284bed2d771c1@o1248831.ingest.us.sentry.io/4507543284613120",
    initialScope: { tags: { source: "frontend" } },
    integrations: [
      Sentry.browserTracingIntegration({ router }),
      Sentry.captureConsoleIntegration(),
    ],
    tracesSampleRate: 0.01,
    attachProps: true,
    logErrors: true,
  })
}

app.mount("#app")

console.log(auth.isAuthenticated())
