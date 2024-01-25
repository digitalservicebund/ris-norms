<script setup lang="ts">
import { ref } from "vue"
const identifier = "123"
const vscodeLink = ref(`vscode://file?fileId=${identifier}`)
console.log("article overview")
</script>

<template>
  <div>
    <p>Article overview</p>
    <a :href="vscodeLink">Text consolidation</a>
  </div>
</template>
