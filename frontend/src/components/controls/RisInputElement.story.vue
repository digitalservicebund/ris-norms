<script lang="ts" setup>
import RisDropdowninput from "@/components/controls/RisDropdowninput.vue"
import RisTextInput from "@/components/controls/RisTextInput.vue"
import { DropdownItem } from "./RisDropdowninput.vue"
import { logEvent } from "histoire/client"
import { ref } from "vue"

const emptyDropdown = ref<string>("")
const dropdownItems: DropdownItem[] = [
  { label: "Dropdown item 1", value: "I1" },
  { label: "Dropdown item 2", value: "I2" },
  { label: "Dropdown item 3", value: "I3" },
]
</script>

<template>
  <Story :layout="{ type: 'grid', width: '400px' }">
    <Variant title="Dropdown Input">
      <RisDropdowninput
        :label="'Click me'"
        :items="dropdownItems"
        :model-value="emptyDropdown"
        @click="logEvent('click', $event)"
      />
    </Variant>
    <Variant title="Text Input">
      <RisTextInput
        id="test-id"
        placeholder="Placeholder text"
        size="medium"
      ></RisTextInput>
    </Variant>
  </Story>
</template>
