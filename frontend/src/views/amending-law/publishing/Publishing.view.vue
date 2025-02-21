<script setup lang="ts">
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"
import { useHeaderContext } from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useReleases } from "@/views/amending-law/publishing/useReleases"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import Button from "primevue/button"
import Message from "primevue/message"
import { computed, onUnmounted } from "vue"

const { pushBreadcrumb } = useHeaderContext()
const cleanupBreadcrumbs = pushBreadcrumb({ title: "Abgabe" })
onUnmounted(() => cleanupBreadcrumbs())

const eli = useDokumentExpressionEliPathParameter()
const {
  data: releases,
  release: {
    execute: releaseAmendingLaw,
    isFetching: isReleasing,
    error: releaseError,
  },
  isFetching,
  error: fetchError,
  statusCode: fetchStatusCode,
} = useReleases(eli)

async function onRelease() {
  await releaseAmendingLaw()
}

const latestRelease = computed(
  () =>
    releases.value?.toSorted(
      (a, b) =>
        new Date(b.releaseAt).valueOf() - new Date(a.releaseAt).valueOf(),
    )?.[0],
)

const releasedAt = computed(() =>
  latestRelease.value?.releaseAt
    ? new Date(latestRelease.value?.releaseAt)
    : null,
)
const publishedAtDateTime = computed(() => releasedAt.value?.toISOString())

const publishedAtTimeString = computed(() =>
  releasedAt.value?.toLocaleTimeString("de-DE", {
    hour: "2-digit",
    minute: "2-digit",
  }),
)
const publishedAtDateString = computed(() =>
  releasedAt.value?.toLocaleDateString("de-DE", {
    dateStyle: "medium",
  }),
)

const downloadLinks = computed(() => {
  const norms = latestRelease.value?.norms
  if (!norms) return []

  return norms.map((norm) => ({
    url: `/api/v1/norms/${norm}`,
    title: norm,
  }))
})
</script>

<template>
  <div class="flex flex-col p-24">
    <section class="flex flex-col gap-20">
      <h1 class="ris-heading2-regular">Abgabe</h1>

      <div
        v-if="isFetching || isReleasing"
        class="mt-20 flex items-center justify-center"
      >
        <RisLoadingSpinner></RisLoadingSpinner>
      </div>

      <div v-else-if="releaseError">
        <RisErrorCallout :error="releaseError" />
      </div>

      <div v-else-if="fetchError && fetchStatusCode !== 404">
        <RisErrorCallout :error="fetchError" />
      </div>

      <Message v-else-if="releasedAt" severity="warn">
        <p class="ris-label2-bold my-6">
          Die Abgabe ist aktuell als Prototyp verfügbar.
        </p>
        <p class="ris-label2-regular mb-6">
          Dieses Änderungsgesetz wurde zuletzt abgegeben am
          <time :datetime="publishedAtDateTime">
            {{ publishedAtDateString }} um {{ publishedAtTimeString }} Uhr. Die
            aktuelle Version kann hier eingesehen werden:
          </time>
        </p>
        <ul class="ris-label2-regular list-disc pl-20">
          <li v-for="download in downloadLinks" :key="download.url">
            <a :href="download.url" target="_blank" class="underline">{{
              download.title
            }}</a>
          </li>
        </ul>
      </Message>

      <span v-else>Das Gesetz wurde noch nicht veröffentlicht.</span>
      <Button
        class="w-fit"
        label="Jetzt abgeben"
        :loading="isReleasing"
        @click="onRelease"
      />
    </section>
  </div>
</template>
