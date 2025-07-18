<script setup lang="ts">
import RisErrorCallout from "@/components/RisErrorCallout.vue"
import type {
  HeaderBreadcrumb,
  RisHeaderBackDestination,
} from "@/components/RisHeader.vue"
import RisHeader from "@/components/RisHeader.vue"
import RisLoadingSpinner from "@/components/RisLoadingSpinner.vue"
import { use404Redirect } from "@/composables/use404Redirect"
import { watchDebounced } from "@vueuse/core"
import { computed, ref } from "vue"

const {
  breadcrumbs = undefined,
  errors = [],
  headerBackDestination = undefined,
  redirectOn404 = true,
  loading = false,
  disableLoadingDebounce = false,
} = defineProps<{
  /**
   * Destination of the back button of the header component. A header will be
   * shown if either this or `breadcrumbs` is set to a truthy value.
   */
  headerBackDestination?: RisHeaderBackDestination

  /**
   * List of breadcrumbs to be displayed in the header. A header will be shown
   * if either this or `headerBackDestination` is set to a truthy value.
   */
  breadcrumbs?: HeaderBreadcrumb[]

  /**
   * If set to true, will show a full page loading spinner instead of rendering
   * the default slot.
   */
  loading?: boolean

  /**
   * If set to a value, will show a full page error message instead of rendering
   * the default slot. The value will be passed to `RisErrorCallout`. Please
   * check the `error` prop of that component for more information on possible
   * error formats and how they will be handled.
   */
  errors?: any[] // eslint-disable-line @typescript-eslint/no-explicit-any -- Fetch errors are always any

  /**
   * If set to true, will automatically perform a redirect to the 404 page if
   * `error` has a 404 status.
   */
  redirectOn404?: boolean

  /**
   * If set to true, will disable the debouncing of the loading state,
   * making the loading spinner appear immediately without delay.
   */
  disableLoadingDebounce?: boolean
}>()

const slots = defineSlots<{
  default: never
  headerAction: never
}>()

const hasHeader = computed(
  () => headerBackDestination || breadcrumbs || slots.headerAction,
)

const errorToShow = computed(() => errors.find((e) => !!e))

if (redirectOn404) use404Redirect(() => errors)

const debouncedLoading = ref(loading)

watchDebounced(
  () => loading,
  (newVal) => {
    debouncedLoading.value = newVal
  },
  { debounce: 300 },
)

const effectiveLoading = computed(() =>
  disableLoadingDebounce ? loading : debouncedLoading.value,
)
</script>

<template>
  <div
    class="grid h-[calc(100dvh-5rem)] grid-cols-1 grid-rows-[5rem_1fr] overflow-hidden bg-gray-100 has-[[data-pc-name=splitter]]:bg-white"
  >
    <div
      v-if="effectiveLoading"
      class="row-span-2 flex items-center justify-center p-24"
    >
      <RisLoadingSpinner />
    </div>

    <div v-else-if="errorToShow" class="p-24">
      <RisErrorCallout :error="errorToShow" />
    </div>

    <template v-else>
      <RisHeader
        v-if="hasHeader"
        :breadcrumbs
        :back-destination="headerBackDestination"
      >
        <main
          class="h-full overflow-auto p-24 has-[[data-pc-name=splitter]]:p-0"
        >
          <slot />
        </main>

        <template #action>
          <slot name="headerAction" />
        </template>
      </RisHeader>

      <main
        v-else
        class="row-span-2 h-full overflow-auto p-24 has-[[data-pc-name=splitter]]:p-0"
      >
        <slot />
      </main>
    </template>
  </div>
</template>
