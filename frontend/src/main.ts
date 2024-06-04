import { createApp } from "vue"
import { createPinia } from "pinia"
import "./style.css"
import App from "./App.vue"
import router from "./router"
import { initializeApiService } from "./services/apiService"

initializeApiService(router)
const storeManager = createPinia()

createApp(App).use(storeManager).use(router).mount("#app")
