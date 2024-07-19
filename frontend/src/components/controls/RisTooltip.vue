<script setup lang="ts">
import { useElementId } from "@/composables/useElementId"
import { computed } from "vue"
import RisCallout from "./RisCallout.vue"

withDefaults(
  defineProps<{
    /** Title of the tooltip. */
    title: string

    /**
     * Visual variant of the tooltip.
     * @default "neutral"
     */
    variant?: "neutral" | "error" | "warning" | "success"

    /**
     * Whether the tooltip is aligned to the left or the right of the available
     * container. This manages the positioning of the arrow and the tooltip box.
     * @default "left"
     */
    alignment?: "left" | "right"

    /**
     * Whether the tooltip is positioned above or below the element in the slot.
     * This also manages the direction of the arrow.
     * @default "top"
     */
    attachment?: "top" | "bottom"

    /**
     * Shows an icon to hide the callout.
     * @default false
     */
    allowDismiss?: boolean
  }>(),
  {
    content: "",
    variant: "neutral",
    alignment: "left",
    attachment: "top",
    allowDismiss: false,
  },
)

const slots = defineSlots<{
  default(props: {
    /**
     * Returns the ID of the tooltip element. This should be used as the
     * `aria-describedby` element for which the tooltip is used, in order
     * to ensure correct a11y association and screen reader announcements.
     *
     * Example:
     *
     * ```html
     * <RisTooltip v-slot="{ ariaDescribedby }">
     *   <button :aria-describedby>Button</button>
     * </RisTooltip>
     * ```
     */
    ariaDescribedby: string
  }): any // eslint-disable-line @typescript-eslint/no-explicit-any

  message(): any // eslint-disable-line @typescript-eslint/no-explicit-any
}>()

const hasChildren = computed(() => Boolean(slots.default))

/**
 * Manages the visibility of the callout. This gets updates by the component
 * when the user presses the "dismiss" button, but you can also use it
 * independently from user actions.
 */
const visible = defineModel<boolean>("visible", { default: true })

const { ariaId } = useElementId("tooltip")
</script>

<template>
  <span>
    <span
      v-if="visible"
      :id="ariaId"
      :data-alignment="alignment"
      :data-attachment="attachment"
      class="group inline-block data-[alignment=left]:left-0 data-[alignment=right]:right-0"
      :class="{
        absolute: hasChildren,
        'translate-y-full': hasChildren && attachment === 'bottom',
        '-translate-y-[calc(100%+8px)]': hasChildren && attachment === 'top',
      }"
      role="tooltip"
    >
      <RisCallout
        :allow-dismiss
        :title
        :variant
        visible
        @update:visible="visible = false"
      >
        <slot name="message" />
      </RisCallout>
      <span
        :data-variant="variant"
        class="absolute inline-block border-8 border-x-transparent data-[variant=error]:border-y-red-200 data-[variant=neutral]:border-y-blue-200 data-[variant=success]:border-y-green-200 data-[variant=warning]:border-y-yellow-200 group-data-[alignment=left]:left-24 group-data-[alignment=right]:right-20 group-data-[attachment=bottom]:-top-8 group-data-[attachment=bottom]:border-t-0 group-data-[attachment=top]:border-b-0"
      ></span>
    </span>

    <template v-if="$slots.default">
      <slot :aria-describedby="ariaId" />
    </template>
  </span>
</template>
