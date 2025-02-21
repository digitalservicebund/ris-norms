import { createDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router"

/**
 * The regular expressions for the eId is based on the definitions from LDML.de 1.7.2 (Section 9.2.12.67, eIdLiterals.einzelvorschrift)
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
    path: `/${createDokumentExpressionEliPathParameter()}/metadata`,
    name: "ExpressionMetadataEditor",
    component: () =>
      import(
        "@/views/expression/metadata-editor/ExpressionMetadataEditor.view.vue"
      ),
    children: [
      {
        path: "",
        name: "ExpressionMetadataEditorRahmen",
        component: () =>
          import(
            "@/views/expression/metadata-editor/rahmen/ExpressionMetadataEditorRahmen.view.vue"
          ),
      },
      {
        path: "element/:eid",
        name: "ExpressionMetadataEditorElement",
        component: () =>
          import(
            "@/views/expression/metadata-editor/element/ExpressionMetadataEditorElement.view.vue"
          ),
      },
      {
        path: "outline/:eid",
        name: "ExpressionMetadataEditorOutlineElement",
        component: () =>
          import(
            "@/views/expression/metadata-editor/outline-element/ExpressionMetadataEditorOutlineElement.view.vue"
          ),
      },
    ],
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
        path: createDokumentExpressionEliPathParameter(),
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
    path: `/amending-laws/${createDokumentExpressionEliPathParameter()}/articles/${ARTICLE_EID_ROUTE_PATH}/edit`,
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
    path: `/amending-laws/${createDokumentExpressionEliPathParameter()}/references/:refEid?`,
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
