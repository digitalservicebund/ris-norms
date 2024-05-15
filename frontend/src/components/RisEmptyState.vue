<script setup lang="ts">
const props = withDefaults(
  defineProps<{
    /** Unique ID for the dro. */
    id?: string
    /** Text message to be displayed */
    textContent: string
    /** Either simple or extended */
    variant?: string
    linkText?: string
    linkHref?: string
  }>(),
  {
    id: "emptyState",
    variant: "simple",
    linkText: "",
    linkHref: "",
  },
)
</script>

<template>
  <div
    v-if="variant == 'simple'"
    :id="id"
    class="mt-[85px] h-fit rounded border-2 border-dashed border-blue-500 px-48 py-24 text-center text-xl text-gray-900"
  >
    {{ props.textContent }}
  </div>
  <div
    v-else
    :id="id"
    class="mt-[85px] h-fit rounded border-2 border-dashed border-blue-500 px-48 py-24 text-center text-xl text-gray-900"
  >
    <div>{{ props.textContent }}</div>
    <div v-if="linkText" class="text-sm">{{ props.linkText }}</div>
  </div>
</template>
