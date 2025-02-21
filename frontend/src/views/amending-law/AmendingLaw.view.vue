<script lang="ts" setup>
import RisHeader, {
  HeaderBreadcrumb,
} from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import RisNavbarSide, {
  LevelOneMenuItem,
} from "@/components/controls/RisNavbarSide.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetNorm } from "@/services/normService"
import { ref, watch } from "vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import { RouterView, useRouter } from "vue-router"

const router = useRouter()

const menuItems: LevelOneMenuItem[] = [
  {
    label: "Verkündung",
    route: { name: "AmendingLaw" },
    children: [
      {
        label: "Zeitgrenzen anlegen",
        route: { name: "TemporalData" },
      },
      {
        label: "Artikelübersicht",
        route: { name: "AmendingLawArticles" },
      },
      {
        label: "Verweise",
        route: { name: "References" },
      },
      {
        label: "Metadaten",
        route: { name: "ExpressionMetadataEditorRahmen" },
      },
      {
        label: "Abgabe",
        route: { name: "AmendingLawPublishing" },
      },
    ],
  },
]

const eli = useDokumentExpressionEliPathParameter()
const { data: amendingLaw, isFetching, error } = useGetNorm(eli)
watch(
  () => error.value,
  (err) => {
    if (err && err.status === 404) {
      router.push({ name: "NotFound" })
    }
  },
)

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "amendingLaw",
    title: () =>
      amendingLaw.value
        ? (getFrbrDisplayText(amendingLaw.value) ?? "...")
        : "...",
    to: `/amending-laws/${eli.value}`,
  },
])
</script>

<template>
  <div
    v-if="isFetching"
    class="flex h-[calc(100dvh-5rem)] items-center justify-center"
  >
    <RisLoadingSpinner />
  </div>

  <div v-else-if="error" class="m-24">
    <RisErrorCallout v-if="error.status !== 404" :error />
  </div>

  <div
    v-else
    class="grid h-[calc(100dvh-5rem)] grid-cols-[16rem,1fr] grid-rows-[5rem,1fr] overflow-hidden bg-gray-100"
  >
    <RisHeader
      class="col-span-2"
      :back-destination="{ name: 'Home' }"
      :breadcrumbs
    >
      <RisNavbarSide
        class="col-span-1 w-full overflow-auto border-r border-gray-400 bg-white"
        :menu-items="menuItems"
      />

      <div class="col-span-1 overflow-auto">
        <RouterView />
      </div>
    </RisHeader>
  </div>
</template>
