<script setup lang="ts">
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import Button from "primevue/button"
import { useHeaderContext } from "@/components/RisHeader.vue"
import RisMetadataEditorElement from "@/components/metadata-editor/element/RisMetadataEditorElement.vue"
import { useNormExpressionEliPathParameter } from "@/composables/useNormExpressionEliPathParameter"
import { computed } from "vue"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

const normExpressionEli = useNormExpressionEliPathParameter()
const dokumentExpressionEli = computed(() =>
  DokumentExpressionEli.fromNormExpressionEli(normExpressionEli.value),
)
const elementEid = useEidPathParameter()

const { actionTeleportTarget } = useHeaderContext()
</script>

<template>
  <RisMetadataEditorElement
    v-if="elementEid"
    :e-id="elementEid"
    :dokument-expression-eli="dokumentExpressionEli"
  >
    <template #save="{ disabled, loading, save }">
      <Teleport v-if="actionTeleportTarget" :to="actionTeleportTarget">
        <div class="relative">
          <Button
            :disabled="disabled"
            :loading="loading"
            label="Speichern"
            @click="() => save()"
          />
        </div>
      </Teleport>
    </template>
  </RisMetadataEditorElement>
</template>
