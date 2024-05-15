<script setup lang="ts">
import UploadFileOutlineRounded from "~icons/ic/baseline-upload-file"

const props = withDefaults(
  defineProps<{
    /** Unique ID for the dro. */
    id?: string
    /** Text message to be displayed */
    textContent: string
    /** Either simple or extended */
    variant?: string
    /** Text for a recommended action */
    recommendedAction?: string
    /** Href for a recommended action */
    recommendedActionHref?: string
  }>(),
  {
    id: "emptyState",
    variant: "simple",
    recommendedAction: "",
    recommendedActionHref: "",
  },
)
</script>

<template>
  <div
    v-if="variant === 'simple'"
    :id="id"
    class="mt-[85px] h-fit rounded border-2 border-dashed border-blue-500 bg-gray-100 px-48 py-24 text-center text-xl text-gray-900"
  >
    {{ props.textContent }}
  </div>
  <div
    v-else
    :id="id"
    class="mt-[85px] h-fit rounded border-2 border-dashed border-blue-500 bg-white px-48 py-24 text-center text-xl text-gray-900"
  >
    <UploadFileOutlineRounded
      class="bg-red mx-auto mb-4 text-4xl text-blue-700"
      aria-label="Upload file icon"
    />
    <div>{{ props.textContent }}</div>
    <div v-if="recommendedAction" class="text-sm font-bold text-blue-800">
      {{ props.recommendedAction }}
    </div>
  </div>
</template>
