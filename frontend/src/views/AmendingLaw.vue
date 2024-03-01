<script lang="ts" setup>
import { RouterView } from "vue-router"
import RisNavbarSide, {
  LevelOneMenuItem,
} from "@/components/controls/RisNavbarSide.vue"
import RisInfoHeader from "@/components/controls/RisInfoHeader.vue"
import { computed, onMounted, onUnmounted } from "vue"
import { useAmendingLawsStore } from "@/store/loadAmendingLawStore"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { storeToRefs } from "pinia"

const menuItems: LevelOneMenuItem[] = [
  {
    label: "Vorgang",
    route: { name: "AmendingLaw" },
    children: [
      {
        label: "Artikelübersicht",
        route: { name: "AmendingLawArticles" },
      },
      {
        label: "Betroffene Normenkomplexe",
        route: { name: "AmendingLawAffectedDocuments" },
      },
    ],
  },
]

const eli = useEliPathParameter()

const amendingLawsStore = useAmendingLawsStore()
const { loadedAmendingLaw } = storeToRefs(amendingLawsStore)

onMounted(() => amendingLawsStore.loadAmendingLawByEli(eli.value))
onUnmounted(() => (loadedAmendingLaw.value = undefined))

const heading = computed(() => {
  const publicationYear = loadedAmendingLaw.value?.publicationDate.substring(
    0,
    4,
  )
  if (loadedAmendingLaw.value?.printAnnouncementGazette) {
    return `${loadedAmendingLaw.value?.printAnnouncementGazette} ${publicationYear} S. ${loadedAmendingLaw.value?.printAnnouncementPage}`
  } else if (loadedAmendingLaw.value?.digitalAnnouncementEdition) {
    return `${loadedAmendingLaw.value?.digitalAnnouncementMedium} ${publicationYear} Nr. ${loadedAmendingLaw.value?.digitalAnnouncementEdition}`
  } else {
    return ""
  }
})
</script>

<template>
  <div class="flex min-h-screen flex-col bg-gray-100">
    <RisInfoHeader :heading="heading" :subtitle="loadedAmendingLaw?.title" />
    <div class="flex">
      <RisNavbarSide
        class="min-h-screen flex-none border-r border-gray-400 bg-white"
        go-back-label="Zurück"
        :go-back-route="{ name: 'Home' }"
        :menu-items="menuItems"
      />
      <div class="w-full flex-1 p-40">
        <RouterView />
      </div>
    </div>
  </div>
</template>
