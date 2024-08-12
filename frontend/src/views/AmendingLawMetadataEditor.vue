<script setup lang="ts">
import RisEmptyState from "@/components/RisEmptyState.vue"
import RisCallout from "@/components/controls/RisCallout.vue"
import RisHeader, {
  HeaderBreadcrumb,
} from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useTimeBoundaryPathParameter } from "@/composables/useTimeBoundaryPathParameter"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetElements } from "@/services/elementService"
import { useGetNorm } from "@/services/normService"
import { useGetTemporalDataTimeBoundaries } from "@/services/temporalDataService"
import dayjs from "dayjs"
import { computed, ref, watchEffect } from "vue"
import { useRouter } from "vue-router"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"

const amendingLawEli = useEliPathParameter()
const affectedDocumentEli = useEliPathParameter("affectedDocument")

const router = useRouter()

const {
  data: amendingLaw,
  isFetching: amendingLawIsLoading,
  error: amendingLawError,
} = useGetNorm(amendingLawEli)

/* -------------------------------------------------- *
 * Header                                             *
 * -------------------------------------------------- */

const { data: affectedDocument, error: affectedDocumentError } =
  useGetNorm(affectedDocumentEli)

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "amendingLaw",
    title: () =>
      amendingLaw.value
        ? (getFrbrDisplayText(amendingLaw.value) ?? "...")
        : "...",
    to: `/amending-laws/${amendingLawEli.value}/affected-documents`,
  },
  {
    key: "affectedDocument",
    title: () => affectedDocument.value?.shortTitle ?? "...",
  },
  { key: "metadataEditor", title: "Metadaten dokumentieren" },
])

/* -------------------------------------------------- *
 * Sidebar                                            *
 * -------------------------------------------------- */

const {
  data: timeBoundaries,
  isFetching: timeBoundariesIsFetching,
  error: timeBoundariesError,
  isFinished: timeBoundariesIsFinished,
} = useGetTemporalDataTimeBoundaries(affectedDocumentEli, {
  amendedBy: amendingLawEli,
})

const sortedTimeBoundaries = computed(() =>
  (timeBoundaries.value ?? []).toSorted((a, b) => {
    if (a.date < b.date) return -1
    else if (a.date > b.date) return 1
    else return 0
  }),
)

const { timeBoundary: selectedTimeBoundary } = useTimeBoundaryPathParameter()

watchEffect(() => {
  if (!timeBoundariesIsFinished.value || !timeBoundaries.value) return
  else if (!timeBoundaries.value.length) {
    // No time boundaries at all
    router.push({ name: "NotFound" })
  } else if (
    selectedTimeBoundary.value &&
    !timeBoundaries.value.some((i) => i.date === selectedTimeBoundary.value)
  ) {
    // Selected time boundary doesn't exist
    router.push({ name: "NotFound" })
  } else if (!selectedTimeBoundary.value) {
    // No time boundary selected -> select initial value
    selectedTimeBoundary.value = timeBoundaries.value[0].date
  }
})

const {
  data: elements,
  isFetching: elementsIsLoading,
  error: elementsError,
} = useGetElements(
  affectedDocumentEli,
  [
    "article",
    "conclusions",
    "preamble",
    "preface",
    "book",
    "chapter",
    "part",
    "subtitle",
    "title",
  ],
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

    <div v-else-if="amendingLawError" class="p-24">
      <RisErrorCallout
        title="Das Gesetz konnte nicht geladen werden."
        variant="error"
      />
    </div>

    <div v-else-if="affectedDocumentError" class="p-24">
      <RisErrorCallout title="Das Gesetz konnte nicht geladen werden." />
    </div>

    <div v-else-if="timeBoundariesError" class="p-24">
      <RisErrorCallout title="Die Zeitgrenzen konnten nicht geladen werden." />
    </div>

    <div
      v-else-if="amendingLaw"
      class="grid h-full grid-cols-[16rem,1fr] grid-rows-[5rem,1fr] bg-gray-100"
    >
      <RisHeader class="col-span-2" :breadcrumbs>
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

          <RisCallout
            v-if="!selectedTimeBoundary"
            variant="warning"
            title="Keine Zeitgrenze ausgewählt."
            class="mx-16 mb-8"
          />

          <!-- Frame link -->
          <!-- Render conditionally on selectedTimeBoundary to prevent missing param errors in the route -->
          <router-link
            v-if="selectedTimeBoundary"
            :to="{
              name: 'AmendingLawMetadataEditorRahmen',
              params: { timeBoundary: selectedTimeBoundary },
            }"
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
            title="Artikel konnten nicht geladen werden."
            class="mx-16"
          />

          <RisEmptyState
            v-else-if="!elements?.length"
            text-content="Keine Artikel gefunden."
            class="mx-16"
            variant="simple"
          />

          <!-- Render conditionally on selectedTimeBoundary to prevent missing param errors in the route -->
          <template v-if="selectedTimeBoundary">
            <router-link
              v-for="element in elements"
              :key="element.eid"
              :to="{
                name:
                  element.type === 'article' ||
                  element.type === 'conclusions' ||
                  element.type === 'preamble' ||
                  element.type === 'preface'
                    ? 'AmendingLawMetadataEditorElement'
                    : 'AmendingLawMetadataEditorOutlineElement',
                params: {
                  eid: element.eid,
                  timeBoundary: selectedTimeBoundary,
                },
              }"
              active-class="font-bold underline bg-blue-200"
              class="ds-label-02-reg block px-16 py-8 hover:bg-blue-200 hover:underline focus:bg-blue-200 focus:underline"
            >
              <span
                class="block overflow-hidden text-ellipsis whitespace-nowrap"
              >
                {{ element.title }}
              </span>
            </router-link>
          </template>
        </aside>

        <RouterView />
      </RisHeader>
    </div>
  </div>
</template>
