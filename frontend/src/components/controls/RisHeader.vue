<script setup lang="ts">
import RisTextButton from "@/components/controls/RisTextButton.vue"
import { useElementId } from "@/composables/useElementId"
import {
  InjectionKey,
  MaybeRefOrGetter,
  Ref,
  computed,
  inject,
  onMounted,
  provide,
  ref,
  shallowRef,
  toValue,
} from "vue"
import { RouteLocationRaw, RouterLink, useRouter } from "vue-router"
import IcBaselineArrowBack from "~icons/ic/baseline-arrow-back"

const props = withDefaults(
  defineProps<{
    /**
     * Destination of the back button. Use `history-back` to simply go to the
     * previous page in the browser history. Use `breadcrumb-back` to go to
     * the previous breadcrumb. You can also provide a fixed route location.
     *
     * @default "breadcrumb-back"
     */
    backDestination?: "history-back" | "breadcrumb-back" | RouteLocationRaw

    /**
     * List of breadcrumbs to be displayed in the header. The list is assumed to
     * be ordered and the last item is treated as the current page.
     */
    breadcrumbs?: HeaderBreadcrumb[]
  }>(),
  {
    backDestination: "breadcrumb-back",
    breadcrumbs: () => [],
  },
)

const router = useRouter()

/* -------------------------------------------------- *
 * Teleport target                                    *
 * -------------------------------------------------- */

/** Static ID for the teleport target in the actions section of the header. */
const { actionTargetId } = useElementId("header-actions")

/**
 * Reactive version of the teleport ID. This will be a valid teleport target
 * once the component has been mounted. If the teleport can't be used yet,
 * the value will be null.
 */
const safeActionTargetId = ref<string | null>(null)

onMounted(() => {
  safeActionTargetId.value = `#${actionTargetId}`
})

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

// Expose header functionality to child components
provide(HeaderContextProvider, {
  pushBreadcrumb,
  actionTeleportTarget: safeActionTargetId,
})

/* -------------------------------------------------- *
 * Back button                                        *
 * -------------------------------------------------- */

const backbuttonTo = computed(() => {
  if (props.backDestination === "breadcrumb-back") {
    const current = router.resolve(router.currentRoute.value)

    return allBreadcrumbs.value
      .filter((i) => Boolean(i.to))
      .findLast((i) => {
        // @ts-expect-error `to` is not undefined, trust me
        const resolved = router.resolve(i.to)
        return resolved !== current
      })?.to
  } else if (props.backDestination === "history-back") {
    return undefined
  } else {
    return props.backDestination
  }
})

const showBackButtonSeparator = computed(
  () =>
    (backbuttonTo.value || props.backDestination === "history-back") &&
    allBreadcrumbs.value?.length,
)
</script>

<script lang="ts">
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

  /**
   * Returns the ID (including #) of the teleport target that can be used for
   * displaying actions in the header. The value will be null if no header
   * is available, or when the header exists but hasn't been mounted yet. Once
   * the value is a string, the teleport is safe to use. Example:
   *
   * ```html
   * <Teleport v-if="actionTeleportTarget" :to="actionTeleportTarget">
   *   <RisTextButton label="Click me" />
   * </Teleport>
   * ```
   */
  actionTeleportTarget: Ref<string | null>
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
    actionTeleportTarget: ref(null),
  } as HeaderContext)
}
</script>

<template>
  <header
    class="flex min-h-80 items-center gap-12 border-b border-solid border-b-gray-400 bg-blue-200 p-16 pr-32"
    v-bind="$attrs"
  >
    <section class="flex items-center">
      <!-- Back button -->
      <RouterLink
        v-if="backbuttonTo"
        :to="backbuttonTo"
        class="ds-button ds-button-small ds-button-ghost ds-button-with-icon ds-button-with-icon-only"
      >
        <IcBaselineArrowBack />
        <span class="sr-only">Zurück</span>
      </RouterLink>
      <RisTextButton
        v-else-if="backDestination === 'history-back'"
        :icon="IcBaselineArrowBack"
        icon-only
        label="Zurück"
        variant="ghost"
        size="small"
        @click="router.back()"
      />

      <span
        v-if="showBackButtonSeparator"
        class="mr-8 text-gray-700"
        data-testid="back-button-separator"
        >/</span
      >

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
