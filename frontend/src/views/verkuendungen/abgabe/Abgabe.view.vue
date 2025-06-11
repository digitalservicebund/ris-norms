<script setup lang="ts">
import type { HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import { getFrbrDisplayText } from "@/lib/frbr"
import { useGetVerkuendungService } from "@/services/verkuendungService"
import { useGetZielnormReleaseStatus } from "@/services/zielnormReleaseStatusService"
import { ConfirmDialog, Badge, Column, DataTable, useConfirm } from "primevue"
import { ref, computed } from "vue"
import Button from "primevue/button"
import { useElementId } from "@/composables/useElementId"
import IcBaselineCheckCircle from "~icons/ic/baseline-check-circle"
import IcBaselineContrast from "~icons/ic/baseline-contrast"
import IcBaselineErrorOutline from "~icons/ic/baseline-error-outline"
import IcBaselinePanoramaFishEye from "~icons/ic/baseline-panorama-fish-eye"
import IcBaselineReadMore from "~icons/ic/baseline-read-more"
import RisHighlightColorSwatch from "@/components/RisHighlightColorSwatch.vue"
import { useNormWorkEliPathParameter } from "@/composables/useNormWorkEliPathParameter"
import { formatDate } from "@/lib/dateTime"
import dayjs from "dayjs"
import RisEmptyState from "@/components/RisEmptyState.vue"

const zielnormEli = useNormWorkEliPathParameter("zielnorm")
const verkuendungEli = useDokumentExpressionEliPathParameter("verkuendung")

const confirm = useConfirm()

const { data: releaseStatus, error: releaseStatusError } =
  useGetZielnormReleaseStatus(() => zielnormEli.value)

const { data: verkuendung } = useGetVerkuendungService(() =>
  verkuendungEli.value.asNormEli(),
)

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

const { abgabeHeadingId } = useElementId()

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
          ? dayjs(expressions[index + 1].normExpressionEli.pointInTime)
              .subtract(1, "day")
              .toDate()
          : undefined,
      colorIndex,
    }
  })
})

function handlePraetextSubmit() {
  confirm.require({
    header: "Expressionen als Prätext abgeben",
    message:
      "Sind sie sicher, dass sie die angezeigten Expressionen als Prätext abgeben möchten?",
    acceptLabel: "Abgeben",
    rejectLabel: "Abbrechen",
    rejectProps: { text: true },
    defaultFocus: "accept",
    acceptClass: "w-full",
    rejectClass: "w-full",
    accept: () => {
      // call backend
    },
  })
}

function handleVolldokumentationSubmit() {
  confirm.require({
    header: "Expressionen als Volldokumentation abgeben",
    message:
      "Sind sie sicher, dass sie die angezeigten Expressionen als Volldokumentation abgeben möchten?",
    acceptLabel: "Abgeben",
    rejectLabel: "Abbrechen",
    rejectProps: { text: true },
    defaultFocus: "accept",
    acceptClass: "w-full",
    rejectClass: "w-full",
    accept: () => {
      // call backend
    },
  })
}
</script>

<template>
  <RisViewLayout :breadcrumbs :errors="[releaseStatusError]">
    <section :aria-labelledby="abgabeHeadingId">
      <h1 :id="abgabeHeadingId" class="ris-subhead-bold mb-24">Abgabe</h1>
      <template v-if="releaseStatus && internalZielnormExpressions.length">
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

            <Column header="Status" header-class="sr-only">
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
            <Button
              label="Prätexte abgeben"
              severity="secondary"
              disabled
              @click="handlePraetextSubmit"
            />
            <Button
              label="Volldokumentationen abgeben"
              disabled
              @click="handleVolldokumentationSubmit"
            />
          </div>
        </div>
      </template>
      <template v-else>
        <RisEmptyState
          text-content="Es sind keine unveröffentlichten Expressionen für diese Norm vorhanden."
        />
      </template>
    </section>
  </RisViewLayout>
  <ConfirmDialog />
</template>
