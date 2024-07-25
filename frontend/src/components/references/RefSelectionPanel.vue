<script setup lang="ts">
import RisCallout from "@/components/controls/RisCallout.vue"
import RisLawPreview from "@/components/RisLawPreview.vue"
import RisLoadingSpinner from "@/components/controls/RisLoadingSpinner.vue"
import { useNormRenderHtml } from "@/composables/useNormRender"

const selectedRef = defineModel<string>("selectedRef")
const xmlSnippet = defineModel<string>("xmlSnippet")

const {
  data: render,
  isFetching: renderLoading,
  error: renderError,
} = useNormRenderHtml(xmlSnippet, { snippet: true })

function test() {
  xmlSnippet.value = `<akn:mod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen"> Der <akn:ref GUID="4400b9ef-c992-49fe-9bb5-30bfd4519e5d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1" href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/einleitung-1_doktitel-1.xml">Titel</akn:ref> des Gesetzes wird ersetzt durch: <akn:quotedStructure GUID="9cb0572a-2933-473e-823f-5541ab360561" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1" endQuote="“" startQuote="„">
                      <akn:longTitle GUID="0505f7b3-54c8-4c9d-b456-cd84adfb98f1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1">
                        <akn:p GUID="6ad3f708-b3be-4dbf-b149-a61e72678105" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1_text-1">
                          <akn:docTitle GUID="ab481c1a-db58-4b6a-886c-1e9301952c34" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1_text-1_doctitel-1">Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten mit Änderungsbefehlen</akn:docTitle>
                          <akn:shortTitle GUID="820e7af3-fd8c-4409-949a-1e40ec2cc8e6" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1_text-1_kurztitel-1"> (Strukturänderungsgesetz!!!) </akn:shortTitle>
                        </akn:p>
                      </akn:longTitle>
                    </akn:quotedStructure>
                  </akn:mod>`
}
</script>

<template>
  <div class="flex flex-col">
    <div v-if="renderLoading" class="flex justify-center">
      <RisLoadingSpinner />
    </div>
    <RisCallout
      v-else-if="renderError"
      variant="error"
      title="Die Vorschau konnte nicht geladen werden."
    />
    <RisLawPreview
      class="ds-textarea flex-grow p-2"
      :content="render ?? ''"
      :selected="selectedRef ? [selectedRef] : []"
      @click="test"
    />
  </div>
</template>
