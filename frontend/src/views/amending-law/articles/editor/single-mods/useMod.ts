import { computed, MaybeRefOrGetter, ref, Ref, toValue, watch } from "vue"
import { xmlStringToDocument } from "@/services/xmlService"
import { getNodeByEid } from "@/services/ldmldeService"
import { getTimeBoundaryDate } from "@/services/ldmldeModService"
import { UseFetchReturn } from "@vueuse/core"
import { useApiFetch } from "@/services/apiService"

/**
 * Get data about an akn:mod element. The data can be overwritten and updates whenever either the eid or xml change.
 *
 * @param eli a reference to the eli of the norm containing the akn:mod.
 * @param eid a reference to the eid of the akn:mod.
 * @param xml a reference to the xml of the norm containing the akn:mod.
 * @returns References to the data about the akn:mod element as well as UseFetchReturn's for previewing or updating it.
 */
export function useMod(
  eli: MaybeRefOrGetter<string | null>,
  eid: MaybeRefOrGetter<string | null>,
  xml: MaybeRefOrGetter<string | null>,
): {
  timeBoundary: Ref<{ date: string; temporalGroupEid: string } | undefined>
  update: UseFetchReturn<unknown>
} {
  const normDocument = computed(() => {
    const xmlValue = toValue(xml)
    return xmlValue ? xmlStringToDocument(xmlValue) : null
  })

  const timeBoundary = ref<
    { date: string; temporalGroupEid: string } | undefined
  >()

  function reset() {
    timeBoundary.value = undefined
  }

  watch(
    [() => normDocument.value, () => toValue(eid)],
    () => {
      const eidValue = toValue(eid)
      if (!eidValue || !normDocument.value) {
        reset()
        return
      }

      const modNode = getNodeByEid(normDocument.value, eidValue)
      if (!modNode) {
        reset()
        return
      }

      timeBoundary.value =
        getTimeBoundaryDate(normDocument.value, eidValue) ?? undefined
    },
    { immediate: true },
  )

  const update = useApiFetch("/non-existing-endpoint", {
    immediate: false,
  })
    .json()
    .put()

  return {
    timeBoundary,
    update,
  }
}
