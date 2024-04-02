<script setup lang="ts">
import RisTextButton from "@/components/controls/RisTextButton.vue"
import { computed, ref, watchEffect, onBeforeUnmount, onMounted } from "vue"
import { useEliPathParameter } from "@/composables/useEliPathParameter"
import { getAmendingLawXmlByEli } from "@/services/amendingLawsService"
import { getTargetLawXmlByEli } from "@/services/targetLawsService"
import { useAmendingLawRelease } from "@/composables/useAmendingLawRelease"

const eli = useEliPathParameter()
const { releasedAt, releasedElis, fetchReleaseStatus, releaseAmendingLaw } =
  useAmendingLawRelease(eli)
const blobUrl = ref("")
const zf0BlobUrls = ref<BlobUrlItem[]>([])

onMounted(async () => {
  await fetchReleaseStatus()
})

interface BlobUrlItem {
  zf0Eli: string
  zf0BlobUrl: string
}

watchEffect(async () => {
  let newBlobUrl = ""
  const newZf0BlobUrls = []

  if (releasedElis?.value?.amendingLawEli) {
    const xmlContent = await getAmendingLawXmlByEli(
      releasedElis.value.amendingLawEli,
    )
    const blob = new Blob([xmlContent], { type: "application/xml" })
    newBlobUrl = URL.createObjectURL(blob)

    if (blobUrl.value) {
      URL.revokeObjectURL(blobUrl.value)
    }
    blobUrl.value = newBlobUrl
  }

  if (releasedElis.value?.zf0Elis) {
    for (const zf0Eli of releasedElis.value.zf0Elis) {
      const xmlContent = await getTargetLawXmlByEli(zf0Eli)
      const blob = new Blob([xmlContent], { type: "application/xml" })
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
  <div class="flex flex-col">
    <section class="flex flex-col gap-20">
      <h1 class="ds-heading-02-reg">Abgabe</h1>

      <div
        v-if="releasedAt"
        aria-label="Infomodal"
        class="flex w-full gap-[0.625rem] border-[0.125rem] border-orange-200 bg-orange-100 px-[1.25rem] py-[1.125rem]"
      >
        <div class="flex flex-col gap-4">
          <span class="ds-label-02-bold">
            Die Abgabe ist aktuell als Prototyp verfügbar.
          </span>
          <span class="ds-body-01-reg">
            Dieses Änderungsgesetz wurde zuletzt abgegeben am
            <time :datetime="publishedAtDateTime">
              {{ publishedAtDateString }} um {{ publishedAtTimeString }} Uhr.
              Die aktuelle Version kann hier eingesehen werden:
            </time>
            <ul class="list-disc pl-20">
              <li>
                <a
                  v-if="releasedElis"
                  :href="blobUrl"
                  :download="formatEliForDownload(releasedElis?.amendingLawEli)"
                  target="_blank"
                  class="underline"
                  >{{ releasedElis?.amendingLawEli }}.xml</a
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
          </span>
        </div>
      </div>

      <span v-else>Das Gesetz wurde noch nicht veröffentlicht.</span>

      <RisTextButton
        class="w-fit"
        variant="primary"
        size="small"
        label="Jetzt abgeben"
        @click="onRelease"
      />
    </section>
  </div>
</template>
