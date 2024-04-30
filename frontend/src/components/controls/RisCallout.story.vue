<script setup lang="ts">
import IcOutlineChecklistRtl from "~icons/ic/outline-checklist-rtl"
import RisCallout from "./RisCallout.vue"
import RisTextButton from "./RisTextButton.vue"
</script>

<template>
  <Story :layout="{ type: 'grid', width: '400px' }">
    <Variant title="Neutral">
      <RisCallout title="Hello world" />
    </Variant>

    <Variant title="Error">
      <RisCallout title="Hello world" variant="error" />
    </Variant>

    <Variant title="Warning">
      <RisCallout title="Hello world" variant="warning" />
    </Variant>

    <Variant title="Success">
      <RisCallout title="Hello world" variant="success" />
    </Variant>

    <Variant title="With text">
      <RisCallout title="Hello world">
        Additional content in one row.
      </RisCallout>
    </Variant>

    <Variant title="With multiple paragraphs">
      <RisCallout title="Hello world">
        <p>Lorem ipsum dolor sit amet.</p>
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Dolor maiores
          harum laborum.
        </p>
      </RisCallout>
    </Variant>

    <Variant title="With action">
      <RisCallout title="Hello world" variant="error">
        <p>A suggested action:</p>
        <RisTextButton label="Retry" variant="tertiary" />
      </RisCallout>
    </Variant>

    <Variant title="With dismiss button">
      <RisCallout title="Hello world" variant="neutral" allow-dismiss>
        <p>Lorem ipsum dolor sit amet.</p>
      </RisCallout>
    </Variant>

    <Variant title="Custom icon">
      <RisCallout title="Successfully validated" variant="success">
        <template #icon>
          <IcOutlineChecklistRtl />
        </template>
      </RisCallout>
    </Variant>
  </Story>
</template>
