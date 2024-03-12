<script setup lang="ts">
import RisTextButton from "@/components/controls/RisTextButton.vue"
import IconCheck from "~icons/ic/baseline-check"
</script>

<template>
  <div class="flex flex-col gap-[60px]">
    <section class="flex flex-col gap-40">
      <h1 class="ds-heading-02-reg">Abgabe</h1>

      <section class="flex gap-20">
        <h2 class="w-1/4">1. Plausibilitätsprüfung</h2>

        <div class="flex flex-grow gap-8">
          <IconCheck class="text-green-700" />
          <span>LDML entspricht dem Standard 1.6</span>
        </div>

        <RisTextButton
          variant="tertiary"
          size="small"
          label="Erneut prüfen"
          disabled
        />
      </section>

      <hr class="border-t-gray-600" />

      <section class="flex gap-20">
        <h2 class="w-1/4">2. Abgabe veranlassen</h2>

        <div class="flex flex-grow gap-8">
          <span>Gesamtes Änderungsgesetz im Portal veröffentlichen.</span>
        </div>
      </section>

      <hr class="border-t-gray-600" />

      <RisTextButton
        class="w-fit"
        variant="primary"
        size="small"
        label="Jetzt Abgeben"
        disabled
      />
    </section>

    <section class="flex flex-col gap-40">
      <h1 class="ds-heading-02-reg">Letzte Abgabe</h1>

      <span
        >Dieses Änderungsgesetz wurde zuletzt abgegeben am 26.02.2024 um 17:33
        Uhr
      </span>
    </section>
  </div>
</template>
