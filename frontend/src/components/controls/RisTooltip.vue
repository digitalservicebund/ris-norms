<script setup lang="ts">
import { useElementId } from "@/composables/useElementId"
import { useElementSize } from "@vueuse/core"
import { computed, ref } from "vue"
import RisCallout from "./RisCallout.vue"

const props = withDefaults(
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

/* -------------------------------------------------- *
 * Positioning                                        *
 * -------------------------------------------------- */

const contentEl = ref<HTMLElement | null>(null)
const { height: contentHeight } = useElementSize(contentEl)

const calloutEl = ref<HTMLElement | null>(null)
const { height: calloutHeight } = useElementSize(calloutEl)

const translate = computed(() => {
  if (!hasChildren.value) return undefined
  else if (props.attachment === "bottom") {
    // Content size + arrow size
    return `0 ${contentHeight.value + 8}px`
  } else if (props.attachment === "top") {
    // Content size + arrow size + padding
    return `0 -${calloutHeight.value + 8 + 32}px`
  } else return undefined
})
</script>

<template>
  <span>
    <span
      v-if="visible"
      :id="ariaId"
      :data-alignment="alignment"
      :data-attachment="attachment"
      class="group inline-block w-max data-[alignment=left]:left-0 data-[alignment=right]:right-0"
      :class="{ absolute: hasChildren, 'z-10': hasChildren }"
      :style="{ translate }"
      role="tooltip"
    >
      <RisCallout
        ref="calloutEl"
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

    <span v-if="$slots.default" ref="contentEl" class="inline-block">
      <slot :aria-describedby="ariaId" />
    </span>
  </span>
</template>
