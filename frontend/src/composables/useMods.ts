import { computed, MaybeRefOrGetter, Ref, toValue } from "vue"
import { getTimeBoundaryDate } from "@/services/ldmldeModService"
import { xmlStringToDocument } from "@/services/xmlService"
import { TemporalDataResponse } from "@/types/temporalDataResponse"

/**
 * Get data about multiple akn:mod elements. The data can be overwritten and updates whenever either the eid or xml change.
 *
 * @param eli a reference to the eli of the norm containing the akn:mod.
 * @param eids a reference to the elis of akn:mod elements.
 * @param xml a reference to the xml of the norm containing the akn:mod.
 * @returns References to the data about the akn:mod element as well as UseFetchReturn's for previewing or updating it.
 */
export function useMods(
  eids: MaybeRefOrGetter<string[]>,
  xml: MaybeRefOrGetter<string | null>,
): Ref<
  {
    eid: string
    timeBoundary: TemporalDataResponse | undefined
  }[]
> {
  const normDocument = computed(() => {
    const xmlValue = toValue(xml)
    return xmlValue ? xmlStringToDocument(xmlValue) : null
  })

  const mods = computed(() => {
    const doc = normDocument.value
    if (!doc) return []

    return toValue(eids).map((eid) => {
      return {
        eid,
        timeBoundary: getTimeBoundaryDate(doc, eid) ?? undefined,
      }
    })
  })

  return mods
}
