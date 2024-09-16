<script lang="ts" setup>
import RisCopyableLabel from "@/components/controls/RisCopyableLabel.vue"
import RisHeader, {
  HeaderBreadcrumb,
} from "@/components/controls/RisHeader.vue"
import { useElementId } from "@/composables/useElementId"
import { useErrorMessage } from "@/composables/useErrorMessage"
import { isErrorResponse } from "@/lib/errorResponseMapper"
import { ErrorResponse } from "@/types/errorResponse"
import { Norm } from "@/types/norm"
import Button from "primevue/button"
import FileUpload, {
  FileUploadErrorEvent,
  FileUploadUploadEvent,
} from "primevue/fileupload"
import ConfirmDialog from "primevue/confirmdialog"
import { useConfirm } from "primevue/useconfirm"
import IcBaselineErrorOutline from "~icons/ic/baseline-error-outline"
import Message from "primevue/message"
import { useToast } from "primevue/usetoast"
import { computed, ref, useTemplateRef } from "vue"
import { useRouter } from "vue-router"
import { useForceUploadFile } from "@/services/uploadService"

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "uploadAnnouncement",
    title: "Verkündung manuell hinzufügen",
  },
])

const { add: addToast } = useToast()

const router = useRouter()

/* -------------------------------------------------- *
 * File upload                                        *
 * -------------------------------------------------- */

const uploadElement =
  useTemplateRef<InstanceType<typeof FileUpload>>("fileUpload")

const isLoading = ref(false)

const error = ref<ErrorResponse | true>()

const errorMessage = useErrorMessage(error)

const errorList = computed(() =>
  isErrorResponse(error.value)
    ? JSON.stringify(error.value.errors, undefined, 2)
    : undefined,
)

const chosenFile = ref<File | null>(null)

function onFileChoosing(event: { files: File[] }) {
  chosenFile.value = event.files[0]
}

async function upload() {
  uploadElement.value?.upload()
}

function onBeginUpload() {
  isLoading.value = true
  error.value = undefined
}

function onUploaded(event: FileUploadUploadEvent) {
  isLoading.value = false

  try {
    const responseData: Norm = JSON.parse(event.xhr.responseText)

    if (responseData.eli) {
      router.push(`/amending-laws/${responseData.eli}`)
    } else throw new Error()

    addToast({
      summary: "Verkündung erfolgreich hochgeladen",
      detail: "Sie können mit der Arbeit an der neuen Verkündung beginnen.",
      severity: "success",
      life: 10000,
    })
  } catch {
    // Failed to parse the response = show generic error
    error.value = true
  }
}

function onUploadError(event: FileUploadErrorEvent) {
  isLoading.value = false

  try {
    const responseData = JSON.parse(event.xhr.responseText)
    if (responseData.type === "/errors/norm-exists-already") {
      showConfirmForceUpload()
    } else {
      error.value = responseData
    }
  } catch {
    // Failed to parse the error response = show generic error
    error.value = true
  }
}

const { errorListId } = useElementId()

const confirm = useConfirm()

const showConfirmForceUpload = () => {
  confirm.require({
    header: "Verkündung existiert bereits",
    message:
      "Diese Verkündung befindet sich bereits im System. Möchten Sie die bestehende Verkündung überschreiben?",
    acceptProps: {
      label: "Überschreiben",
      severity: "secondary",
      autofocus: false,
    },
    rejectProps: {
      label: "Abbrechen",
      severity: "primary",
      autofocus: true,
    },
    acceptClass: "w-full",
    rejectClass: "w-full",
    accept: () => {
      forceUpload()
    },
    reject: () => {
      resetUploadPage()
    },
  })
}

const uploadUrl = ref("/api/v1/announcements")

async function forceUpload() {
  if (!chosenFile.value) return
  isLoading.value = true

  const { data, error } = await useForceUploadFile(chosenFile.value)

  if (data.value) {
    addToast({
      summary: "Verkündung erfolgreich hochgeladen",
      detail: "Sie können mit der Arbeit an der neuen Verkündung beginnen.",
      severity: "success",
      life: 10000,
    })
    if (data.value.eli) {
      await router.push(`/amending-laws/${data.value.eli}`)
    }
  } else if (error.value) {
    addToast({
      summary: "Fehler beim Überschreiben",
      detail: "Das Überschreiben der Verkündung ist fehlgeschlagen.",
      severity: "error",
      life: 10000,
    })
  }

  isLoading.value = false
}

function resetUploadPage() {
  error.value = undefined
  isLoading.value = false
  uploadUrl.value = "/api/v1/announcements"
}
</script>

<template>
  <RisHeader :back-destination="{ name: 'Home' }" :breadcrumbs="breadcrumbs" />

  <div class="flex flex-col bg-gray-100 p-24 pb-64">
    <h1 class="ris-heading2-regular mb-64">Upload</h1>

    <div class="mx-auto flex w-full max-w-[640px] flex-col items-center gap-24">
      <span class="ris-body1-regular text-center">
        Importieren Sie eine XML-Datei, um die Verkündung hinzuzufügen.
      </span>

      <div
        class="flex w-full flex-col items-center gap-24 rounded-lg border-2 border-dashed border-blue-500 bg-white p-48"
      >
        <FileUpload
          ref="fileUpload"
          :disabled="isLoading"
          :multiple="false"
          accept="application/xml, text/xml"
          invalid-file-type-message="{0} ist keine XML-Datei."
          mode="basic"
          name="file"
          :url="uploadUrl"
          @select="onFileChoosing"
          @before-upload="onBeginUpload()"
          @error="onUploadError"
          @upload="onUploaded"
        />

        <Button
          :loading="isLoading"
          label="Hochladen"
          severity="secondary"
          @click="upload()"
        />
      </div>

      <Message v-if="errorMessage" severity="error" class="w-full">
        <p class="ris-label1-bold mb-6 mt-4">{{ errorMessage.title }}</p>
        <p v-if="errorMessage.message" class="ris-label1-regular">
          {{ errorMessage.message }}
        </p>
      </Message>

      <div v-if="errorList" class="flex w-full flex-col gap-8">
        <RisCopyableLabel
          text="In die Zwischenablage kopieren"
          :value="errorList"
          class="ml-auto"
        />

        <!-- eslint-disable-next-line vuejs-accessibility/label-has-for -->
        <label :for="errorListId" class="sr-only">Fehlerliste</label>
        <output
          :id="errorListId"
          class="block w-full overflow-auto whitespace-pre bg-white p-16 font-mono text-sm"
        >
          {{ errorList }}
        </output>
      </div>
    </div>
  </div>
  <ConfirmDialog>
    <template #icon>
      <IcBaselineErrorOutline class="text-red-800" />
    </template>
  </ConfirmDialog>
</template>
