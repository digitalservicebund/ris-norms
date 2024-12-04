import { createEliPathParameter } from "@/composables/useEliPathParameter"
import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router"

/**
 * The regular expressions for the eId is based on the definitions from LDML.de 1.7.1 (Section 9.2.12.67, eIdLiterals.einzelvorschrift)
 *
 * The expression only matches eIds that represent articles or paragraphs.
 *
 * All groups have been converted to non-capturing groups and all closing ) have been escaped. This is as the vue-router otherwise has problems parsing the RegEx.
 */
const ARTICLE_EID_ROUTE_PATH =
  ":eid((?:[a-zäöüß0-9]+-[1-9]{1}[0-9]*_\\)*(?:art\\)-[1-9]{1}[0-9]*)"

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
        component: () => import("@/views/amending-laws/AmendingLaws.view.vue"),
      },
      {
        path: "upload",
        name: "UploadAnnouncement",
        component: () =>
          import("@/views/amending-law/upload/UploadAnnouncement.view.vue"),
      },
      {
        path: createEliPathParameter(),
        component: () => import("@/views/amending-law/AmendingLaw.view.vue"),
        children: [
          {
            path: "",
            name: "AmendingLaw",
            component: () =>
              import(
                "@/views/amending-law/overview/AmendingLawOverview.view.vue"
              ),
          },
          {
            path: "temporal-data",
            name: "TemporalData",
            component: () =>
              import(
                "@/views/amending-law/temporal-data/TemporalData.view.vue"
              ),
          },
          {
            path: "affected-documents",
            name: "AmendingLawAffectedDocuments",
            component: () =>
              import(
                "@/views/amending-law/affected-documents/AffectedDocuments.view.vue"
              ),
          },
          {
            path: "articles",
            name: "AmendingLawArticles",
            component: () =>
              import("@/views/amending-law/articles/Articles.view.vue"),
          },
          {
            path: "publishing",
            name: "AmendingLawPublishing",
            component: () =>
              import("@/views/amending-law/publishing/Publishing.view.vue"),
          },
        ],
      },
    ],
  },
  {
    path: `/amending-laws/${createEliPathParameter()}/articles/${ARTICLE_EID_ROUTE_PATH}/edit`,
    name: "AmendingLawArticleEditor",
    component: () =>
      import(
        "@/views/amending-law/articles/editor/AmendingLawArticleEditor.view.vue"
      ),
    children: [
      {
        path: ":modEid",
        name: "AmendingLawArticleEditorSingleMod",
        component: () =>
          import(
            "@/views/amending-law/articles/editor/single-mods/AmendingLawArticleEditorSingleMod.view.vue"
          ),
      },
      {
        path: "",
        name: "AmendingLawArticleEditorMultiMod",
        component: () =>
          import(
            "@/views/amending-law/articles/editor/multiple-mods/AmendingLawArticleEditorMultiMod.view.vue"
          ),
      },
    ],
  },
  {
    path: `/amending-laws/${createEliPathParameter()}/affected-documents/${createEliPathParameter("affectedDocument")}/edit`,
    name: "AmendingLawMetadataEditor",
    component: () =>
      import(
        "@/views/amending-law/affected-documents/metadata-editor/AmendingLawMetadataEditor.view.vue"
      ),
    children: [
      {
        path: ":timeBoundary?",
        name: "AmendingLawMetadataEditorRahmen",
        component: () =>
          import(
            "@/views/amending-law/affected-documents/metadata-editor/rahmen/AmendingLawMetadataEditorRahmen.view.vue"
          ),
      },
      {
        path: ":timeBoundary/:eid",
        name: "AmendingLawMetadataEditorElement",
        component: () =>
          import(
            "@/views/amending-law/affected-documents/metadata-editor/element/AmendingLawMetadataEditorElement.view.vue"
          ),
      },
      {
        path: ":timeBoundary/outline/:eid",
        name: "AmendingLawMetadataEditorOutlineElement",
        component: () =>
          import(
            "@/views/amending-law/affected-documents/metadata-editor/outline-element/AmendingLawMetadataEditorOutlineElement.view.vue"
          ),
      },
    ],
  },
  {
    path: `/amending-laws/${createEliPathParameter()}/affected-documents/${createEliPathParameter("affectedDocument")}/references/:modEid?/:refEid?`,
    name: "References",
    component: () =>
      import(
        "@/views/amending-law/affected-documents/references/References.view.vue"
      ),
  },
  {
    path: "/:pathMatch(.*)*",
    name: "NotFound",
    component: () => import("@/views/404/404NotFound.view.vue"),
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
