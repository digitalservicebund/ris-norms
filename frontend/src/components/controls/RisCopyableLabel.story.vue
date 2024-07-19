<script setup lang="ts">
import RisCopyableLabel from "./RisCopyableLabel.vue"
</script>

<template>
  <Story>
    <Variant title="Label text is the value (default)">
      <RisCopyableLabel text="Copy this text to the clipboard" />
    </Variant>

    <Variant title="Value is different from label text">
      <RisCopyableLabel text="Copy a value to the clipboard" value="foo" />
    </Variant>
  </Story>
</template>
