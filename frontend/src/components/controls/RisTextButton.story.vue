<script lang="ts" setup>
import RisTextButton from "./RisTextButton.vue"
import IcOutlineModeEdit from "~icons/ic/outline-mode-edit"
import { logEvent } from "histoire/client"
</script>

<template>
  <Story :layout="{ type: 'grid' }">
    <Variant title="Default (primary)">
      <RisTextButton :label="'Click me'" @click="logEvent('click', $event)" />
    </Variant>

    <Variant title="Secondary">
      <RisTextButton
        :label="'Click me'"
        variant="secondary"
        @click="logEvent('click', $event)"
      />
    </Variant>

    <Variant title="Tertiary">
      <RisTextButton
        :label="'Click me'"
        variant="tertiary"
        @click="logEvent('click', $event)"
      />
    </Variant>

    <Variant title="Ghost">
      <RisTextButton
        :label="'Click me'"
        variant="ghost"
        @click="logEvent('click', $event)"
      />
    </Variant>

    <Variant title="With label and icon">
      <RisTextButton
        :label="'Click me'"
        :icon="IcOutlineModeEdit"
        @click="logEvent('click', $event)"
      />
    </Variant>

    <Variant title="Icon only">
      <RisTextButton
        :label="'Click me'"
        :icon="IcOutlineModeEdit"
        icon-only
        @click="logEvent('click', $event)"
      />
    </Variant>

    <Variant title="Disabled">
      <RisTextButton
        :label="'Click me'"
        disabled
        @click="logEvent('click', $event)"
      />
    </Variant>

    <Variant title="Small">
      <RisTextButton
        :label="'Click me'"
        size="small"
        @click="logEvent('click', $event)"
      />
    </Variant>

    <Variant title="Large">
      <RisTextButton
        :label="'Click me'"
        size="large"
        @click="logEvent('click', $event)"
      />
    </Variant>

    <Variant title="Link button">
      <RisTextButton
        :label="'Click me'"
        :to="{ href: 'https://example.com', target: '_blank' }"
      />
    </Variant>

    <Variant title="Full width">
      <RisTextButton
        :label="'Click me'"
        full-width
        @click="logEvent('click', $event)"
      />
    </Variant>
  </Story>
</template>
