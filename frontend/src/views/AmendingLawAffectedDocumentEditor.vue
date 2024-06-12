<script setup lang="ts">
import RisAmendingLawInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTemporalData } from "@/composables/useTemporalData"
import { useTimeBoundaryPathParameter } from "@/composables/useTimeBoundaryPathParameter"
import { useGetElements } from "@/services/elementService"
import { useNormService } from "@/services/normService"
import dayjs from "dayjs"
import { computed, watch } from "vue"

const amendingLawEli = useEliPathParameter()
const affectedDocumentEli = useEliPathParameter("affectedDocument")

const {
  data: amendingLaw,
  isFetching: amendingLawIsLoading,
  error: amendingLawError,
} = useNormService(amendingLawEli, undefined, {
  immediate: true,
})

/* -------------------------------------------------- *
 * Sidebar                                            *
 * -------------------------------------------------- */

const {
  timeBoundaries,
  isFetching: timeBoundariesIsFetching,
  fetchError: timeBoundariesError,
} = useTemporalData(affectedDocumentEli)

const sortedTimeBoundaries = computed(() =>
  timeBoundaries.value.toSorted((a, b) => {
    if (a.date < b.date) return -1
    else if (a.date > b.date) return 1
    else return 0
  }),
)

const { timeBoundary: selectedTimeBoundary } = useTimeBoundaryPathParameter()

// Choose the first time boundary if none is selected so far
watch(
  timeBoundaries,
  (val) => {
    if (!selectedTimeBoundary.value && val.length) {
      selectedTimeBoundary.value = val[0].date
    }
  },
  { immediate: true },
)

const {
  data: elements,
  isFetching: elementsIsLoading,
  error: elementsError,
} = useGetElements(
  affectedDocumentEli,
  ["article", "conclusions", "preamble", "preface"],
  { amendedBy: amendingLawEli },
)
</script>

<template>
  <div class="h-[calc(100dvh-5rem)] bg-gray-100">
    <div
      v-if="amendingLawIsLoading || timeBoundariesIsFetching"
      class="flex h-full items-center justify-center"
    >
      <RisLoadingSpinner />
    </div>

    <div v-else-if="amendingLawError" class="p-40">
      <RisCallout
        title="Das Gesetz konnte nicht geladen werden."
        variant="error"
      />
    </div>

    <div v-else-if="timeBoundariesError" class="p-40">
      <RisCallout
        title="Die Zeitgrenzen konnten nicht geladen werden."
        variant="error"
      />
    </div>

    <div
      v-else-if="amendingLaw"
      class="grid h-full grid-cols-[16rem,1fr] grid-rows-[5rem,1fr] bg-gray-100"
    >
      <RisAmendingLawInfoHeader class="col-span-2" :amending-law />

      <aside
        class="col-span-1 flex h-[calc(100dvh-5rem-5rem)] w-full flex-col overflow-auto border-r border-gray-400 bg-white"
        aria-labelledby="sidebarNavigation"
      >
        <span id="sidebarNavigation" class="sr-only">Inhaltsverzeichnis</span>

        <!-- Time boundary selection -->
        <div class="px-16 pb-20 pt-10">
          <label for="timeBoundarySelect">
            <span class="ds-label-03-reg">Zeitgrenze</span>

            <select
              id="timeBoundarySelect"
              v-model="selectedTimeBoundary"
              class="ds-select ds-select-small"
            >
              <option
                v-for="timeBoundary in sortedTimeBoundaries"
                :key="timeBoundary.eventRefEid"
                :value="timeBoundary.date"
              >
                {{ dayjs(timeBoundary.date).format("DD.MM.YYYY") }}
              </option>
            </select>
          </label>
        </div>

        <!-- Frame link -->
        <router-link
          :to="{
            name: 'AmendingLawAffectedDocumentRahmenEditor',
            params: { timeBoundary: selectedTimeBoundary },
          }"
          class="ds-label-01-reg px-16 py-8 hover:bg-blue-200 hover:underline focus:bg-blue-200 focus:underline"
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

        <RisCallout
          v-else-if="elementsError"
          title="Artikel konnten nicht geladen werden."
          class="mx-16"
          variant="error"
        />

        <RisCallout
          v-else-if="!elements?.length"
          title="Keine Artikel gefunden."
          class="mx-16"
        />

        <router-link
          v-for="element in elements"
          :key="element.eid"
          :to="{
            name: 'AmendingLawAffectedDocumentElementEditor',
            params: { eid: element.eid, timeBoundary: selectedTimeBoundary },
          }"
          active-class="font-bold underline bg-blue-200"
          class="ds-label-02-reg block px-16 py-8 hover:bg-blue-200 hover:underline focus:bg-blue-200 focus:underline"
        >
          <span class="block overflow-hidden text-ellipsis whitespace-nowrap">
            {{ element.title }}
          </span>
        </router-link>
      </aside>

      <RouterView />
    </div>
  </div>
</template>
