<script setup lang="ts">
import RisEmptyState from "@/components/controls/RisEmptyState.vue"
import RisHeader from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useGetElements } from "@/services/elementService"
import { ComputedRef, computed, ref } from "vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import Tree from "primevue/tree"

const expressionEli = useEliPathParameter()

const {
  data: elements,
  isFetching: elementsIsLoading,
  error: elementsError,
} = useGetElements(expressionEli, [
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

const expandedKeys = ref<Record<string, boolean>>({})
const selectionKeys = ref<Record<string, boolean>>({})

const elementLinks = computed(
  () =>
    elements.value?.map((el) => ({
      eid: el.eid,
      title: el.title,
      type: el.type,
      route: {
        name: ["article", "conclusions", "preamble", "preface"].includes(
          el.type,
        )
          ? "ExpressionMetadataEditorElement"
          : "ExpressionMetadataEditorOutlineElement",
        params: { eid: el.eid },
      },
    })) ?? [],
)

interface TreeNode {
  key: string
  label: string
  route?: { name: string; params: { expressionEli: string; eid: string } }
  children?: TreeNode[]
}

const treeNodes: ComputedRef<TreeNode[]> = computed(() => {
  return elementLinks.value.map((el) => ({
    key: el.eid,
    label: el.title,
    route: el.route
      ? {
          name: el.route.name,
          params: {
            expressionEli: expressionEli.value,
            eid: el.eid,
          },
        }
      : undefined,
  }))
})
</script>

<template>
  <div class="h-[calc(100dvh-5rem)] bg-gray-100">
    <div
      class="grid h-full grid-cols-[16rem,1fr] grid-rows-[5rem,1fr] bg-gray-100"
    >
      <RisHeader class="col-span-2">
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
            v-else-if="!elements || !elements.length"
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
