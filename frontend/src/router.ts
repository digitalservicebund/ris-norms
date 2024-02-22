import { createRouter, createWebHistory } from "vue-router"

const routes = [
  {
    path: "/",
    name: "Home",
    redirect: { name: "AmendingLaws" },
  },
  {
    path: "/amendinglaws",
    children: [
      {
        path: "",
        name: "AmendingLaws",
        component: () => import("@/views/AmendingLaws.vue"),
      },
      {
        path: ":id",
        component: () => import("@/views/AmendingLaw.vue"),
        children: [
          {
            path: "",
            name: "AmendingLaw",
            redirect: { name: "AmendingLawArticleOverview" },
          },
          {
            path: "article-overview",
            name: "AmendingLawArticleOverview",
            component: () => import("@/views/ArticleOverview.vue"),
          },
          {
            path: "affected-standards",
            name: "AmendingLawAffectedStandards",
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
