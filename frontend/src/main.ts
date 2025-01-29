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

const env = await getEnv()

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

const auth = useAuthentication()

await auth.configure({
  url: env.authUrl,
  clientId: env.authClientId,
  realm: env.authRealm,
})

app.mount("#app")
