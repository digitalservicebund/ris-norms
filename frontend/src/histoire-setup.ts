import "@/style.css"
import { defineSetupVue3 } from "@histoire/plugin-vue"
import { createRouter, createWebHashHistory } from "vue-router"

// Very simple mock router for histoire. It doesn't do anything, but it has
// the routes needed for components in stories to render without crashing.
const dummyRouter = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: "/",
      name: "Home",
      component: () => {},
    },
  ],
})

export const setupVue3 = defineSetupVue3(({ app }) => {
  app.use(dummyRouter)
})
