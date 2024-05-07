<script setup lang="ts">
import RisAmendingLawInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
import { useAmendingLaw } from "@/composables/useAmendingLaw"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { computed, ref, watch, WritableComputedRef } from "vue"
import { useTargetLawXml } from "@/composables/useTargetLawXml"
import { useTimeBoundaryPathParameter } from "@/composables/useTimeBoundaryPathParameter"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import { useArticlesChangedAtTimeBoundary } from "@/composables/useArticlesChangedAtTimeBoundary"
import { useRouter } from "vue-router"
import { useTemporalData } from "@/composables/useTemporalData"
import dayjs from "dayjs"

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

const { timeBoundaries } = useTemporalData(affectedDocumentEli)

const selectedTimeBoundary: WritableComputedRef<string> =
  useTimeBoundaryPathParameter()

const selectedTimeBoundaryEid = computed(
  () =>
    timeBoundaries.value.find(
      (boundary) => boundary.date === selectedTimeBoundary.value,
    )?.eventRefEid,
)

// choose the first time boundary if none is selected so far
watch(
  timeBoundaries,
  () => {
    if (selectedTimeBoundary.value === "" && timeBoundaries.value.length > 0) {
      router.replace({
        params: {
          timeBoundary: timeBoundaries.value[0].date,
        },
      })
    }
  },
  { immediate: true },
)

const articles = useArticlesChangedAtTimeBoundary(
  affectedDocumentEli,
  selectedTimeBoundaryEid,
  amendingLawEli,
)

// TODO: (Malte Laukötter, 2024-04-26) how to handle if a time boundary changes and the currently selected article has no changes for the newly selected zeitgrenze

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
      class="col-span-1 flex h-[calc(100dvh-5rem-5rem)] w-full flex-col overflow-auto border-r border-gray-400 bg-white"
      aria-labelledby="sidebarNavigation"
    >
      <span id="sidebarNavigation" hidden>SideBar Navigation</span>

      <div class="px-16 py-8">
        <label for="timeBoundarySelect">
          <span class="ds-label-03-reg">Zeitgrenze</span>

          <select
            id="timeBoundarySelect"
            v-model="selectedTimeBoundary"
            class="ds-select ds-select-small"
          >
            <option
              v-for="timeBoundary in timeBoundaries"
              :key="timeBoundary.eventRefEid"
              :value="timeBoundary.date"
            >
              {{ dayjs(timeBoundary.date).format("DD.MM.YYYY") }}
            </option>
          </select>
        </label>
      </div>

      <router-link
        class="ds-label-01-reg px-16 py-8 hover:bg-blue-200 hover:underline focus:bg-blue-200 focus:underline"
        exact-active-class="font-bold"
        :to="{
          name: 'AmendingLawAffectedDocumentRahmenEditor',
          params: { timeBoundary: selectedTimeBoundary },
        }"
      >
        Rahmen
      </router-link>

      <hr class="mx-16 border-b border-gray-400" />

      <router-link
        v-for="article in articles"
        :key="article.eid"
        class="ds-label-02-reg block px-16 py-8 hover:bg-blue-200 hover:underline focus:bg-blue-200 focus:underline"
        active-class="font-bold"
        :to="{
          name: 'AmendingLawAffectedDocumentArticleEditor',
          params: { eid: article.eid, timeBoundary: selectedTimeBoundary },
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
