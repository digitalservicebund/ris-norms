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
  .use(Sentry, { environmentName: "local", router })

const auth = useAuthentication()

await auth.configure({
  url: "http://localhost:8443",
  clientId: "ris-norms-local",
  realm: "ris",
})

app.mount("#app")
