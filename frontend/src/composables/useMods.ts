import { computed, MaybeRefOrGetter, ref, Ref, toValue, watch } from "vue"
import {
  getTextualModType,
  getTimeBoundaryDate,
  useUpdateMods,
} from "@/services/ldmldeModService"
import { xmlStringToDocument } from "@/services/xmlService"
import { TemporalDataResponse } from "@/types/temporalDataResponse"
import { ModData, ModType } from "@/types/ModType"
import { UseFetchReturn } from "@vueuse/core"
import { getNodeByEid } from "@/services/ldmldeService"

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
      textualModType: ModType | ""
    }[]
  >
  preview: UseFetchReturn<{
    amendingNormXml: string
    targetNormZf0Xml: string
  }>
  update: UseFetchReturn<{
    amendingNormXml: string
    targetNormZf0Xml: string
  }>
} {
  const normDocument = computed(() => {
    const xmlValue = toValue(xml)
    return xmlValue ? xmlStringToDocument(xmlValue) : null
  })

  const mods: Ref<
    {
      eid: string
      timeBoundary: { date: string; temporalGroupEid: string } | undefined
      textualModType: ModType | ""
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
        const modNode = getNodeByEid(doc, eid)

        return {
          eid,
          timeBoundary: getTimeBoundaryDate(doc, eid) ?? undefined,
          textualModType: modNode ? (getTextualModType(modNode) ?? "") : "",
        }
      })
    },
    { immediate: true },
  )

  const modsData = computed(() => {
    const entries: [string, Partial<ModData>][] = mods.value.map((mod) => [
      mod.eid,
      {
        textualModType: mod.textualModType,
        timeBoundaryEid: mod.timeBoundary?.temporalGroupEid,
      },
    ])
    return Object.fromEntries(entries)
  })

  const preview = useUpdateMods(eli, modsData, true)
  const update = useUpdateMods(eli, modsData, false)

  return {
    data: mods,
    preview,
    update,
  }
}
