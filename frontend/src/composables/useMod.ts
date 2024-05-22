import { computed, MaybeRefOrGetter, ref, Ref, toValue, watch } from "vue"
import { xmlStringToDocument } from "@/services/xmlService"
import { getNodeByEid } from "@/services/ldmldeService"
import {
  getDestinationHref,
  getQuotedTextFirst,
  getQuotedTextSecond,
  getTextualModType,
  getTimeBoundaryDate,
  updateModData,
} from "@/services/ldmldeModService"
import { ModData, ModType } from "@/types/ModType"

/**
 * Get data about an akn:mod element. The data can be overwritten and updates whenever either the eid or xml change.
 *
 * @param eid a reference to the eid of the akn:mod.
 * @param xml a reference to the xml of the norm containing the akn:mod.
 * @returns References to the data about the akn:mod element.
 */
export function useMod(
  eid: MaybeRefOrGetter<string | null>,
  xml: MaybeRefOrGetter<string | undefined>,
): {
  textualModType: Ref<ModType | "">
  destinationHref: Ref<string>
  quotedTextFirst: Ref<string>
  quotedTextSecond: Ref<string>
  timeBoundary: Ref<string | undefined>
  updateMod: (
    eli: MaybeRefOrGetter<string>,
    eid: MaybeRefOrGetter<string>,
    updatedMods: ModData,
  ) => Promise<string>
} {
  const normDocument = computed(() => {
    const xmlValue = toValue(xml)
    return xmlValue ? xmlStringToDocument(xmlValue) : null
  })

  const textualModType = ref<ModType | "">("")
  const destinationHref = ref<string>("")
  const quotedTextFirst = ref<string>("")
  const quotedTextSecond = ref<string>("")
  const timeBoundary = ref<string | undefined>()

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

  /**
   * Update the mod data to the server.
   *
   * @param eli - The ELI of the norm.
   * @param eid - The eId of the akn:mod.
   * @param updatedMods - The updated mod data.
   * @returns A promise that resolves when the save operation is complete.
   */
  async function updateMod(
    eli: MaybeRefOrGetter<string>,
    eid: MaybeRefOrGetter<string>,
    updatedMods: ModData,
  ) {
    return await updateModData(toValue(eli), toValue(eid), updatedMods)
  }

  return {
    textualModType,
    destinationHref,
    quotedTextFirst,
    quotedTextSecond,
    timeBoundary,
    updateMod,
  }
}
