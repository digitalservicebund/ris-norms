<script setup lang="ts">
import RisCopyableLabel from "@/components/controls/RisCopyableLabel.vue"
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisTooltip from "./RisTooltip.vue"
</script>

<template>
  <Story :layout="{ type: 'grid', width: '400px' }">
    <Variant title="Top left">
      <div>
        <div class="mb-16 h-160 w-full bg-gray-100/50"></div>
        <RisTooltip
          v-slot="{ ariaDescribedby }"
          title="Something went wrong"
          alignment="left"
          attachment="top"
          variant="error"
        >
          <RisTextButton :aria-describedby label="Button" />
        </RisTooltip>
      </div>
    </Variant>

    <Variant title="Top right">
      <div class="flex flex-col items-end">
        <div class="mb-16 h-160 w-full bg-gray-100/50"></div>
        <RisTooltip
          v-slot="{ ariaDescribedby }"
          title="Something went wrong"
          alignment="right"
          attachment="top"
          variant="error"
        >
          <RisTextButton :aria-describedby label="Button" />
        </RisTooltip>
      </div>
    </Variant>

    <Variant title="Bottom left">
      <div>
        <RisTooltip
          v-slot="{ ariaDescribedby }"
          title="Something went wrong"
          alignment="left"
          attachment="bottom"
          variant="error"
        >
          <RisTextButton :aria-describedby label="Button" />
        </RisTooltip>
        <div class="mt-16 h-160 w-full bg-gray-100/50"></div>
      </div>
    </Variant>

    <Variant title="Bottom right">
      <div class="flex flex-col items-end">
        <RisTooltip
          v-slot="{ ariaDescribedby }"
          title="Something went wrong"
          alignment="right"
          attachment="bottom"
          variant="error"
        >
          <RisTextButton :aria-describedby label="Button" />
        </RisTooltip>
        <div class="mt-16 h-160 w-full bg-gray-100/50"></div>
      </div>
    </Variant>

    <Variant title="With block child element">
      <div class="py-128">
        <RisTooltip
          title="Something went wrong"
          alignment="right"
          attachment="bottom"
        >
          <div class="bg-gray-100 p-16"></div>
        </RisTooltip>
      </div>
    </Variant>

    <Variant title="Standalone">
      <div class="py-8">
        <RisTooltip title="Something went wrong" />
      </div>
    </Variant>

    <Variant title="With content">
      <div class="py-8">
        <RisTooltip title="Something went wrong">
          <template #message>
            <RisCopyableLabel text="Copy error ID" value="4711" />
          </template>
        </RisTooltip>
      </div>
    </Variant>
  </Story>
</template>
