<script setup lang="ts">
import RisTextButton from "@/components/controls/RisTextButton.vue"
import RisTabs from "@/components/editor/RisTabs.vue"
import { defineComponent, h, onUnmounted, ref } from "vue"
import RisHeader, { HeaderBreadcrumb, useHeaderContext } from "./RisHeader.vue"

const exampleBreadcrumbs: HeaderBreadcrumb[] = [
  { key: "0", title: "BGBL 1 2023 Nr. 413", to: "/" },
  { key: "1", title: "PassG", to: "/" },
  { key: "2", title: "Metadatendokumentation" },
]

/* eslint-disable-next-line vue/one-component-per-file */
const ExampleChild1 = defineComponent({
  setup() {
    const { pushBreadcrumb } = useHeaderContext()
    const cleanup = pushBreadcrumb({ title: "From first child" })

    onUnmounted(() => {
      cleanup()
    })
  },
})

/* eslint-disable-next-line vue/one-component-per-file */
const ExampleChild2 = defineComponent({
  setup() {
    const { pushBreadcrumb } = useHeaderContext()
    const cleanup = pushBreadcrumb(
      { title: "From second child" },
      { title: "Another one" },
    )

    onUnmounted(() => {
      cleanup()
    })
  },
})

/* eslint-disable-next-line vue/one-component-per-file */
const ExampleChild3 = defineComponent({
  setup() {
    const counter = ref("1")

    function increment() {
      const current = Number.parseInt(counter.value)
      counter.value = (current + 1).toString()
    }

    const { pushBreadcrumb } = useHeaderContext()
    const cleanup = pushBreadcrumb({ title: counter }, { title: "Another one" })

    onUnmounted(() => {
      cleanup()
    })

    return () =>
      h(RisTextButton, { onClick: () => increment(), label: "Increment" })
  },
})
</script>

<template>
  <Story>
    <Variant title="Normal">
      <RisHeader :breadcrumbs="exampleBreadcrumbs" />
    </Variant>

    <Variant title="With action in slot">
      <RisHeader :breadcrumbs="exampleBreadcrumbs">
        <template #action>
          <RisTextButton label="Click me" size="small" />
        </template>
      </RisHeader>
    </Variant>

    <Variant title="With child breadcrumbs">
      <RisHeader :breadcrumbs="[{ key: '0', title: 'From parent' }]">
        <div class="p-24">
          <RisTabs
            :tabs="[
              { id: 'child1', label: 'Child 1' },
              { id: 'child2', label: 'Child 2' },
            ]"
          >
            <template #child1>
              <ExampleChild1 />
            </template>
            <template #child2>
              <ExampleChild2 />
            </template>
          </RisTabs>
        </div>
      </RisHeader>
    </Variant>

    <Variant title="With dynamic title">
      <RisHeader :breadcrumbs="[{ key: '0', title: 'From parent' }]">
        <div class="p-24">
          <ExampleChild3 />
        </div>
      </RisHeader>
    </Variant>
  </Story>
</template>
