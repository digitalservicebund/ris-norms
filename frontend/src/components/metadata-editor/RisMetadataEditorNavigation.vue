<script setup lang="ts">
import { useGetNormToc } from "@/services/tocService"
import type { TocItem } from "@/types/toc"
import { useRouter } from "vue-router"
import { useElementId } from "@/composables/useElementId"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import RisTableOfContents from "@/components/RisTableOfContents.vue"
import { computed } from "vue"

const router = useRouter()
const { sidebarNavigationId } = useElementId()

const props = defineProps<{
  routeNameEditorElement: string
  routeNameEditorOutlineElement: string
  routeNameEditorRahmen: string
  normExpressionEli: NormExpressionEli
  selectedEId: string | undefined
}>()

const {
  data: tocItems,
  isFetching: tocIsLoading,
  error: tocError,
} = useGetNormToc(() =>
  DokumentExpressionEli.fromNormExpressionEli(props.normExpressionEli),
)

const elementTypes = computed<Map<string, string>>(() => {
  const typeMap = new Map<string, string>()
  function collectTypes(item: TocItem) {
    typeMap.set(item.id, item.type)
    item.children?.forEach((child) => collectTypes(child))
  }
  tocItems.value?.forEach((item) => collectTypes(item))
  return typeMap
})

const handleTocSelect = ({ eId }: { eId: string }) => {
  const elementType = elementTypes.value.get(eId)

  if (!elementType) {
    return
  }

  router.push({
    name: ["article", "conclusions", "preamble", "preface"].includes(
      elementType,
    )
      ? props.routeNameEditorElement
      : props.routeNameEditorOutlineElement,
    params: { eid: eId },
  })
}
</script>

<template>
  <aside
    class="w-full flex-1 overflow-auto px-8 pt-16"
    :aria-labelledby="sidebarNavigationId"
  >
    <span :id="sidebarNavigationId" class="sr-only">Inhaltsverzeichnis</span>

    <!-- Frame link -->
    <router-link
      :to="{ name: props.routeNameEditorRahmen }"
      class="flex w-full justify-start border-l-4 border-transparent px-20 py-10 hover:bg-blue-200 hover:underline focus:bg-blue-200 focus:underline focus-visible:outline focus-visible:outline-4 focus-visible:outline-offset-4 focus-visible:outline-blue-800"
      exact-active-class="font-bold underline bg-blue-200 border-l-blue-800"
    >
      Rahmen
    </router-link>

    <div class="min-w-max">
      <hr class="mx-16 my-8 border-t border-gray-400" />

      <RisTableOfContents
        :aria-labelledby="sidebarNavigationId"
        :toc="tocItems"
        :is-fetching="tocIsLoading"
        :fetch-error="tocError"
        :selected-e-id="selectedEId"
        @select="handleTocSelect"
      />
    </div>
  </aside>
</template>
