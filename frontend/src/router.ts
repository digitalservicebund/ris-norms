import { createEliPathParameter } from "@/composables/useEliPathParameter"
import { createRouter, createWebHistory } from "vue-router"

/**
 * The regular expressions for the eId is based on the definitions from LDML.de 1.6 (Section 9.2.11.63, eIdLiterals.einzelvorschrift)
 *
 * The expression only matches eIds that represent articles or paragraphs.
 *
 * All groups have been converted to non-capturing groups and all closing ) have been escaped. This is as the vue-router otherwise has problems parsing the RegEx.
 */
const ARTICLE_EID_ROUTE_PATH =
  ":eid((?:(?:[a-zäöüß0-9]+-[a-zäöüß0-9]+(?:\\.[azäöüß0-9]+\\)*\\)_\\)*(?:(?:para|art\\)+-[a-zäöüß0-9]+(?:\\.[azäöüß0-9]+\\)*\\))"

const routes = [
  {
    path: "/",
    name: "Home",
    redirect: { name: "AmendingLaws" },
  },
  {
    path: "/amending-laws",
    children: [
      {
        path: "",
        name: "AmendingLaws",
        component: () => import("@/views/AmendingLaws.vue"),
      },
      {
        path: createEliPathParameter(),
        component: () => import("@/views/AmendingLaw.vue"),
        children: [
          {
            path: "",
            name: "AmendingLaw",
            redirect: { name: "AmendingLawArticles" },
          },
          {
            path: "affected-documents",
            name: "AmendingLawAffectedDocuments",
            component: () => import("@/views/AffectedDocuments.vue"),
          },
          {
            path: "articles",
            name: "AmendingLawArticles",
            component: () => import("@/views/Articles.vue"),
          },
        ],
      },
    ],
  },
  {
    path: `/amending-laws/${createEliPathParameter()}/articles/${ARTICLE_EID_ROUTE_PATH}/edit`,
    name: "AmendingLawArticleEditor",
    component: () => import("@/views/AmendingLawArticleEditor.vue"),
  },
  {
    path: `/amending-laws/${createEliPathParameter()}/affected-documents/${createEliPathParameter("affectedDocument")}/edit`,
    name: "AmendingLawAffectedDocumentEditor",
    component: () => import("@/views/AmendingLawAffectedDocumentEditor.vue"),
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
