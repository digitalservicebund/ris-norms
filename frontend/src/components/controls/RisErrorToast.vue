<script setup lang="ts">
import RisCopyableLabel from "@/components/controls/RisCopyableLabel.vue"
import { ERROR_TOAST_GROUP } from "@/lib/errorToast"
import Toast from "primevue/toast"
import IconErrorOutline from "~icons/ic/error-outline"
</script>

<template>
  <Toast :group="ERROR_TOAST_GROUP">
    <template #message="slot">
      <div class="flex w-320 gap-10">
        <IconErrorOutline class="ris-label1-regular flex-none text-red-800" />
        <div class="mt-2">
          <p class="ris-label1-bold">{{ slot.message.summary }}</p>

          <p v-if="slot.message.detail?.message" class="ris-label1-regular">
            {{ slot.message.detail?.message }}
          </p>

          <div v-if="slot.message.detail?.traceId" class="mt-8">
            <RisCopyableLabel
              name="Trace-ID"
              text="Trace-ID kopieren"
              :value="slot.message.detail?.traceId"
            />
          </div>
        </div>
      </div>
    </template>
  </Toast>
</template>
