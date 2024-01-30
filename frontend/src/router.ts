import { createRouter, createWebHistory } from "vue-router"

const routes = [
  {
    path: "/",
    name: "Home",
    redirect: { name: "Procedures" },
  },
  {
    path: "/procedures",
    children: [
      {
        path: "",
        name: "Procedures",
        component: () => import("@/views/Procedures.vue"),
      },
      {
        path: ":id",
        component: () =>
          import("./components/controls/RisNavbarSideLayout.vue"),
        children: [
          {
            path: "",
            name: "Procedure",
            redirect: { name: "ProcedureArticleOverview" },
          },
          {
            path: "article-overview",
            name: "ProcedureArticleOverview",
            component: () => import("@/views/ArticleOverview.vue"),
          },
          {
            path: "affected-standards",
            name: "ProcedureAffectedStandards",
            component: () => import("@/views/AffectedStandards.vue"),
          },
        ],
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
