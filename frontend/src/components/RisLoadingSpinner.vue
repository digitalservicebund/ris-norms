<template>
  <div
    role="status"
    aria-label="Lädt..."
    class="inline-block animate-spin rounded-full border-[3px] border-solid align-[-0.125em] motion-reduce:animate-[spin_1.5s_linear_infinite]"
    :class="$style.spinner"
  />
</template>

<style module>
/* Managing size and color as a separate class with a 0-specificity selector so
they can be easily overridden using classes on the RisLoadingSpinner component.
See RisLoadingSpinner.story.vue for an example. */
:where(.spinner) {
  height: var(--spacing-32);
  width: var(--spacing-32);
  border-color: var(--color-blue-700);
  border-right-color: transparent;
}
</style>
