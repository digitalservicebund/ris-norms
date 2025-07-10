<script setup lang="ts">
import { useHeaderContext } from "@/components/RisHeader.vue"
import { useDokumentExpressionEliPathParameter } from "@/composables/useDokumentExpressionEliPathParameter"
import Button from "primevue/button"
import RisMetadataEditorRahmen from "@/components/metadata-editor/rahmen/RisMetadataEditorRahmen.vue"
import { onBeforeUnmount } from "vue"

const dokumentExpressionEli = useDokumentExpressionEliPathParameter()
const { actionTeleportTarget } = useHeaderContext()

const headerContext = useHeaderContext()

const cleanupBreadcrumb = headerContext.pushBreadcrumb({
  title: "Rahmen",
})

onBeforeUnmount(() => cleanupBreadcrumb())
</script>

<template>
  <RisMetadataEditorRahmen :dokument-expression-eli="dokumentExpressionEli">
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
