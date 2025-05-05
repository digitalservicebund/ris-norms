<script setup lang="ts">
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useGetVerkuendungService } from "@/services/verkuendungService"
import { ref } from "vue"
import { getFrbrDisplayText } from "@/lib/frbr"
import type { HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"

const eli = useDokumentExpressionEliPathParameter()

const {
  data: verkuendung,
  error: verkuendungError,
  isFinished: verkuendungHasFinished,
} = useGetVerkuendungService(() => eli.value.asNormEli())

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "verkuendung",
    title: () => getFrbrDisplayText(verkuendung.value) ?? "...",
    to: `/verkuendungen/${eli.value}`,
  },
  { key: "expressionen-erzeugen", title: "Expressionen erzeugen" },
])
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[verkuendungError]"
    :loading="!verkuendungHasFinished"
  ></RisViewLayout>
</template>

<style scoped></style>
