<script lang="ts" setup>
import { computed, type Component } from "vue"
import { RouteLocationRaw, RouterLink } from "vue-router"

type LinkButtonHref = {
  href: string
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
    variant?: "primary" | "secondary" | "tertiary" | "ghost"

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
      'ds-button': true,
      'ds-button-with-icon': !!icon,
      'ds-button-with-icon-only': iconOnly,
      'ds-button-secondary': variant === 'secondary',
      'ds-button-tertiary': variant === 'tertiary',
      'ds-button-ghost': variant === 'ghost',
      'is-disabled': disabled,
      'ds-button-large': size === 'large',
      'ds-button-small': size === 'small',
      'ds-button-full-width': fullWidth,
    }"
    :disabled="tag === 'button' && disabled === true ? true : undefined"
    :aria-label="iconOnly ? label : undefined"
    v-bind="linkBindings"
  >
    <component :is="icon" v-if="icon" class="ds-button-icon" alt="" />
    <span
      v-if="!iconOnly"
      class="ds-button-label"
      :class="{ 'sr-only static': iconOnly }"
      >{{ label }}</span
    >
  </component>
</template>
<!--render lavel with vif dependin on icon only. and add to component an aria label but only if icon only is true-->
