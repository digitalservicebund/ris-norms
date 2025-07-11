import { createDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { createNormWorkEliPathParameter } from "@/composables/useNormWorkEliPathParameter"
import type { RouteRecordRaw, NavigationGuardWithThis } from "vue-router"
import { createRouter, createWebHistory } from "vue-router"
import { toValue } from "vue"
import { useNormGuidService } from "@/services/normGuidService"
import { createNormExpressionEliPathParameter } from "@/composables/useNormExpressionEliPathParameter"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import {
  PATH_PARAMETER_LANGUAGE,
  PATH_PARAMETER_POINT_IN_TIME,
  PATH_PARAMETER_VERSION,
} from "@/lib/eli/NormExpressionEli"

const GUID_ROUTE_PATH = `:guid([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})`

/**
 * Replace a guid in the path of the route with the dokument eli of the expression
 */
const beforeRouteEnterGuidToDokumentExpressionEliRedirect: NavigationGuardWithThis<
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

/**
 * Replace a guid in the path of the route with the norm eli of the expression
 */
const beforeRouteEnterGuidToNormExpressionEliRedirect: NavigationGuardWithThis<
  undefined
> = async (to) => {
  const guid: string = to.params.guid as string

  const { data } = await useNormGuidService(guid)
  const eli = toValue(data)?.eli

  if (eli == null) {
    return { name: "NotFound" }
  }

  return {
    path: to.path.replace(
      guid,
      DokumentExpressionEli.fromString(eli).asNormEli.toString(),
    ),
  }
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
        path: `/verkuendungen/${createNormExpressionEliPathParameter("verkuendung")}/textkonsolidierung/${createDokumentExpressionEliPathParameter("expression")}`,
        name: "VerkuendungExpressionTextkonsolidierungEditor",
        component: () =>
          import(
            "@/views/verkuendungen/verkuendungDetail/textkonsolidierung/ExpressionTextkonsolidierungEditor.view.vue"
          ),
      },
      {
        path: `/verkuendungen/${createNormExpressionEliPathParameter("verkuendung")}/zielnorm/${createNormWorkEliPathParameter("zielnorm")}/abgabe`,
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
        path: `${createNormExpressionEliPathParameter()}`,
        name: "VerkuendungDetail",
        component: () =>
          import(
            "@/views/verkuendungen/verkuendungDetail/VerkuendungDetail.view.vue"
          ),
      },
      {
        path: `${createNormExpressionEliPathParameter()}/zeitgrenzen`,
        name: "VerkuendungZeitgrenzen",
        component: () =>
          import(
            "@/views/verkuendungen/verkuendungDetail/zeitgrenzen/VerkuendungZeitgrenzen.view.vue"
          ),
      },
      {
        path: `${createNormExpressionEliPathParameter()}/zielnormen`,
        name: "VerkuendungZielnormen",
        component: () =>
          import(
            "@/views/verkuendungen/verkuendungDetail/zielnormen/VerkuendungZielnormen.view.vue"
          ),
      },
      {
        path: `${createNormExpressionEliPathParameter()}/expressionen-erzeugen`,
        name: "VerkuendungExpressionenErzeugen",
        component: () =>
          import(
            "@/views/verkuendungen/verkuendungDetail/expressionenErzeugen/VerkuendungExpressionenErzeugen.view.vue"
          ),
      },
      {
        path:
          `${createNormExpressionEliPathParameter("verkuendung")}` +
          `/textkonsolidierung/${GUID_ROUTE_PATH}`,
        component: () => null,
        beforeEnter: beforeRouteEnterGuidToDokumentExpressionEliRedirect,
      },
      {
        path: `${GUID_ROUTE_PATH}/:any(.*)*`,
        component: () => null,
        beforeEnter: beforeRouteEnterGuidToNormExpressionEliRedirect,
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
      {
        path: `${createNormWorkEliPathParameter()}`,
        name: "DatenbankWorkDetail",
        component: () => import("@/views/datenbank/WorkDetail.view.vue"),
        children: [
          {
            path: `:eli${PATH_PARAMETER_POINT_IN_TIME}/:eli${PATH_PARAMETER_VERSION}/:eli${PATH_PARAMETER_LANGUAGE}`,
            name: "DatenbankWorkExpressionDetail",
            component: () =>
              import("@/views/datenbank/WorkExpressionDetail.view.vue"),
          },
        ],
      },
      {
        path: `/datenbank/textbearbeitung/${createDokumentExpressionEliPathParameter()}`,
        name: "DatenbankExpressionTextbearbeitung",
        component: () =>
          import(
            "@/views/datenbank/bestandskorrektur/ExpressionBestandskorrekturEditor.view.vue"
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
  {
    path: `/${GUID_ROUTE_PATH}/:any(.*)*`,
    component: () => null,
    beforeEnter: beforeRouteEnterGuidToDokumentExpressionEliRedirect,
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
