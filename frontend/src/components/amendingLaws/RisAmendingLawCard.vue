<script setup lang="ts">
import { computed } from "vue"
import IcBaselineChevronRight from "~icons/ic/baseline-chevron-right"

const props = defineProps<{
  /** ELI of the norm. */
  eli: string

  /** Name in the bibliographic record of the norm. */
  frbrName?: string

  /** Number in the bibliographic record of the norm. */
  frbrNumber?: string

  /** Announcement date in the bibliographic record of the norm. */
  frbrDateVerkuendung?: string
}>()

const gazetteOrMediumUpper = computed(() => {
  return props.frbrName ?? ""
})

const pageOrEdition = computed(() => {
  if (props.frbrNumber && props.frbrNumber.startsWith("s")) {
    return `S. ${props.frbrNumber.replace("s", "")}`
  } else {
    return `Nr. ${props.frbrNumber}`
  }
})

const frbrDateVerkuendungYear = computed(() =>
  props.frbrDateVerkuendung?.substring(0, 4),
)

const frbrDateVerkuendungGerman = computed(() => {
  if (props.frbrDateVerkuendung == null) {
    return null
  }

  const options: Intl.DateTimeFormatOptions = {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  }
  return new Date(props.frbrDateVerkuendung).toLocaleDateString(
    "de-DE",
    options,
  )
})
</script>

<template>
  <div class="flex justify-between bg-white p-16 hover:bg-blue-200">
    <div class="flex items-center">
      <span class="ds-label-02-bold w-128 flex-none whitespace-nowrap">
        {{ gazetteOrMediumUpper }} {{ frbrDateVerkuendungYear }}
        {{ pageOrEdition }}
      </span>

      <span class="publication-date ml-40">
        {{ frbrDateVerkuendungGerman }}
      </span>
    </div>

    <button class="text-blue-800">
      <IcBaselineChevronRight class="h-24 w-24" />
      <span class="sr-only">Details anzeigen</span>
    </button>
  </div>
</template>
