<script setup lang="ts">
import Button from "primevue/button"
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
import { useDebounceFn } from "@vueuse/core"
import Breadcrumb from "primevue/breadcrumb"

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

const allBreadcrumbs = computed(() => {
  const breadcrumbs = []

  if (props.backDestination) {
    const backDestination = props.backDestination

    if (backDestination === "breadcrumb-back") {
      const currentFullPath = router.resolve(router.currentRoute.value).fullPath

      const previousBreadcrumb = [
        ...props.breadcrumbs,
        ...localBreadcrumbs.value,
      ]
        .filter((i) => Boolean(i.to))
        .findLast((i) => {
          // @ts-expect-error `to` is not undefined, trust me
          const resolvedPath = router.resolve(i.to).fullPath
          return resolvedPath !== currentFullPath
        })
      breadcrumbs.push({
        key: "back-button",
        title: "Zurück",
        to: previousBreadcrumb?.to || "history-back",
        type: "breadcrumb-back",
      })
    } else if (backDestination === "history-back") {
      breadcrumbs.push({
        key: "back-button",
        title: "Zurück",
        type: "history-back",
      })
    } else {
      breadcrumbs.push({
        key: "back-button",
        title: "Zurück",
        to: backDestination,
        type: "route-back",
      })
    }
  }

  breadcrumbs.push(...props.breadcrumbs, ...localBreadcrumbs.value)

  return breadcrumbs
})

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

function handleBreadcrumbClick(to: RouteLocationRaw) {
  router.push(to)
}

/*
 * Debounce the breadcrumb click handler with a delay of 20 milliseconds to prevent rapid consecutive navigation requests
 * which can cause aborted fetch requests and display error messages in Firefox .
 */
const debouncedBreadcrumbClick = useDebounceFn(handleBreadcrumbClick, 20)

// Expose header functionality to child components
provide(HeaderContextProvider, {
  pushBreadcrumb,
  actionTeleportTarget: safeActionTargetId,
})
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
    class="flex min-h-80 items-center gap-12 border-b border-solid border-b-gray-400 bg-blue-200 p-16 pr-24"
    v-bind="$attrs"
  >
    <section class="flex items-center">
      <Breadcrumb :model="allBreadcrumbs">
        <template #item="{ item, props: slotProps }">
          <template v-if="item.key === 'back-button'">
            <RouterLink
              v-if="item.to && item.to !== 'history-back'"
              v-slot="{ href }"
              :to="item.to"
              custom
            >
              <a
                :href="href"
                v-bind="slotProps.action"
                @click.prevent="debouncedBreadcrumbClick(item.to)"
              >
                <IcBaselineArrowBack />
                <span class="sr-only">Zurück</span>
              </a>
            </RouterLink>

            <Button
              v-else-if="item.to === 'history-back' || !item.to"
              label="Zurück"
              severity="text"
              @click="router.back()"
            >
              <template #icon>
                <IcBaselineArrowBack />
              </template>
            </Button>
          </template>

          <template v-else>
            <RouterLink
              v-if="item.to"
              v-slot="{ href, isExactActive }"
              :to="item.to"
              custom
            >
              <a
                v-if="!isExactActive"
                :href="href"
                v-bind="slotProps.action"
                @click.prevent="debouncedBreadcrumbClick(item.to)"
              >
                <span class="ris-link1-bold line-clamp-1">{{
                  toValue(item.title)
                }}</span>
              </a>
              <span
                v-else
                class="line-clamp-1"
                data-testid="current-route-breadcrumb"
              >
                {{ toValue(item.title) }}
              </span>
            </RouterLink>
            <span
              v-else
              data-testid="text-only-breadcrumb"
              class="line-clamp-1"
            >
              {{ toValue(item.title) }}
            </span>
          </template>
        </template>

        <template #separator>
          <span>/</span>
        </template>
      </Breadcrumb>
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
