import { createDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import type { RouteRecordRaw } from "vue-router"
import { createRouter, createWebHistory } from "vue-router"

/**
 * The regular expressions for the eId is based on the definitions from
 * LDML.de 1.7.2 (Section 9.2.12.67, eIdLiterals.einzelvorschrift)
 *
 * The expression only matches eIds that represent articles or paragraphs.
 *
 * All groups have been converted to non-capturing groups and all closing ")"
 * have been escaped. This is as the vue-router otherwise has problems parsing
 * the RegEx.
 */
const ARTICLE_EID_ROUTE_PATH =
  ":eid((?:[a-zäöüß0-9]+-[1-9]{1}[0-9]*_\\)*(?:art\\)-[1-9]{1}[0-9]*)"

const routes: readonly RouteRecordRaw[] = [
  {
    path: "/",
    name: "Home",
    redirect: { name: "Verkuendungen" },
  },

  {
    path: `/verkuendungen`,
    children: [
      {
        path: "",
        name: "Verkuendungen",
        component: () => import("@/views/verkuendungen/Verkuendungen.view.vue"),
      },
      {
        path: "upload",
        name: "VerkuendungUpload",
        component: () =>
          import("@/views/verkuendungen/upload/UploadVerkuendung.view.vue"),
      },
      {
        path: `${createDokumentExpressionEliPathParameter()}`,
        name: "VerkuendungDetail",
        component: () =>
          import(
            "@/views/verkuendungen/verkuendungDetail/VerkuendungDetail.view.vue"
          ),
      },
      {
        path: `${createDokumentExpressionEliPathParameter()}/zeitgrenzen`,
        name: "VerkuendungZeitgrenzen",
        component: () =>
          import(
            "@/views/verkuendungen/verkuendungDetail/zeitgrenzen/VerkuendungZeitgrenzen.view.vue"
          ),
      },
    ],
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

  // Legacy routes - these are leftovers from an earlier version of the
  // application and will be removed soon
  {
    path: "/amending-laws",
    children: [
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

  // Legacy routes - these are leftovers from an earlier version of the
  // application and will be removed soon
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

  // Legacy routes - these are leftovers from an earlier version of the
  // application and will be removed soon
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
