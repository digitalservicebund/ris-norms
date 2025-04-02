import theme from "@/lib/theme"
import { Sentry } from "@/plugins/sentry"
import "@digitalservicebund/ris-ui/fonts.css"
import { RisUiLocale } from "@digitalservicebund/ris-ui/primevue"
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
      pt: theme,
      unstyled: true,
      locale: RisUiLocale.deDE,
    })
    .use(ToastService)
    .use(ConfirmationService)
    .use(router)
    .use(Sentry, { environment: env.name, router })

  // Configure authentication
  const auth = useAuthentication()
  await auth.configure({
    url: env.authUrl,
    clientId: env.authClientId,
    realm: env.authRealm,
  })

  // If all initialization succeeds, mount app
  app.mount("#app")
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
