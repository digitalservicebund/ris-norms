import { Sentry } from "@/plugins/sentry"
import "@digitalservicebund/ris-ui/fonts.css"
import { RisUiLocale, RisUiTheme } from "@digitalservicebund/ris-ui/primevue"
import "@digitalservicebund/ris-ui/primevue/style.css"
import PrimeVue from "primevue/config"
import ConfirmationService from "primevue/confirmationservice"
import ToastService from "primevue/toastservice"
import { createApp } from "vue"
import App from "./App.vue"
import { useAuthentication } from "./lib/auth"
import router from "./router"
import { getEnv } from "./services/envService"
import "./style.css"

try {
  // Fetch environment configuration
  const env = await getEnv()

  // Initialize Vue application
  const app = createApp(App)
    .use(PrimeVue, {
      pt: RisUiTheme,
      unstyled: true,
      locale: RisUiLocale.deDE,
    })
    .use(ToastService)
    .use(ConfirmationService)
    .use(router)
    .use(Sentry, { environment: env.name, router })

  console.log("[auth debug] location when creating app: ", window.location.href)
  console.log("[auth debug] created app")

  // Configure authentication
  const auth = useAuthentication()
  await auth.configure({
    url: env.authUrl,
    clientId: env.authClientId,
    realm: env.authRealm,
  })

  console.log("[auth debug] configured authentication")

  // If all initialization succeeds, mount app
  app.mount("#app")
  console.log("[auth debug] mounted app")
} catch (e: unknown) {
  // If an error occurs above, catch it here

  // Get references to the error message container and the loading message
  const errorContainer = document.getElementById("error-container")
  const loadingMessage = document.querySelector(".fallback p")

  if (errorContainer) {
    errorContainer.removeAttribute("hidden")
    if (loadingMessage) {
      loadingMessage.setAttribute("hidden", "true")
    }
    if (e instanceof Error) {
      const errorDetails = errorContainer.querySelector("#error-detail")
      if (errorDetails) {
        errorDetails.textContent = e.message
      }
    }
  }
}
