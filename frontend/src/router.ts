import { createDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { createNormWorkEliPathParameter } from "@/composables/useNormWorkEliPathParameter"
import type { RouteRecordRaw, NavigationGuardWithThis } from "vue-router"
import { createRouter, createWebHistory } from "vue-router"
import { toValue } from "vue"
import { useNormGuidService } from "@/services/normGuidService"

const GUID_ROUTE_PATH = `:guid([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})`

/**
 * Replace a guid in the path of the route with the eli of the expression
 */
const beforeRouteEnterGuidToEliRedirect: NavigationGuardWithThis<
  undefined
> = async (to) => {
  const guid: string = to.params.guid as string

  const { data } = await useNormGuidService(guid)
  const eli = toValue(data)?.eli

  if (eli == null) {
    return { name: "NotFound" }
  }

  return { path: to.path.replace(guid, eli) }
}

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
        path: `/verkuendungen/${createDokumentExpressionEliPathParameter("verkuendung")}/textkonsolidierung/${createDokumentExpressionEliPathParameter("expression")}`,
        name: "VerkuendungExpressionTextkonsolidierungEditor",
        component: () =>
          import(
            "@/views/verkuendungen/verkuendungDetail/textkonsolidierung/ExpressionTextkonsolidierungEditor.view.vue"
          ),
      },
      {
        path: `/verkuendungen/${createDokumentExpressionEliPathParameter("verkuendung")}/zielnorm/${createNormWorkEliPathParameter("zielnorm")}/abgabe`,
        name: "Abgabe",
        component: () => import("@/views/verkuendungen/abgabe/Abgabe.view.vue"),
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
      {
        path: `${createDokumentExpressionEliPathParameter()}/zielnormen`,
        name: "VerkuendungZielnormen",
        component: () =>
          import(
            "@/views/verkuendungen/verkuendungDetail/zielnormen/VerkuendungZielnormen.view.vue"
          ),
      },
      {
        path: `${createDokumentExpressionEliPathParameter()}/expressionen-erzeugen`,
        name: "VerkuendungExpressionenErzeugen",
        component: () =>
          import(
            "@/views/verkuendungen/verkuendungDetail/expressionenErzeugen/VerkuendungExpressionenErzeugen.view.vue"
          ),
      },
      {
        path:
          `${createDokumentExpressionEliPathParameter("verkuendung")}` +
          `/textkonsolidierung/${GUID_ROUTE_PATH}`,
        component: () => null,
        beforeEnter: beforeRouteEnterGuidToEliRedirect,
      },
      {
        path: `${GUID_ROUTE_PATH}/:any(.*)*`,
        component: () => null,
        beforeEnter: beforeRouteEnterGuidToEliRedirect,
      },
    ],
  },

  {
    path: "/datenbank",
    children: [
      {
        path: "",
        name: "Datenbank",
        component: () => import("@/views/datenbank/Datenbank.view.vue"),
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
  {
    path: `/${GUID_ROUTE_PATH}/:any(.*)*`,
    component: () => null,
    beforeEnter: beforeRouteEnterGuidToEliRedirect,
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
