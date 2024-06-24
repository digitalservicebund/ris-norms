<script setup lang="ts">
import RisTextButton from "@/components/controls/RisTextButton.vue"
import { useElementId } from "@/composables/useElementId"
import {
  InjectionKey,
  MaybeRefOrGetter,
  computed,
  inject,
  provide,
  shallowRef,
  toValue,
} from "vue"
import { RouteLocationRaw, RouterLink, useRouter } from "vue-router"
import IcBaselineArrowBack from "~icons/ic/baseline-arrow-back"

const props = withDefaults(
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

/* -------------------------------------------------- *
 * Breadcrumbs                                        *
 * -------------------------------------------------- */

/**
 * Additional breadcrumbs that can be appended by children of the header
 * component.
 */
const localBreadcrumbs = shallowRef<HeaderBreadcrumb[]>([])

const allBreadcrumbs = computed(() => [
  ...props.breadcrumbs,
  ...localBreadcrumbs.value,
])

function removeBreadcrumb(key: string) {
  const index = localBreadcrumbs.value.findIndex((i) => i.key === key)
  localBreadcrumbs.value = localBreadcrumbs.value.toSpliced(index, 1)
}

const pushBreadcrumb: HeaderContext["pushBreadcrumb"] = (...breadcrumb) => {
  const breadcrumbsWithKey = breadcrumb.map((i) => {
    const { key } = useElementId("breadcrumb")
    return { ...i, key }
  })

  localBreadcrumbs.value = [...localBreadcrumbs.value, ...breadcrumbsWithKey]

  return () => {
    breadcrumbsWithKey.forEach(({ key }) => removeBreadcrumb(key))
  }
}

provide(HeaderContextProvider, {
  pushBreadcrumb,
})
</script>

<script lang="ts">
/**
 * Can be used to teleport content to the action slot of the header. Note that
 * the teleport will break if no header exists on the page. Example:
 *
 * ```html
 * <Teleport :to="HEADER_ACTION_TARGET">
 *   <RisTextButton label="Click me" />
 * </Teleport>
 * ```
 */
export const HEADER_ACTION_TARGET = "#header-action-target"

/** Breadcrumb item displayed in the header. */
export type HeaderBreadcrumb = {
  /** Unique key used for rendering and managing the breadcrumbs. */
  key: string

  /** Visible label of the breadcrumb. */
  title: MaybeRefOrGetter<string>

  /**
   * Link destination of the breadcrumb. If this is not provided, the breadcrumb
   * will render as non-interactive text instead and will be skipped during
   * back-navigation.
   */
  to?: RouteLocationRaw
}

/** Additional functionality for interacting with the header. */
export type HeaderContext = {
  /**
   * Append breadcrumbs to the current list. Returns a `cleanup` function that
   * should be called `onUnmount` to remove the breadcrumbs again.
   *
   * @param breadcrumb
   * @returns Cleanup function
   */
  pushBreadcrumb: (...breadcrumb: Omit<HeaderBreadcrumb, "key">[]) => () => void
}

const HeaderContextProvider: InjectionKey<HeaderContext> = Symbol()

/**
 * Provides utilities for interacting with the header, e.g. adding breadcrumbs.
 * Note that for this to work, the component calling it needs to be a descendant
 * of a `RisHeader`.
 *
 * Returns a fallback object with no-op functions such that the composable is
 * safe to use in components that may or may not be used as a descendant of a
 * header.
 */
export function useHeaderContext() {
  return inject(HeaderContextProvider, {
    pushBreadcrumb: () => () => {},
  } as HeaderContext)
}
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
          v-for="crumb in allBreadcrumbs"
          :key="crumb.key"
          class="ds-body-01-reg after:mx-8 after:inline-block after:text-gray-700 after:content-['/'] last-of-type:after:hidden"
        >
          <RouterLink
            v-if="crumb.to"
            :to="crumb.to"
            class="ds-link-01-bold underline"
          >
            {{ toValue(crumb.title) }}
          </RouterLink>
          <span v-else data-testid="text-only-breadcrumb">
            {{ toValue(crumb.title) }}
          </span>
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

  <slot />
</template>
