import { computed, MaybeRefOrGetter, ref, Ref, toValue, watch } from "vue"
import { getTimeBoundaryDate } from "@/services/ldmldeModService"
import { xmlStringToDocument } from "@/services/xmlService"
import { TemporalDataResponse } from "@/types/temporalDataResponse"
import { UseFetchReturn } from "@vueuse/core"
import { useApiFetch } from "@/services/apiService"

/**
 * Get data about multiple akn:mod elements. The data can be overwritten and updates whenever either the eid or xml change.
 *
 * @param eli a reference to the eli of the norm containing the akn:mod.
 * @param eids a reference to the elis of akn:mod elements.
 * @param xml a reference to the xml of the norm containing the akn:mod.
 * @returns References to the data about the akn:mod element as well as UseFetchReturn's for previewing or updating it.
 */
export function useMods(
  eli: MaybeRefOrGetter<string>,
  eids: MaybeRefOrGetter<string[]>,
  xml: MaybeRefOrGetter<string | null>,
): {
  data: Ref<
    {
      eid: string
      timeBoundary: TemporalDataResponse | undefined
    }[]
  >
  update: UseFetchReturn<unknown>
} {
  const normDocument = computed(() => {
    const xmlValue = toValue(xml)
    return xmlValue ? xmlStringToDocument(xmlValue) : null
  })

  const mods: Ref<
    {
      eid: string
      timeBoundary: { date: string; temporalGroupEid: string } | undefined
    }[]
  > = ref([])

  watch(
    [() => toValue(eids), normDocument],
    () => {
      const doc = normDocument.value
      if (!doc) {
        mods.value = []
        return
      }

      mods.value = toValue(eids).map((eid: string) => {
        return {
          eid,
          timeBoundary: getTimeBoundaryDate(doc, eid) ?? undefined,
        }
      })
    },
    { immediate: true },
  )

  const update = useApiFetch("/non-existing-endpoint", {
    immediate: false,
  })
    .json()
    .put()

  return {
    data: mods,
    update,
  }
}
