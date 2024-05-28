<script setup lang="ts">
import RisAmendingLawInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
</script>

<template>
  <Story :layout="{ type: 'grid', width: '800px' }">
    <Variant title="Announced printed">
      <RisAmendingLawInfoHeader
        :amending-law="{
          eli: 'eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1',
          frbrName: 'BGBl. I',
          frbrDateVerkuendung: '2017-03-15',
          frbrNumber: 's419',
          title:
            'Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes',
        }"
      />
    </Variant>
    <Variant title="Announced digitally">
      <RisAmendingLawInfoHeader
        :amending-law="{
          eli: 'eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1',
          frbrName: 'BGBl. I',
          frbrDateVerkuendung: '2023-12-29',
          frbrNumber: '413',
          title:
            'Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts',
        }"
      />
    </Variant>
  </Story>
</template>
