<script setup lang="ts">
import { watch, ref, computed } from "vue"
import type { HeaderBreadcrumb } from "@/components/RisHeader.vue"
import RisViewLayout from "@/components/RisViewLayout.vue"
import type { ZielnormReleaseStatusDomain } from "@/services/zielnormReleaseService"
import {
  useGetZielnormReleaseStatus,
  usePostZielnormRelease,
} from "@/services/zielnormReleaseService"
import { ConfirmDialog, Column, DataTable, useConfirm } from "primevue"
import Button from "primevue/button"
import { useElementId } from "@/composables/useElementId"
import { useNormWorkEliPathParameter } from "@/composables/useNormWorkEliPathParameter"
import { formatDate } from "@/lib/dateTime"
import RisEmptyState from "@/components/RisEmptyState.vue"
import { useToast } from "@/composables/useToast"
import { useGetNormWork } from "@/services/normService"

const normWorkEli = useNormWorkEliPathParameter()
const releaseStatus = ref<ZielnormReleaseStatusDomain | null>(null)
const toast = useToast()

const confirm = useConfirm()

const {
  data: initialReleaseStatus,
  error: releaseStatusError,
  isFetching: isFetchingReleaseStatus,
} = useGetZielnormReleaseStatus(normWorkEli)

watch(initialReleaseStatus, (val) => {
  if (val) releaseStatus.value = val
})

const {
  execute: postRelease,
  data: postData,
  error: postError,
} = usePostZielnormRelease(normWorkEli)

const {
  data: normWork,
  isFetching: isFetchingNormWork,
  error: normWorkError,
} = useGetNormWork(normWorkEli)

const breadcrumbs = computed<HeaderBreadcrumb[]>(() => [
  {
    key: "normWorkTitle",
    title: () => normWork.value?.title ?? "...",
  },
])

const { abgabeHeadingId } = useElementId()

function handleSubmit() {
  confirm.require({
    header: "Expressionen abgeben",
    message:
      "Sind Sie sicher, dass Sie die angezeigten Expressionen abgeben möchten?",
    acceptLabel: "Abgeben",
    rejectLabel: "Abbrechen",
    rejectProps: { text: true },
    defaultFocus: "accept",
    acceptClass: "w-full",
    rejectClass: "w-full",
    accept: () => {
      postRelease("praetext")
    },
  })
}

watch([postData, postError], ([data, error]) => {
  if (data && !error) {
    releaseStatus.value = data
    toast.add({
      summary: "Abgabe erfolgreich",
      detail: "Die Expressionen sind ab morgen im Portal verfügbar.",
      severity: "success",
    })
  } else if (error) {
    toast.addError(error)
  }
})
</script>

<template>
  <RisViewLayout
    :breadcrumbs
    :errors="[normWorkError, releaseStatusError]"
    :loading="isFetchingNormWork || isFetchingReleaseStatus"
  >
    <section :aria-labelledby="abgabeHeadingId">
      <h1 :id="abgabeHeadingId" class="ris-subhead-bold mb-24">Abgabe</h1>
      <template v-if="releaseStatus && releaseStatus?.expressions?.length">
        <div class="flex flex-col bg-white">
          <DataTable
            :value="releaseStatus.expressions"
            data-key="normExpressionEli"
            :row-class="() => `hover:bg-blue-200`"
          >
            <Column header="Expressions-ELI">
              <template #body="{ data }">
                <div class="flex items-center gap-8">
                  {{ data.normExpressionEli.toString() }}
                </div>
              </template>
            </Column>

            <Column header="gültig ab">
              <template #body="{ data }">
                <div v-if="data.isGegenstandslos" class="pl-28">
                  gegenstandslos
                </div>
                <div v-else class="flex items-center gap-8">
                  <div>
                    <span>{{
                      formatDate(data.normExpressionEli.pointInTime)
                    }}</span>
                  </div>
                </div>
              </template>
            </Column>
          </DataTable>
          <div class="flex gap-16 p-14">
            <Button
              label="Abgeben"
              severity="secondary"
              @click="handleSubmit"
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
