<script lang="ts" setup>
import { ref } from "vue"
import RisHeader, {
  HeaderBreadcrumb,
} from "@/components/controls/RisHeader.vue"
import RisCopyableLabel from "@/components/controls/RisCopyableLabel.vue"
import Button from "primevue/button"
import RisErrorCallout from "@/components/controls/RisErrorCallout.vue"

const breadcrumbs = ref<HeaderBreadcrumb[]>([
  {
    key: "uploadAnnouncement",
    title: "Verkündung manuell hinzufügen",
  },
])

const errorMessage = ref<string>(
  "Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          InaccessibleError Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          InaccessibleError Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          InaccessibleError Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          InaccessibleError Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          InaccessibleError Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          InaccessibleError Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible Error Description !ERROR=0 File, Record, or Device Busy or\n" +
    "          Inaccessible",
)
</script>

<template>
  <RisHeader
    class="col-span-2"
    :back-destination="{ name: 'Home' }"
    :breadcrumbs="breadcrumbs"
  />

  <div class="flex min-h-screen flex-col bg-gray-100 p-24">
    <h1 class="ds-heading-02-reg mb-8">Verkündung Manuell Hinzufügen</h1>
    <div
      class="mx-auto my-64 flex w-full flex-col items-center sm:w-4/5 md:w-3/4 lg:w-3/5"
    >
      <span class="ds-body-01-reg mb-8 text-center">
        Importieren Sie eine XML-Datei, um die Verkündung hinzuzufügen
      </span>
      <div
        class="my-24 w-full rounded-lg border-2 border-dashed border-blue-500 bg-white p-48 text-center"
      >
        <div
          class="flex w-full flex-col items-center justify-center space-y-20"
        >
          <Button label="Choose to upload" severity="primary" />
          <div>regelungstext.xml</div>
          <Button label="Hochladen" severity="secondary" />
        </div>
      </div>

      <!--      this would actually me a message component-->
      <RisErrorCallout class="min-w-lg mb-64 w-full max-w-4xl" error="" />

      <div class="min-w-lg w-full max-w-4xl">
        <div class="flex justify-end">
          <p class="ris-link2-regular inline-flex items-center gap-4">
            <RisCopyableLabel
              text="In die Zwischenablage kopieren"
              :value="errorMessage"
              name="In die Zwischenablage kopieren"
            />
          </p>
        </div>
        <span class="mb-4 block max-h-208 overflow-auto bg-white p-20">
          {{ errorMessage }}
        </span>
      </div>
    </div>
  </div>
</template>
