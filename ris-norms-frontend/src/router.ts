import { createRouter, createWebHistory } from "vue-router"

const routes = [
  {
    path: "/",
    name: "Home",
    redirect: { name: "Procedures" },
  },
  {
    path: "/procedures",
    name: "Procedures",
    component: () => import("@/views/Procedures.vue"),
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
