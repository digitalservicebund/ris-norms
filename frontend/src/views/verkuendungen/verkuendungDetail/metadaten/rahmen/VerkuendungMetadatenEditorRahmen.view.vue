<script setup lang="ts">
import { useHeaderContext } from "@/components/RisHeader.vue"
import Button from "primevue/button"
import RisMetadataEditorRahmen from "@/components/metadata-editor/rahmen/RisMetadataEditorRahmen.vue"
import { useNormExpressionEliPathParameter } from "@/composables/useNormExpressionEliPathParameter"

const normExpressionEli = useNormExpressionEliPathParameter()
const { actionTeleportTarget } = useHeaderContext()
</script>

<template>
  <RisMetadataEditorRahmen :norm-expression-eli="normExpressionEli">
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
  </RisMetadataEditorRahmen>
</template>
