<script setup lang="ts">
import { useEidPathParameter } from "@/composables/useEidPathParameter"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import Button from "primevue/button"
import { useHeaderContext } from "@/components/RisHeader.vue"
import RisMetadataEditorElement from "@/components/metadata-editor/element/RisMetadataEditorElement.vue"

const dokumentExpressionEli = useDokumentExpressionEliPathParameter()
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
