<script setup lang="ts">
import RisLawPreview from "@/components/RisLawPreview.vue"
import { useAmendingLawHtml } from "@/composables/useAmendingLawHtml"
import { useArticleXml } from "@/composables/useArticleXml"
import { useEliPathParameter } from "@/composables/useEliPathParameter"

const eli = useEliPathParameter()
const { html: amendingLawHtml, reload } = useAmendingLawHtml(eli)

/* -------------------------------------------------- *
 * Saving changes                                     *
 * -------------------------------------------------- */

const amendingLawXml = useArticleXml(() => ({ eli: eli.value, eid: "" }))

async function saveChanges(value: {
  modifiedElement: string
  targetLawEli: string
  quotedTextFrom: string
  quotedTextTo: string
}) {
  const parser = new DOMParser()
  const xmlDom = parser.parseFromString(
    amendingLawXml.xml.value ?? "",
    "application/xml",
  )

  const mod = xmlDom.querySelector(`[eId=${value.modifiedElement}]`)

  if (!mod) {
    console.log(`[eId=${value.modifiedElement}] not found!`)
    return
  }

  const quotedTextEls = mod.querySelectorAll("quotedText")
  quotedTextEls[0].innerHTML = value.quotedTextFrom
  quotedTextEls[1].innerHTML = value.quotedTextTo

  const serializer = new XMLSerializer()
  const result = serializer.serializeToString(xmlDom)

  await amendingLawXml.update(result)
  reload()
}
</script>

<template>
  <div>
    <h1 class="ds-heading-02-reg mb-40">Verkündung</h1>
    <RisLawPreview
      class="drop-shadow-md"
      :content="amendingLawHtml ?? ''"
      highlight-mods
      highlight-affected-document
      @save="saveChanges($event)"
    />
  </div>
</template>
