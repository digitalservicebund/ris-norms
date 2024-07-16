<script lang="ts" setup>
import { computed, type Component } from "vue"
import { RouteLocationRaw, RouterLink } from "vue-router"
import RisLoadingSpinner from "./RisLoadingSpinner.vue"

type LinkButtonHref = {
  /** Target URL of the link. */
  href: string

  /** Target window of the link. */
  target?: string
}

function isLinkButton(maybe: unknown): maybe is LinkButtonHref {
  return !!maybe && typeof maybe === "object" && "href" in maybe
}

const props = withDefaults(
  defineProps<{
    /**
     * The label of the button. For accesssibility reasons, this is always
     * required, including for buttons only displaying an icon.
     */
    label: string

    /** An optional icon to be displayed next to the label. */
    icon?: Component

    /**
     * Visually hides the label to display only an icon. Note that for
     * accessibility reasons, you will still need to provide a label.
     *
     * @default false
     */
    iconOnly?: boolean

    /**
     * The visual appearance of the button.
     *
     * @default "primary"
     */
    variant?: "primary" | "secondary" | "tertiary" | "ghost" | "link"

    /**
     * The size of the button.
     *
     * @default "default" (regular size)
     */
    size?: "default" | "large" | "small"

    /**
     * Disables the button.
     *
     * @default false
     */
    disabled?: boolean

    /**
     * When set to true, the button will stretch to take up the entire
     * available width.
     *
     * @default false
     */
    fullWidth?: boolean

    /**
     * When provided, the button will be rendered as a link with the
     * appearance of a button. Use this when clicking the button should
     * trigger a navigation. You can provide a link to a route inside the
     * app or an external link.
     */
    to?: RouteLocationRaw | LinkButtonHref

    /**
     * Shows a loading state on the button.
     *
     * @default false
     */
    loading?: boolean
  }>(),
  {
    icon: undefined,
    variant: "primary",
    size: "default",
    to: undefined,
  },
)

const tag = computed<"a" | "button" | typeof RouterLink>(() => {
  if (!props.to) return "button"
  else if (isLinkButton(props.to)) return "a"
  else return RouterLink
})

const linkBindings = computed(() => {
  if (tag.value === "button" || props.disabled) return undefined
  else if (tag.value === "a") return { ...(props.to as LinkButtonHref) }
  else return { to: props.to }
})
</script>

<template>
  <component
    :is="tag"
    :class="{
      'ds-button relative': true,
      'ds-button-with-icon': !!icon,
      'ds-button-with-icon-only': iconOnly,
      'ds-button-secondary': variant === 'secondary',
      'ds-button-tertiary': variant === 'tertiary',
      'ds-button-ghost': variant === 'ghost',
      [$style['ds-button-link']]: variant === 'link',
      'is-disabled': disabled,
      'ds-button-large': size === 'large',
      'ds-button-small': size === 'small',
      'ds-button-full-width': fullWidth,
    }"
    :disabled="tag === 'button' && (disabled || loading) ? true : undefined"
    :aria-label="iconOnly ? label : undefined"
    :aria-busy="loading || undefined"
    v-bind="linkBindings"
  >
    <slot name="icon">
      <component :is="icon" v-if="icon" class="ds-button-icon" alt="" />
    </slot>
    <span
      v-if="!iconOnly"
      class="ds-button-label"
      :class="{ 'sr-only static': iconOnly }"
    >
      {{ label }}
    </span>

    <span
      v-if="loading"
      class="absolute inset-4 flex items-center justify-center text-inherit"
      :class="[
        variant === 'tertiary' || variant === 'ghost'
          ? 'bg-white'
          : 'bg-inherit',
      ]"
    >
      <RisLoadingSpinner class="h-[1.5em] w-[1.5em] border-current" />
    </span>
  </component>
</template>

<style module>
.ds-button-link {
  @apply border-2 border-solid border-transparent bg-transparent p-4 text-sm text-blue-800 underline hover:border-gray-600 hover:bg-transparent focus:border-gray-600 disabled:bg-transparent disabled:text-gray-600 disabled:hover:border-transparent disabled:focus:border-transparent;
}
</style>
