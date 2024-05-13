<script lang="ts" setup>
import RisDropdowninput from "@/components/controls/RisDropdowninput.vue"
import { DropdownItem } from "./RisDropdowninput.vue"
import { logEvent } from "histoire/client"
import { ref } from "vue"

const emptyDropdown = ref<string>("")
const preselectedDropdown = ref<string>("I3")
const dropdownItems: DropdownItem[] = [
  { label: "Dropdown item 1", value: "I1" },
  { label: "Dropdown item 2", value: "I2" },
  { label: "Dropdown item 3", value: "I3" },
]
</script>

<template>
  <Story :layout="{ type: 'grid', width: '400px' }">
    <Variant title="Default">
      <RisDropdowninput
        :label="'Click me'"
        :items="dropdownItems"
        :model-value="emptyDropdown"
        @click="logEvent('click', $event)"
      />
    </Variant>
    <Variant title="With Placeholder">
      <RisDropdowninput
        :label="'Click me'"
        :items="dropdownItems"
        :model-value="emptyDropdown"
        placeholder="Placeholder"
        @click="logEvent('click', $event)"
      />
    </Variant>
    <Variant title="With preselection">
      <RisDropdowninput
        :label="'Click me'"
        :items="dropdownItems"
        :model-value="preselectedDropdown"
        placeholder="Placeholder"
        @click="logEvent('click', $event)"
      />
    </Variant>
  </Story>
</template>
