import { MaybeRefOrGetter, toValue, computed } from "vue"
import { xmlStringToDocument } from "@/services/xmlService"
import { getNodeByEid } from "@/services/ldmldeService"
import {
  getDestinationHref,
  getQuotedTextFirst,
  getQuotedTextSecond,
  getTextualModType,
  getTimeBoundaryDate,
} from "@/services/ldmldeModService"

/**
 * Get data about an akn:mod element.
 * @param eid a reference to the eid of the akn:mod.
 * @param xml a reference to the xml of the norm containing the akn:mod.
 * @returns References to the different data about the akn:mod element.
 */
export function useMod(
  eid: MaybeRefOrGetter<string | null>,
  xml: MaybeRefOrGetter<string | undefined>,
) {
  const normDocument = computed(() => {
    const xmlValue = toValue(xml)
    return xmlValue ? xmlStringToDocument(xmlValue) : null
  })
  const modNode = computed(() => {
    const eidValue = toValue(eid)
    return eidValue && normDocument.value
      ? getNodeByEid(normDocument.value, eidValue)
      : null
  })

  const textualModType = computed(() =>
    modNode.value ? getTextualModType(modNode.value) ?? "" : "",
  )
  const destinationHref = computed(() =>
    modNode.value ? getDestinationHref(modNode.value) ?? "" : "",
  )
  const quotedTextFirst = computed(() =>
    modNode.value ? getQuotedTextFirst(modNode.value) ?? "" : "",
  )
  const quotedTextSecond = computed(() =>
    modNode.value ? getQuotedTextSecond(modNode.value) ?? "" : "",
  )
  const timeBoundary = computed(() => {
    const eidValue = toValue(eid)
    return eidValue && normDocument.value
      ? getTimeBoundaryDate(normDocument.value, eidValue) ?? "no_choice"
      : "no_choice"
  })

  return {
    textualModType,
    destinationHref,
    quotedTextFirst,
    quotedTextSecond,
    timeBoundary,
  }
}
