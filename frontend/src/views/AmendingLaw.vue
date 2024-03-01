<script lang="ts" setup>
import { RouterView } from "vue-router"
import RisNavbarSide, {
  LevelOneMenuItem,
} from "@/components/controls/RisNavbarSide.vue"
import RisInfoHeader from "@/components/controls/RisInfoHeader.vue"
import { computed } from "vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useAmendingLaw } from "@/composables/useAmendingLaw"

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
const amendingLaw = useAmendingLaw(eli)

const heading = computed(() => {
  const publicationYear = amendingLaw.value?.publicationDate.substring(0, 4)
  if (amendingLaw.value?.printAnnouncementGazette) {
    return `${amendingLaw.value?.printAnnouncementGazette} ${publicationYear} S. ${amendingLaw.value?.printAnnouncementPage}`
  } else if (amendingLaw.value?.digitalAnnouncementEdition) {
    return `${amendingLaw.value?.digitalAnnouncementMedium} ${publicationYear} Nr. ${amendingLaw.value?.digitalAnnouncementEdition}`
  } else {
    return ""
  }
})
</script>

<template>
  <div class="flex min-h-screen flex-col bg-gray-100">
    <RisInfoHeader :heading="heading" :subtitle="amendingLaw?.title" />
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
