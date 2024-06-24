<script setup lang="ts">
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisHeader, { HeaderBreadcrumb } from "./RisHeader.vue"

const exampleBreadcrumbs: HeaderBreadcrumb[] = [
  { key: "0", title: "BGBL 1 2023 Nr. 413", to: "/" },
  { key: "1", title: "PassG", to: "/" },
  { key: "2", title: "Metadatendokumentation" },
]
</script>

<template>
  <Story>
    <Variant title="Normal">
      <RisHeader :breadcrumbs="exampleBreadcrumbs" />
    </Variant>

    <Variant title="With action in slot">
      <RisHeader :breadcrumbs="exampleBreadcrumbs">
        <template #action>
          <RisTextButton label="Click me" size="small" />
        </template>
      </RisHeader>
    </Variant>
  </Story>
</template>
