<script setup lang="ts">
import type { HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetVerkuendungService } from "@/services/verkuendungService"
// import { useGetZielnormReleaseStatus } from "@/services/zielnormExpressionsService"
import type { ZielnormReleaseStatusDomain } from "@/services/zielnormExpressionsService"
import { ConfirmDialog, Badge, Column, DataTable, useConfirm } from "primevue"
import { ref, computed } from "vue"
import Button from "primevue/button"
import { useElementId } from "@/composables/useElementId"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import IcBaselineCheckCircle from "~icons/ic/baseline-check-circle"
import IcBaselineContrast from "~icons/ic/baseline-contrast"
import IcBaselineErrorOutline from "~icons/ic/baseline-error-outline"
import IcBaselinePanoramaFishEye from "~icons/ic/baseline-panorama-fish-eye"
import IcBaselineReadMore from "~icons/ic/baseline-read-more"
import RisHighlightColorSwatch from "@/components/RisHighlightColorSwatch.vue"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"
// import { useNormWorkEliPathParameter } from "@/composables/useNormWorkEliPathParameter"
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import { useToast } from "@/composables/useToast"

// const zielnormEli = useNormWorkEliPathParameter("zielnorm")
const verkuendungEli = useDokumentExpressionEliPathParameter("verkuendung")

const confirm = useConfirm()
const traceId = useSentryTraceId()
const { add: addToast, addError: addErrorToast } = useToast()

// const { data: releaseStatus } = useGetZielnormReleaseStatus(
//   () => zielnormEli.value,
// )

const releaseStatus = ref<ZielnormReleaseStatusDomain>({
  normWorkEli: NormWorkEli.fromString("eli/bund/bgbl-1/2010/s1885"),
  title: "Luftverkehrsteuergesetz",
  shortTitle: "LuftVStG",
  expressions: [
    {
      normExpressionEli: new NormExpressionEli(
        "bgbl-1",
        "2010",
        "s1885",
        "2023-01-01",
        1,
        "deu",
      ),
      isGegenstandslos: false,
      currentStatus: "NOT_RELEASED",
    },
    {
      normExpressionEli: new NormExpressionEli(
        "bgbl-1",
        "2010",
        "s1885",
        "2023-02-01",
        1,
        "deu",
      ),
      isGegenstandslos: false,
      currentStatus: "PRAETEXT_RELEASED",
    },
    {
      normExpressionEli: new NormExpressionEli(
        "bgbl-1",
        "2010",
        "s1885",
        "2024-02-01",
        2,
        "deu",
      ),
      isGegenstandslos: false,
      currentStatus: "VOLLDOKUMENTATION_RELEASED",
    },
    {
      normExpressionEli: new NormExpressionEli(
        "bgbl-1",
        "2010",
        "s1885",
        "2024-05-01",
        1,
        "deu",
      ),
      isGegenstandslos: true,
      currentStatus: "NOT_RELEASED",
    },
  ],
})

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "verkuendung",
    title: () => getFrbrDisplayText(verkuendung.value) ?? "...",
    to: `/verkuendungen/${verkuendungEli.value}`,
  },
  {
    key: "norm",
    title: () =>
      releaseStatus.value
        ? `${releaseStatus.value.title} (${releaseStatus.value.shortTitle})`
        : "...",
  },
  { key: "Abgabe", title: "Abgabe" },
])

const { data: verkuendung } = useGetVerkuendungService(() =>
  verkuendungEli.value.asNormEli(),
)

const { abgabeHeadingId } = useElementId()

function formatDate(dateStr: string): string {
  return new Date(dateStr).toLocaleDateString("de-DE", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
  })
}

const internalZielnormExpressions = computed(() => {
  const expressions = releaseStatus.value?.expressions ?? []
  let nextColorIndex = 0

  return expressions.map((item, index) => {
    const colorIndex = nextColorIndex++
    return {
      ...item,
      isReplacingGegenstandslos:
        index > 0 && expressions[index - 1].isGegenstandslos,
      untilDate:
        index < expressions.length - 1
          ? new Date(
              new Date(
                expressions[index + 1].normExpressionEli.pointInTime,
              ).getTime() - 86400000,
            )
          : undefined,
      colorIndex,
    }
  })
})
</script>

<template>
  <RisViewLayout :breadcrumbs>
    <section :aria-labelledby="abgabeHeadingId">
      <h1 :id="abgabeHeadingId" class="ris-subhead-bold mb-24">Abgabe</h1>
      <template v-if="internalZielnormExpressions?.length">
        <div class="flex flex-col bg-white">
          <DataTable
            :value="internalZielnormExpressions"
            data-key="normExpressionEli"
            :row-class="() => `hover:bg-blue-200`"
          >
            <Column header="Expressions-ELI">
              <template #body="{ data }">
                <IcBaselineReadMore
                  v-if="data.isReplacingGegenstandslos"
                  class="text-gray-600"
                />
                {{ data.normExpressionEli.toString() }}
              </template>
            </Column>

            <Column header="gültig ab - gültig bis">
              <template #body="{ data }">
                <div v-if="data.isGegenstandslos" class="pl-28">
                  gegenstandslos
                </div>
                <div v-else class="flex items-center gap-8">
                  <RisHighlightColorSwatch :color-index="data.colorIndex" />
                  <div>
                    <span>{{
                      formatDate(data.normExpressionEli.pointInTime)
                    }}</span>
                    <span v-if="data.untilDate" class="text-gray-800">
                      –
                      {{ formatDate(data.untilDate.toISOString()) }}
                    </span>
                  </div>
                </div>
              </template>
            </Column>

            <Column>
              <template #body="{ data }">
                <div class="flex justify-end">
                  <Badge v-if="data.isGegenstandslos">
                    <IcBaselineErrorOutline />
                    Gegenstandslos
                  </Badge>

                  <Badge
                    v-else-if="
                      data.currentStatus === 'VOLLDOKUMENTATION_RELEASED'
                    "
                    severity="success"
                  >
                    <IcBaselineCheckCircle />
                    Volldokumentation abgegeben
                  </Badge>

                  <Badge
                    v-else-if="data.currentStatus === 'PRAETEXT_RELEASED'"
                    severity="warn"
                  >
                    <IcBaselineContrast />
                    Prätext abgegeben
                  </Badge>

                  <Badge v-else severity="info">
                    <IcBaselinePanoramaFishEye />
                    Noch nicht abgegeben
                  </Badge>
                </div>
              </template>
            </Column>
          </DataTable>
          <div class="flex gap-16 p-14">
            <Button label="Prätexte abgeben" severity="secondary" disabled />
            <Button label="Volldokumentationen abgeben" disabled />
          </div>
        </div>
      </template>
    </section>
  </RisViewLayout>
  <ConfirmDialog />
</template>
