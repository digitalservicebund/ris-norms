<script setup lang="ts">
import { ref } from "vue"
import RisLawPreview from "./RisLawPreview.vue"

const content = ref(`
  <div>
    <div class="akn-act">
      <h1 class="akn-longTitle" data-eid="act1">Act Title</h1>
      <div class="akn-preface">This is the preface of the act.</div>
      <div class="akn-body">
        <div class="akn-section" data-eid="section1">
          <h2>Section 1</h2>
          <div class="akn-article" data-eid="article1">
            <h3>Article 1</h3>
            <div class="akn-paragraph" data-eid="paragraph1">
              <span class="akn-num">1.</span>
              <span class="akn-content">This is the first paragraph.</span>
            </div>
            <div class="akn-paragraph akn-mod" data-eid="mod1">
              <span class="akn-num">2.</span>
              <span class="akn-content">This paragraph is modified.</span>
            </div>
          </div>
        </div>
        <div class="akn-section" data-eid="section2">
          <h2>Section 2</h2>
          <div class="akn-article akn-affectedDocument" data-eid="article2">
            <h3>Article 2</h3>
            <div class="akn-paragraph" data-eid="paragraph2">
              <span class="akn-num">1.</span>
              <span class="akn-content">This is another paragraph.</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
`)

const selected = ref(["mod1"])

function handleClick({ eid }: { eid: string }) {
  alert(`Clicked on element with eId: ${eid}`)
}
</script>

<template>
  <Story>
    <Variant title="Default">
      <RisLawPreview
        :content="content"
        highlight-affected-document
        :selected="selected"
        @click:akn:mod="handleClick"
        @click:akn:article="handleClick"
      />
    </Variant>
    <Variant title="Custom classes on specific eId">
      <RisLawPreview
        :content="content"
        :e-id-classes="{
          paragraph1: ['bg-blue-900', 'text-white'],
        }"
      />
    </Variant>
  </Story>
</template>
