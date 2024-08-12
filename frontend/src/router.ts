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
    children: [
      {
        path: ":modEid",
        name: "AmendingLawArticleEditorSingleMod",
        component: () =>
          import("@/views/AmendingLawArticleEditorSingleMod.vue"),
      },
      {
        path: "",
        name: "AmendingLawArticleEditorMultiMod",
        component: () => import("@/views/AmendingLawArticleEditorMultiMod.vue"),
      },
    ],
  },
  {
    path: `/amending-laws/${createEliPathParameter()}/affected-documents/${createEliPathParameter("affectedDocument")}/edit`,
    name: "AmendingLawMetadataEditor",
    component: () => import("@/views/AmendingLawMetadataEditor.vue"),
    children: [
      {
        path: ":timeBoundary?",
        name: "AmendingLawMetadataEditorRahmen",
        component: () => import("@/views/AmendingLawMetadataEditorRahmen.vue"),
      },
      {
        path: ":timeBoundary/:eid",
        name: "AmendingLawMetadataEditorElement",
        component: () => import("@/views/AmendingLawMetadataEditorElement.vue"),
      },
      {
        path: ":timeBoundary/:eid",
        name: "AmendingLawMetadataEditorOutlineElement",
        component: () =>
          import("@/views/AmendingLawMetadataEditorOutlineElement.vue"),
      },
    ],
  },
  {
    path: `/amending-laws/${createEliPathParameter()}/affected-documents/${createEliPathParameter("affectedDocument")}/references`,
    name: "References",
    component: () => import("@/views/References.vue"),
  },
  {
    path: "/:pathMatch(.*)*",
    name: "NotFound",
    component: () => import("@/views/404NotFound.vue"),
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
