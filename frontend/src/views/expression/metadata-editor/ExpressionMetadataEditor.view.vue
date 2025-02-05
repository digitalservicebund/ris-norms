<script setup lang="ts">
import RisEmptyState from "@/components/controls/RisEmptyState.vue"
import RisHeader, {
  HeaderBreadcrumb,
} from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetElements } from "@/services/elementService"
import { computed, ref } from "vue"
import { useRoute } from "vue-router"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import Tree from "primevue/tree"

const route = useRoute()
const eli = computed(() => route.params.eli)
const expandedKeys = ref<Record<string, boolean>>({})
if (!expandedKeys.value) {
  expandedKeys.value = {}
}
const selectionKeys = ref<Record<string, boolean>>({})
const expression = ref(null)
const expressionIsLoading = ref(false)
const expressionError = ref(null)

const expressionData = computed(
  () =>
    expression.value ?? {
      title: "Dummy Expression Title",
      content: "This is a placeholder for the full expression content.",
    },
)

/* -------------------------------------------------- *
 * Header                                             *
 * -------------------------------------------------- */
const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "expression",
    title: () => getFrbrDisplayText(expressionData.value) ?? "...",
    to: `/eli/${eli.value}`,
  },
  { key: "metadataEditor", title: "Metadaten dokumentieren" },
])

/* -------------------------------------------------- *
 * Sidebar                                            *
 * -------------------------------------------------- */

const {
  data: elements,
  isFetching: elementsIsLoading,
  error: elementsError,
} = useGetElements(eli, [
  "article",
  "conclusions",
  "preamble",
  "preface",
  "book",
  "chapter",
  "part",
  "section",
  "subsection",
  "subtitle",
  "title",
])

const elementData = computed(
  () =>
    elements.value ?? [
      { eid: "1", title: "Kapitel 1", type: "article" },
      { eid: "2", title: "Kapitel 2", type: "book" },
      { eid: "3", title: "Annex 1", type: "annex" },
      { eid: "4", title: "Appendix A", type: "appendix" },
    ],
)

const elementLinks = computed(() => {
  const items = elements.value ?? [
    { eid: "1", title: "Kapitel 1", type: "article" },
    { eid: "2", title: "Kapitel 2", type: "book" },
  ]

  return items.map((el) => ({
    eid: el.eid,
    title: el.title,
    type: el.type,
    route: {
      name:
        el.type === "article" ||
        el.type === "conclusions" ||
        el.type === "preamble" ||
        el.type === "preface"
          ? "ExpressionMetadataEditorElement"
          : "ExpressionMetadataEditorOutlineElement",
      params: {
        eli: eli.value,
        eid: el.eid,
      },
    },
  }))
})

const treeNodes = computed<TreeNode[]>(() => {
  if (!elementLinks.value) return []

  return elementLinks.value.map((el) => ({
    key: el.eid,
    label: el.title,
    route: el.route
      ? {
          name: el.route.name,
          params: {
            eli: eli.value ?? "default-eli", // Ensure eli is never undefined
            eid: el.eid,
          },
        }
      : undefined,
    children: el.children ?? [],
  }))
})

interface TreeNode {
  key: string
  label: string
  route?: { name: string; params: Record<string, string | number> }
  secondaryLabel?: string
  children?: TreeNode[]
}

console.log("Tree Nodes:", treeNodes.value)
</script>

<template>
  <div class="h-[calc(100dvh-5rem)] bg-gray-100">
    <div
      v-if="expressionIsLoading"
      class="flex h-full items-center justify-center"
    >
      <RisLoadingSpinner />
    </div>

    <div v-else-if="expressionError" class="p-24">
      <RisErrorCallout :error="expressionError" />
    </div>

    <div
      v-else
      class="grid h-full grid-cols-[16rem,1fr] grid-rows-[5rem,1fr] bg-gray-100"
    >
      <RisHeader class="col-span-2" :breadcrumbs>
        <aside
          class="col-span-1 flex h-[calc(100dvh-5rem-5rem)] w-full flex-col overflow-auto border-r border-gray-400 bg-white px-8 pt-16"
        >
          <span id="sidebarNavigation" class="sr-only">Inhaltsverzeichnis</span>

          <!-- Frame link -->
          <router-link
            :to="{ name: 'ExpressionMetadataEditorRahmen' }"
            class="px-16 py-8 hover:bg-blue-200 hover:underline focus:bg-blue-200 focus:underline"
            exact-active-class="font-bold underline bg-blue-200"
          >
            Rahmen
          </router-link>
          <router-link
            :to="{ name: 'ExpressionMetadataEditorElement' }"
            class="px-16 py-8 hover:bg-blue-200 hover:underline focus:bg-blue-200 focus:underline"
            exact-active-class="font-bold underline bg-blue-200"
          >
            Element
          </router-link>
          <router-link
            :to="{ name: 'ExpressionMetadataEditorOutlineElement' }"
            class="px-16 py-8 hover:bg-blue-200 hover:underline focus:bg-blue-200 focus:underline"
            exact-active-class="font-bold underline bg-blue-200"
          >
            Outline Element
          </router-link>
          <hr class="mx-16 my-8 border-t border-gray-400" />

          <!-- Content links -->
          <div
            v-if="elementsIsLoading"
            class="m-16 flex items-center justify-center"
          >
            <RisLoadingSpinner />
          </div>

          <RisErrorCallout
            v-else-if="elementsError"
            :error="elementsError"
            class="mx-16"
          />

          <RisEmptyState
            v-else-if="!elementData.length"
            text-content="Keine Artikel gefunden."
            class="mx-16"
            variant="simple"
          />

          <Tree
            v-else
            v-model:expanded-keys="expandedKeys"
            v-model:selection-keys="selectionKeys"
            :value="treeNodes"
            selection-mode="single"
          />
        </aside>

        <RouterView />
      </RisHeader>
    </div>
  </div>
</template>
