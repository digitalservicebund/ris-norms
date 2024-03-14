<script setup lang="ts">
import RisTextButton from "@/components/controls/RisTextButton.vue"
import IconCheck from "~icons/ic/baseline-check"
import IconErrorOutline from "~icons/ic/baseline-error-outline"
import { computed, ref } from "vue"
import { useShowAlert } from "@/composables/useAlerts"

/**
 * The result of a plausibility check of an amending law
 */
type PlausibilityCheckResult = {
  /**
   * True if the amending law is plausible, false otherwise
   */
  plausible: boolean
  /**
   * A message containing more information. E.g. an error message if the amending law is not plausible or a statement
   * about the LDML.de Version the amending law was found plausible for.
   */
  message: string
}

/**
 * The result of the plausibility check
 */
const plausibilityCheckResult = ref<PlausibilityCheckResult>({
  plausible: true,
  message: "LDML entspricht dem Standard 1.6",
})

function onCheckPlausibility() {
  // TODO: (Malte Laukötter, 2024-03-12) Do a plausibility check
  if (plausibilityCheckResult.value.plausible) {
    plausibilityCheckResult.value = {
      plausible: false,
      message: "LDML Fehler in Zeile 232",
    }
  } else {
    plausibilityCheckResult.value = {
      plausible: true,
      message: "LDML entspricht dem Standard 1.6",
    }
  }
}

const showAlert = useShowAlert()

// TODO: (Malte Laukötter, 2024-03-12) this is probably a property of the amending law in the future
const publishedAt = ref<Date | null>(null)

function onPublish() {
  // TODO: (Malte Laukötter, 2024-03-12) Call an endpoint to publish the amending law
  if (Math.random() > 0.5) {
    publishedAt.value = new Date()

    showAlert({
      variant: "success",
      message: "Die Datei wurde erfolgreich abgegeben",
    })
  } else {
    showAlert({
      variant: "error",
      message: "Gesetz konnte nicht als XML abgegeben werden",
    })
  }
}

const publishedAtDateTime = computed(() => publishedAt.value?.toISOString())
const publishedAtTimeString = computed(() =>
  publishedAt.value?.toLocaleTimeString("de-DE", {
    hour: "2-digit",
    minute: "2-digit",
  }),
)
const publishedAtDateString = computed(() =>
  publishedAt.value?.toLocaleDateString("de-DE", {
    dateStyle: "medium",
  }),
)
</script>

<template>
  <div class="flex flex-col">
    <section class="flex flex-col gap-40">
      <h1 class="ds-heading-02-reg">Abgabe</h1>

      <section class="flex gap-20">
        <h2 class="w-1/4">1. Plausibilitätsprüfung</h2>

        <div class="flex flex-grow gap-8">
          <IconCheck
            v-if="plausibilityCheckResult.plausible"
            class="text-green-700"
          />
          <IconErrorOutline
            v-if="!plausibilityCheckResult.plausible"
            class="text-red-900"
          />
          <span>{{ plausibilityCheckResult.message }}</span>
        </div>

        <RisTextButton
          variant="tertiary"
          size="small"
          label="Erneut prüfen"
          @click="onCheckPlausibility"
        />
      </section>

      <hr class="border-t-gray-600" />

      <section class="flex gap-20">
        <h2 class="w-1/4">2. Abgabe veranlassen</h2>

        <div class="flex flex-grow gap-8">
          <span>Gesamtes Änderungsgesetz im Portal veröffentlichen.</span>
        </div>
      </section>

      <hr class="border-t-gray-600" />

      <RisTextButton
        class="w-fit"
        variant="primary"
        size="small"
        label="Jetzt Abgeben"
        @click="onPublish"
      />
    </section>

    <section class="mt-[60px] flex flex-col gap-40">
      <h1 class="ds-heading-02-reg">Letzte Abgabe</h1>

      <span v-if="publishedAt"
        >Dieses Änderungsgesetz wurde zuletzt abgegeben am
        <time :datetime="publishedAtDateTime">
          {{ publishedAtDateString }} um {{ publishedAtTimeString }} Uhr
        </time>
      </span>
      <span v-else>Das Gesetz wurde noch nicht veröffentlicht.</span>
    </section>
  </div>
</template>
