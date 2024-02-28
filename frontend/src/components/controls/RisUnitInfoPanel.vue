<script lang="ts" setup>
interface Props {
  heading: string
  title: string
}

withDefaults(defineProps<Props>(), {
  heading: "",
  title: "",
})
</script>

<template>
  <div
    class="flex h-96 flex-row items-center border-b border-solid border-gray-400 bg-blue-200 px-[2rem]"
  >
    <div class="flex flex-col justify-center gap-12">
      <div class="ds-heading-03-reg">
        {{ heading }}
      </div>
      <div>
        {{ title }}
      </div>
    </div>
  </div>
</template>
