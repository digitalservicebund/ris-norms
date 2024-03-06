<script lang="ts" setup>
withDefaults(
  defineProps<{
    /** The primary content of the header. */
    heading: string

    /** An optional subtitle that is displayed below the heading. */
    subtitle?: string
  }>(),
  {
    subtitle: "",
  },
)
</script>

<template>
  <div
    class="flex min-h-80 flex-row items-center border-b border-solid border-gray-400 bg-blue-200 px-40 py-4"
  >
    <div class="flex flex-col justify-center gap-12">
      <div class="ds-heading-03-reg mb-0">
        {{ heading }}
      </div>
      <div v-if="subtitle">
        {{ subtitle }}
      </div>
    </div>
  </div>
</template>
