<script setup lang="ts">
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { useGetVerkuendungService } from "@/services/verkuendungService"
import { ref } from "vue"
import { getFrbrDisplayText } from "@/lib/frbr"
import type { HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import RisZielnormenList from "@/views/verkuendungen/verkuendungDetail/expressionen-erzeugen/RisZielnormenList.vue"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"

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

const items = [
  {
    title: "Luftverkehrsteuergesetz",
    shortTitle: "LuftVStG",
    expressions: [
      {
        normExpressionEli: NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2010/s1885/2023-01-01/1/deu",
        ),
        isGegenstandslos: false,
        isCreated: true,
        erzeugtDurch: "diese Verkündung",
      },
      {
        normExpressionEli: NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2010/s1885/2024-01-01/1/deu",
        ),
        isGegenstandslos: true,
        isCreated: true,
        erzeugtDurch: "andere Verkündung",
      },
      {
        normExpressionEli: NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2010/s1885/2024-01-01/2/deu",
        ),
        isGegenstandslos: false,
        isCreated: false,
        erzeugtDurch: "System",
      },
      {
        normExpressionEli: NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2010/s1885/2024-05-01/1/deu",
        ),
        isGegenstandslos: false,
        isCreated: false,
        erzeugtDurch: "diese Verkündung",
      },
    ],
  },
  {
    title: "Luftverkehrsteuergesetz 2",
    shortTitle: "LuftVStG",
    expressions: [
      {
        normExpressionEli: NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2010/s1885/2023-01-01/1/deu",
        ),
        isGegenstandslos: false,
        isCreated: true,
        erzeugtDurch: "diese Verkündung",
      },
      {
        normExpressionEli: NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2010/s1885/2024-01-01/1/deu",
        ),
        isGegenstandslos: true,
        isCreated: true,
        erzeugtDurch: "andere Verkündung",
      },
      {
        normExpressionEli: NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2010/s1885/2024-01-01/2/deu",
        ),
        isGegenstandslos: false,
        isCreated: false,
        erzeugtDurch: "System",
      },
      {
        normExpressionEli: NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2010/s1885/2024-05-01/1/deu",
        ),
        isGegenstandslos: false,
        isCreated: false,
        erzeugtDurch: "diese Verkündung",
      },
    ],
  },
]
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[verkuendungError]"
    :loading="!verkuendungHasFinished"
  >
    <RisZielnormenList :items="items"></RisZielnormenList>
  </RisViewLayout>
</template>

<style scoped></style>
