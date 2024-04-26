<script setup lang="ts">
import RisAmendingLawInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
</script>

<template>
  <Story>
    <Variant title="Announced printed">
      <RisAmendingLawInfoHeader
        :amending-law="{
          eli: 'eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1',
          printAnnouncementGazette: 'BGBl. I',
          digitalAnnouncementMedium: undefined,
          frbrDateVerkuendung: '2017-03-15',
          printAnnouncementPage: '419',
          digitalAnnouncementEdition: undefined,
          title:
            'Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes',
        }"
      />
    </Variant>
    <Variant title="Announced digitally">
      <RisAmendingLawInfoHeader
        :amending-law="{
          eli: 'eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1',
          printAnnouncementGazette: undefined,
          digitalAnnouncementMedium: 'BGBl. I',
          frbrDateVerkuendung: '2023-12-29',
          printAnnouncementPage: undefined,
          digitalAnnouncementEdition: '413',
          title:
            'Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts',
        }"
      />
    </Variant>
  </Story>
</template>
