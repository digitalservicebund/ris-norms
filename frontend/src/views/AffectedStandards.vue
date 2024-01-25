<script setup lang="ts">
import { ref } from "vue"
const identifier = "123"
const vscodeLink = ref(`vscode://file?fileId=${identifier}`)
</script>

<template>
  <div>
    <p>Affected Standards</p>
    <a :href="vscodeLink">Meta data management</a>
  </div>
</template>
