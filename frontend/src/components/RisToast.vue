<script setup lang="ts">
import { TOAST_GROUP } from "@/composables/useToast"
import {
  RisCopyableLabel,
  RisExpandableText,
} from "@digitalservicebund/ris-ui/components"
import { Toast } from "primevue"
import IcBaselineCheck from "~icons/ic/baseline-check"
import IcBaselineWarningAmber from "~icons/ic/baseline-warning-amber"
import IcErrorOutline from "~icons/ic/error-outline"
import IcOutlineInfo from "~icons/ic/outline-info"
</script>

<template>
  <Toast :group="TOAST_GROUP">
    <template #message="slot">
      <div class="flex w-auto max-w-320 gap-10">
        <!-- Icons -->
        <IcBaselineCheck
          v-if="slot.message.severity === 'success'"
          class="ris-label1-regular flex-none text-green-800"
        />
        <IcBaselineWarningAmber
          v-else-if="slot.message.severity === 'warn'"
          class="ris-label1-regular flex-none text-black"
        />
        <IcErrorOutline
          v-else-if="slot.message.severity === 'error'"
          class="ris-label1-regular flex-none text-red-800"
        />
        <IcOutlineInfo
          v-else
          class="ris-label1-regular flex-none text-blue-800"
        />

        <div class="mt-2">
          <!-- Summary -->
          <p class="ris-label1-bold">{{ slot.message.summary }}</p>

          <!-- Regular string messages -->
          <p
            v-if="typeof slot.message.detail === 'string'"
            class="ris-label1-regular"
          >
            {{ slot.message.detail }}
          </p>

          <!-- Error messages -->
          <template v-else>
            <RisExpandableText
              v-if="slot.message.detail?.message"
              class="ris-label1-regular"
            >
              {{ slot.message.detail?.message }}
            </RisExpandableText>
            <p
              v-if="slot.message.detail?.suggestion"
              class="ris-label1-regular mt-6"
            >
              {{ slot.message.detail.suggestion }}
            </p>
            <div v-if="slot.message.detail?.traceId" class="mt-8">
              <RisCopyableLabel
                name="Trace-ID"
                text="Trace-ID kopieren"
                :value="slot.message.detail?.traceId"
              />
            </div>
          </template>
        </div>
      </div>
    </template>
  </Toast>
</template>
