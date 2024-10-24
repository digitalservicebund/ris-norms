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
} = useAmendingLawRelease(eli)
const blobUrl = ref("")
const zf0BlobUrls = ref<BlobUrlItem[]>([])

interface BlobUrlItem {
  zf0Eli: string
  zf0BlobUrl: string
}

watch(release, async () => {
  if (release?.value?.amendingLawEli) {
    const { data: xmlContent } = await useGetNormXml(
      release.value.amendingLawEli,
    )

    const blob = new Blob([xmlContent.value ?? ""], { type: "application/xml" })
    const newBlobUrl = URL.createObjectURL(blob)

    if (blobUrl.value) {
      URL.revokeObjectURL(blobUrl.value)
    }
    blobUrl.value = newBlobUrl
  }

  if (release.value?.zf0Elis) {
    const newZf0BlobUrls = []
    for (const zf0Eli of release.value.zf0Elis) {
      const { data: xmlContent } = await useGetNormXml(zf0Eli)

      const blob = new Blob([xmlContent.value ?? ""], {
        type: "application/xml",
      })
      const blobUrlForZf0 = URL.createObjectURL(blob)
      newZf0BlobUrls.push({ zf0Eli, zf0BlobUrl: blobUrlForZf0 })

      zf0BlobUrls.value.forEach((item) => {
        URL.revokeObjectURL(item.zf0BlobUrl)
      })
      zf0BlobUrls.value = newZf0BlobUrls
    }
  }
})

onBeforeUnmount(() => {
  if (blobUrl.value) {
    URL.revokeObjectURL(blobUrl.value)
  }

  zf0BlobUrls.value.forEach((item) => {
    URL.revokeObjectURL(item.zf0BlobUrl)
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

      <div v-else-if="fetchError">
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
          <li>
            <a
              v-if="release"
              :href="blobUrl"
              :download="formatEliForDownload(release.amendingLawEli)"
              target="_blank"
              class="underline"
              >{{ release.amendingLawEli }}.xml</a
            >
          </li>
          <li v-for="{ zf0Eli, zf0BlobUrl } in zf0BlobUrls" :key="zf0Eli">
            <a
              :href="zf0BlobUrl"
              :download="formatEliForDownload(zf0Eli)"
              target="_blank"
              class="underline"
              >{{ zf0Eli }}.xml</a
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
