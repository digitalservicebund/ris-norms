import { computed, MaybeRefOrGetter, ref, Ref, toValue, watch } from "vue"
import { xmlStringToDocument } from "@/services/xmlService"
import { getNodeByEid } from "@/services/ldmldeService"
import {
  getDestinationHref,
  getQuotedTextFirst,
  getQuotedTextSecond,
  getTextualModType,
  getTimeBoundaryDate,
  useUpdateModData,
} from "@/services/ldmldeModService"
import { ModType } from "@/types/ModType"
import { UseFetchReturn } from "@vueuse/core"

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
  textualModType: Ref<ModType | "">
  destinationHref: Ref<string>
  quotedTextFirst: Ref<string>
  quotedTextSecond: Ref<string>
  timeBoundary: Ref<{ date: string; temporalGroupEid: string } | undefined>
  update: UseFetchReturn<{
    amendingNormXml: string
    targetNormZf0Xml: string
  }>
  preview: UseFetchReturn<{
    amendingNormXml: string
    targetNormZf0Xml: string
  }>
} {
  const normDocument = computed(() => {
    const xmlValue = toValue(xml)
    return xmlValue ? xmlStringToDocument(xmlValue) : null
  })

  const textualModType = ref<ModType | "">("")
  const destinationHref = ref<string>("")
  const quotedTextFirst = ref<string>("")
  const quotedTextSecond = ref<string>("")
  const timeBoundary = ref<
    { date: string; temporalGroupEid: string } | undefined
  >()

  function reset() {
    textualModType.value = ""
    destinationHref.value = ""
    quotedTextFirst.value = ""
    quotedTextSecond.value = ""
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

      textualModType.value = getTextualModType(modNode) ?? ""
      destinationHref.value = getDestinationHref(modNode) ?? ""
      quotedTextFirst.value = getQuotedTextFirst(modNode) ?? ""
      quotedTextSecond.value = getQuotedTextSecond(modNode) ?? ""
      timeBoundary.value =
        getTimeBoundaryDate(normDocument.value, eidValue) ?? undefined
    },
    { immediate: true },
  )

  const modData = computed(() => {
    const eidValue = toValue(eid)

    if (
      !eidValue ||
      destinationHref.value === "" ||
      quotedTextSecond.value === ""
    )
      return null

    return {
      refersTo: eidValue,
      timeBoundaryEid: timeBoundary.value?.temporalGroupEid,
      destinationHref: destinationHref.value,
      newText: quotedTextSecond.value,
    }
  })
  const preview = useUpdateModData(eli, eid, modData, true)
  const update = useUpdateModData(eli, eid, modData, false)

  return {
    textualModType,
    destinationHref,
    quotedTextFirst,
    quotedTextSecond,
    timeBoundary,
    preview,
    update,
  }
}
