<script setup lang="ts">
import RisTextButton from "@/components/controls/RisTextButton.vue"
import { computed, ref } from "vue"

// TODO: (Malte Laukötter, 2024-03-12) this is probably a property of the amending law in the future
const publishedAt = ref<Date | null>(null)

function onPublish() {
  // TODO: (Malte Laukötter, 2024-03-12) Call an endpoint to publish the amending law
  publishedAt.value = new Date()
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
      <span v-if="publishedAt"
        >Dieses Änderungsgesetz wurde zuletzt abgegeben am
        <time :datetime="publishedAtDateTime">
          {{ publishedAtDateString }} um {{ publishedAtTimeString }} Uhr
        </time>
      </span>
      <span v-else>Das Gesetz wurde noch nicht veröffentlicht.</span>

      <RisTextButton
        class="w-fit"
        variant="primary"
        size="small"
        label="Jetzt Abgeben"
        @click="onPublish"
      />
    </section>
  </div>
</template>
