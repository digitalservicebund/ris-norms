import { createApp } from "vue"
import "./style.css"
import App from "./App.vue"
import router from "./router"
import { initializeApiService } from "./services/apiService"

initializeApiService(router)

createApp(App).use(router).mount("#app")
