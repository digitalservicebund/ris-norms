<script setup lang="ts">
import RisAmendingLawInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
import { useAmendingLaw } from "@/composables/useAmendingLaw"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { AmendingLawTemporalDataReleaseResponse } from "@/types/amendingLawTemporalDataReleaseResponse"
import { Ref, ref, watch, WritableComputedRef } from "vue"
import { useTargetLawXml } from "@/composables/useTargetLawXml"
import { useZeitgrenzePathParameter } from "@/composables/useZeitgrenzePathParameter"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import { useArticlesChangedAtZeitgrenze } from "@/composables/useArticlesChangedAtZeitgrenze"
import { useRouter } from "vue-router"

const router = useRouter()
const amendingLawEli = useEliPathParameter()
const affectedDocumentEli = useEliPathParameter("affectedDocument")

const amendingLaw = useAmendingLaw(amendingLawEli)

const { xml: targetLawXml, update: updateTargetLawXml } =
  useTargetLawXml(affectedDocumentEli)

const currentTargetLawXml = ref<string | undefined>("")

watch(targetLawXml, () => {
  currentTargetLawXml.value = targetLawXml.value
})

// TODO: (Malte Laukötter, 2024-04-26) load zeitgrenzen
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

const selectedZeitgrenze: WritableComputedRef<string> =
  useZeitgrenzePathParameter()

// choose the first zeitgrenze if none is selected so far
watch(
  zeitgrenzen,
  () => {
    if (selectedZeitgrenze.value === "" && zeitgrenzen.value.length > 0) {
      void router.replace({
        params: {
          zeitgrenze: zeitgrenzen.value[0].eid,
        },
      })
    }
  },
  { immediate: true },
)

// TODO: (Malte Laukötter, 2024-04-29) useArticlesChangedAtZeitgrenze needs implementing loading the correct articles
const articles = useArticlesChangedAtZeitgrenze(
  affectedDocumentEli,
  selectedZeitgrenze,
)

// TODO: (Malte Laukötter, 2024-04-26) how to handle if a zeitgrenze changes and the currently selected article has no changes for the newly selected zeitgrenze

async function handleSave() {
  try {
    if (currentTargetLawXml.value) {
      await updateTargetLawXml(currentTargetLawXml.value)
    }
  } catch (error) {
    alert("Metadaten nicht gespeichert")
    console.error(error)
  }
}
</script>

<template>
  <div
    v-if="amendingLaw"
    class="grid grid-cols-[16rem,1fr] grid-rows-[5rem,1fr] bg-gray-100"
  >
    <RisAmendingLawInfoHeader class="col-span-2" :amending-law="amendingLaw" />

    <aside
      class="col-span-1 flex h-full w-full flex-col gap-16 overflow-auto border-r border-gray-400 bg-white p-16"
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

      <div class="mt-auto">
        <!-- this is only placed here temporarily -->
        <RisTextButton
          :disabled="targetLawXml === currentTargetLawXml"
          size="small"
          full-width
          class="h-fit flex-none self-end"
          label="Speichern"
          @click="handleSave"
        />
      </div>
    </aside>

    <RouterView v-model:xml="currentTargetLawXml"></RouterView>
  </div>
  <div v-else>Laden...</div>
</template>
