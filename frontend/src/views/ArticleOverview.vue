<script setup lang="ts">
import { ref } from "vue"
const identifier = "123"
const vscodeLink = ref(`vscode://file?fileId=${identifier}`)
</script>

<template>
  <div>
    <H1>Article overview</H1>
    <a :href="vscodeLink">Text consolidation</a>
  </div>
</template>
