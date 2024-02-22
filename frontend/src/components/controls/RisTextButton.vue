<script lang="ts" setup>
import type { Component } from "vue"
import { computed, h } from "vue"

interface Props {
  label?: string
  icon?: Component
  ariaLabel?: string
  disabled?: boolean
  href?: string
  target?: string
}

const props = withDefaults(defineProps<Props>(), {
  label: undefined,
  icon: undefined,
  ariaLabel: undefined,
  disabled: false,
  href: undefined,
  target: undefined,
})

const buttonClasses = computed(() => ({
  "ds-button-ghost": true,
  "ds-button-with-icon": props.icon,
  "is-disabled": props.href && props.disabled,
}))

const sanitizedUrl = computed(() => props.href)

const renderIcon = () =>
  props.icon ? h(props.icon, { class: "ds-button-icon" }) : undefined

const renderLabel = () =>
  props.label ? h("span", { class: "ds-button-label" }, props.label) : undefined

const render = () => {
  const { disabled, href } = props
  const tag = href && !disabled ? "a" : "button"

  return h(
    tag,
    {
      class: ["ds-button", buttonClasses.value],
      "aria-label": props.ariaLabel,
      disabled: !href && disabled,
      href: sanitizedUrl.value,
      target: props.target,
    },
    [renderIcon(), renderLabel()],
  )
}
</script>

<template>
  <render />
</template>
