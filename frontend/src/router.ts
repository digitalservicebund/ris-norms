import { createEliPathParameter } from "@/composables/useEliPathParameter"
import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router"

/**
 * The regular expressions for the eId is based on the definitions from LDML.de 1.6 (Section 9.2.11.63, eIdLiterals.einzelvorschrift)
 *
 * The expression only matches eIds that represent articles or paragraphs.
 *
 * All groups have been converted to non-capturing groups and all closing ) have been escaped. This is as the vue-router otherwise has problems parsing the RegEx.
 */
const ARTICLE_EID_ROUTE_PATH =
  ":eid((?:(?:[a-zäöüß0-9]+-[a-zäöüß0-9]+(?:\\.[azäöüß0-9]+\\)*\\)_\\)*(?:(?:para|art\\)+-[a-zäöüß0-9]+(?:\\.[azäöüß0-9]+\\)*\\))"

const routes: readonly RouteRecordRaw[] = [
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
            component: () => import("@/views/AmendingLawOverview.vue"),
          },
          {
            path: "temporal-data",
            name: "TemporalData",
            component: () => import("@/views/TemporalData.vue"),
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
          {
            path: "publishing",
            name: "AmendingLawPublishing",
            component: () => import("@/views/Publishing.vue"),
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
    children: [
      {
        path: "",
        // We do not know the possible zeitgrenzen (and therefore which one to select by default) when first navigating
        // to the affected document editor. Vue-Router doesn't support optional parameters so we need to provide some
        // parameter for the zeitgrenze.
        redirect: (route) => ({
          ...route,
          name: "AmendingLawAffectedDocumentRahmenEditor",
          params: {
            ...route.params,
            zeitgrenze: "unknown",
          },
        }),
      },
      {
        path: ":zeitgrenze",
        children: [
          {
            path: "",
            name: "AmendingLawAffectedDocumentRahmenEditor",
            component: () =>
              import("@/views/AmendingLawAffectedDocumentRahmenEditor.vue"),
          },
          {
            path: ":eid",
            name: "AmendingLawAffectedDocumentArticleEditor",
            component: () =>
              import("@/views/AmendingLawAffectedDocumentArticleEditor.vue"),
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
