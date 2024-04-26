<script setup lang="ts">
import RisAmendingLawInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
import { useAmendingLaw } from "@/composables/useAmendingLaw"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { Article } from "@/types/article"
import { AmendingLawTemporalDataReleaseResponse } from "@/types/amendingLawTemporalDataReleaseResponse"
import { useRoute, useRouter } from "vue-router"
import { computed, Ref, ref, watch, WritableComputedRef } from "vue"

const router = useRouter()
const route = useRoute()

const amendingLawEli = useEliPathParameter()

const amendingLaw = useAmendingLaw(amendingLawEli)

const zeitgrenzen: Ref<AmendingLawTemporalDataReleaseResponse[]> = ref([
  {
    date: "23.10.2023",
    eid: "unknown-eid-1",
  },
  {
    date: "02.12.2023",
    eid: "unknown-eid-2",
  },
  {
    date: "03.02.2024",
    eid: "unknown-eid-3",
  },
])

// Current selected zeitgrenze that keeps it in sync with the route parameter
const selectedZeitgrenze: WritableComputedRef<string> = computed({
  get() {
    if (Array.isArray(route.params.zeitgrenze)) {
      return route.params.zeitgrenze[0]
    }

    return route.params.zeitgrenze
  },
  set(zeitgrenze) {
    router.push({
      params: {
        zeitgrenze,
      },
    })
  },
})

// choose the first zeitgrenze if none is selected so far
watch(
  zeitgrenzen,
  () => {
    if (selectedZeitgrenze.value === "" && zeitgrenzen.value.length > 0) {
      selectedZeitgrenze.value = zeitgrenzen.value[0].eid
    }
  },
  { immediate: true },
)

const articles: Article[] = [
  {
    eid: "hauptteil-1_art-1",
    title: "Passpflicht",
    enumeration: "1",
  },
  {
    eid: "hauptteil-1_art-3",
    title: "Befreiung von der Passpflichtblablabla",
    enumeration: "3",
  },
]
</script>

<template>
  <div
    v-if="amendingLaw"
    class="grid-rows-[5rem, 1fr] grid bg-gray-100"
    style="grid-template-columns: 16rem 1fr"
  >
    <RisAmendingLawInfoHeader class="col-span-2" :amending-law="amendingLaw" />

    <aside
      class="col-span-1 flex h-full w-full flex-col gap-[1rem] overflow-auto border-r border-gray-400 bg-white p-[1rem]"
      aria-labelledby="sidebarNavigation"
    >
      <span id="sidebarNavigation" hidden>SideBar Navigation</span>

      <div>
        <label for="zeitgrenzeSelect">
          <span class="ds-label-03-reg">Zeitgrenze</span>

          <select
            id="zeitgrenzeSelect"
            v-model="selectedZeitgrenze"
            class="ds-select ds-select-small"
          >
            <option
              v-for="zeitgrenze in zeitgrenzen"
              :key="zeitgrenze.eid"
              :value="zeitgrenze.eid"
            >
              {{ zeitgrenze.date }}
            </option>
          </select>
        </label>
      </div>

      <router-link
        class="ds-label-01-reg hover:bg-blue-200 hover:underline focus:bg-blue-200 focus:underline"
        exact-active-class="font-bold"
        :to="{
          name: 'AmendingLawAffectedDocumentRahmenEditor',
          params: { zeitgrenze: selectedZeitgrenze },
        }"
      >
        Rahmen
      </router-link>

      <hr class="border-b border-gray-400" />

      <router-link
        v-for="article in articles"
        :key="article.eid"
        class="ds-label-02-reg block hover:bg-blue-200 hover:underline focus:bg-blue-200 focus:underline"
        active-class="font-bold"
        :to="{
          name: 'AmendingLawAffectedDocumentArticleEditor',
          params: { eid: article.eid, zeitgrenze: selectedZeitgrenze },
        }"
      >
        <span class="block overflow-hidden text-ellipsis whitespace-nowrap"
          >§{{ article.enumeration }} {{ article.title }}</span
        >
      </router-link>
    </aside>

    <RouterView></RouterView>
  </div>
  <div v-else>Laden...</div>
</template>
