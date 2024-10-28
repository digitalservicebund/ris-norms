<script setup lang="ts">
import RisCallout from "@/components/controls/RisCallout.vue"
import { useHeaderContext } from "@/components/controls/RisHeader.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import Button from "primevue/button"
import { useAmendingLawRelease } from "@/composables/useAmendingLawRelease"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { useGetNormXml } from "@/services/normService"
import { computed, onBeforeUnmount, onUnmounted, ref, watch } from "vue"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"

const { pushBreadcrumb } = useHeaderContext()
const cleanupBreadcrumbs = pushBreadcrumb({ title: "Abgabe" })
onUnmounted(() => cleanupBreadcrumbs())

const eli = useEliPathParameter()
const {
  data: release,
  release: {
    execute: releaseAmendingLaw,
    isFetching: isReleasing,
    error: releaseError,
  },
  isFetching,
  error: fetchError,
  statusCode: fetchStatusCode,
} = useAmendingLawRelease(eli)
const blobUrls = ref<BlobUrlItem[]>([])

interface BlobUrlItem {
  releasedNormEli: string
  blobUrl: string
}

watch(release, async () => {
  if (release.value?.norms) {
    const newBlobUrls = []
    for (const releasedNormEli of release.value.norms) {
      const { data: xmlContent } = await useGetNormXml(releasedNormEli)

      const blob = new Blob([xmlContent.value ?? ""], {
        type: "application/xml",
      })
      const blobUrl = URL.createObjectURL(blob)
      newBlobUrls.push({ releasedNormEli, blobUrl })
    }

    blobUrls.value.forEach((item) => {
      URL.revokeObjectURL(item.blobUrl)
    })
    blobUrls.value = newBlobUrls
  }
})

onBeforeUnmount(() => {
  blobUrls.value.forEach((item) => {
    URL.revokeObjectURL(item.blobUrl)
  })
})
async function onRelease() {
  await releaseAmendingLaw()
}

const releasedAt = computed(() =>
  release.value?.releaseAt ? new Date(release.value.releaseAt) : null,
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

const formatEliForDownload = (eli: string) => eli.replace(/\//g, "_") + ".xml"
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

      <RisCallout
        v-else-if="releasedAt"
        title="Die Abgabe ist aktuell als Prototyp verfügbar."
        variant="warning"
      >
        <p class="mt-4 leading-snug">
          Dieses Änderungsgesetz wurde zuletzt abgegeben am
          <time :datetime="publishedAtDateTime">
            {{ publishedAtDateString }} um {{ publishedAtTimeString }} Uhr. Die
            aktuelle Version kann hier eingesehen werden:
          </time>
        </p>
        <ul class="list-disc pl-20">
          <li
            v-for="{ releasedNormEli, blobUrl } in blobUrls"
            :key="releasedNormEli"
          >
            <a
              :href="blobUrl"
              :download="formatEliForDownload(releasedNormEli)"
              target="_blank"
              class="underline"
              >{{ releasedNormEli }}</a
            >
          </li>
        </ul>
      </RisCallout>

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
