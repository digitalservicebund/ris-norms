<script lang="ts" setup>
withDefaults(
  defineProps<{
    /** Unique ID for the checkbox. */
    id: string
    /** Optional label for the field */
    label?: string
    /** Optional size for the field */
    size?: "mini" | "small" | "regular"
    /** Optional read-only for the field */
    readOnly?: boolean
  }>(),
  {
    label: undefined,
    size: "mini",
  },
)

const checked = defineModel<boolean | undefined>({ default: false })
</script>

<template>
  <div class="grid gap-2">
    <input
      :id="id"
      v-model="checked"
      class="ds-checkbox bg-white"
      :class="{
        'ds-checkbox-mini': size === 'mini',
        'ds-checkbox-small': size === 'small',
        '!bg-blue-300 !shadow-none': readOnly,
      }"
      :disabled="readOnly"
      type="checkbox"
      @keydown.space.prevent="checked = !checked"
    />
    <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
    <label v-if="label" :for="id" class="ds-label">
      {{ label }}
    </label>
  </div>
</template>
