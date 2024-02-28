<script setup lang="ts">
import RisCodeEditor from "@/components/editor/RisCodeEditor.vue"
import { ref } from "vue"

const ARTICLE = 1
const TARGET_LAW_TITLE = "Bundesverfassungsschutzgesetz"
const ARTICLE_TITLE =
  "Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts"
const ARTICLE_XML = `<akn:activeModifications eId="meta-1_analysis-1_activemod-1"
                  GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3ac">
  <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-1"
                 GUID="2e5533d3-d0e3-43ba-aa1a-5859d108eb46"
                 type="substitution">
    <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1"
                GUID="8b3e1841-5d63-4400-96ae-214f6ee28db6"
                href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"/>
    <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1"
                     GUID="94c1e417-e849-4269-8320-9f0173b39626"
                     href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-3.xml"/>
    <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1"
               GUID="6f5eabe9-1102-4d29-9d25-a44643354519"
               period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
  </akn:textualMod>
</akn:activeModifications>`
const TARGET_LAW_XML = ARTICLE_XML
const PREVIEW_XML = ARTICLE_XML

const currentArticleXml = ref(ARTICLE_XML)

function handleSave() {
  console.log("AmendingLawArticleEditor::handleSave")
}

function handleGeneratePreview() {
  console.log("AmendingLawArticleEditor::handleGeneratePreview")
}

function handleArticleXMLChange({ content }: { content: string }) {
  currentArticleXml.value = content
}
</script>

<template>
  <div class="flex flex-col" style="height: calc(100dvh - 40px * 2)">
    <div class="mb-40 flex">
      <div class="flex-grow">
        <h1 class="ds-heading-02-reg">Artikel {{ ARTICLE }}</h1>
        <h2 class="ds-heading-03-reg">Änderungsbefehle prüfen</h2>
      </div>

      <button
        class="ds-button ds-button-small mr-16 h-fit self-end"
        @click="handleSave"
      >
        Speichern
      </button>
      <button
        class="ds-button ds-button-small ds-button-tertiary h-fit self-end"
        @click="handleGeneratePreview"
      >
        Vorschau generieren
      </button>
    </div>

    <div class="gap grid min-h-0 flex-grow grid-cols-2 grid-rows-2 gap-32">
      <div class="flex flex-col gap-8">
        <h3 class="ds-label-02-bold">{{ TARGET_LAW_TITLE }}</h3>
        <RisCodeEditor
          class="flex-grow border border-black"
          :readonly="true"
          :editable="false"
          :initial-content="TARGET_LAW_XML"
        ></RisCodeEditor>
      </div>
      <div class="row-span-2 flex flex-col gap-8">
        <h3 class="ds-label-02-bold">Vorschau</h3>
        <RisCodeEditor
          class="flex-grow border border-black"
          :readonly="true"
          :editable="false"
          :initial-content="PREVIEW_XML"
        ></RisCodeEditor>
      </div>
      <div class="flex flex-col gap-8">
        <h3 class="ds-label-02-bold">
          <span class="block">Änderungsbefehle</span>
          <span>{{ ARTICLE_TITLE }}</span>
        </h3>
        <RisCodeEditor
          class="flex-grow border border-black"
          :initial-content="ARTICLE_XML"
          @change="handleArticleXMLChange"
        ></RisCodeEditor>
      </div>
    </div>
  </div>
</template>
