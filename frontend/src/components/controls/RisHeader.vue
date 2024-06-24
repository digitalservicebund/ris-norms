<script setup lang="ts">
import RisTextButton from "@/components/controls/RisTextButton.vue"
import { RouteLocationRaw, RouterLink, useRouter } from "vue-router"
import IcBaselineArrowBack from "~icons/ic/baseline-arrow-back"

/** Breadcrumb item displayed in the header. */
export type HeaderBreadcrumb = {
  /** Unique key used for rendering and managing the breadcrumbs. */
  key: string

  /** Visible label of the breadcrumb. */
  title: string

  /**
   * Link destination of the breadcrumb. If this is not provided, the breadcrumb
   * will render as non-interactive text instead and will be skipped during
   * back-navigation.
   */
  to?: RouteLocationRaw
}

withDefaults(
  defineProps<{
    /**
     * Destination of the back button. Use `history-back` to simply go to the
     * previous page in the browser history. You can also provide a route
     * location for more precise control.
     *
     * @default "history-back"
     */
    backDestination?: "history-back" | RouteLocationRaw

    /**
     * List of breadcrumbs to be displayed in the header. The list is assumed to
     * be ordered and the last item is treated as the current page.
     */
    breadcrumbs: HeaderBreadcrumb[]
  }>(),
  {
    backDestination: "history-back",
  },
)

const router = useRouter()

const actionTargetId = HEADER_ACTION_TARGET.replace(/^#/, "")
</script>

<script lang="ts">
export const HEADER_ACTION_TARGET = "#header-action-target"
</script>

<template>
  <header
    class="flex min-h-80 items-center gap-12 border-b border-solid border-b-gray-400 bg-blue-200 p-16 pr-32"
  >
    <section class="flex items-center gap-x-8">
      <!-- Back button -->
      <RisTextButton
        v-if="backDestination === 'history-back'"
        :icon="IcBaselineArrowBack"
        icon-only
        label="Zurück"
        variant="ghost"
        size="small"
        @click="router.back()"
      />

      <RouterLink v-else :to="backDestination">
        <IcBaselineArrowBack />
        <span class="sr-only">Zurück</span>
      </RouterLink>

      <!-- Bread crumbs -->
      <nav class="line-clamp-2 leading-5">
        <span
          v-for="crumb in breadcrumbs"
          :key="crumb.key"
          class="ds-body-01-reg after:mx-8 after:inline-block after:text-gray-800 after:content-['/'] last-of-type:after:hidden"
        >
          <RouterLink
            v-if="crumb.to"
            :to="crumb.to"
            class="ds-link-01-bold underline"
          >
            {{ crumb.title }}
          </RouterLink>
          <template v-else>{{ crumb.title }}</template>
        </span>
      </nav>
    </section>

    <!-- Actions -->
    <section class="ml-auto flex-none">
      <slot name="action">
        <div :id="actionTargetId"></div>
      </slot>
    </section>
  </header>
</template>
