import {
  evaluateXPath,
  evaluateXPathOnce,
  xmlNodeToString,
} from "@/services/xmlService"
import { ModType, ModData } from "@/types/ModType"
import {
  getActiveModificationByModEid,
  getNodeByEid,
} from "@/services/ldmldeService"
import { getForcePeriod } from "@/services/ldmldeTextualModService"
import { getTemporalGroupDate } from "@/services/ldmldeTemporalGroupService"
import { INVALID_URL, useApiFetch } from "@/services/apiService"
import { computed, MaybeRefOrGetter, ref, toValue, watch } from "vue"
import { UseFetchReturn } from "@vueuse/core"

/**
 * Find the eIds of all akn:mod elements.
 */
export function getModEIds(node: Node): string[] {
  return evaluateXPath(`//akn:mod/@eId`, node)
    .map((eIdNode) => eIdNode.nodeValue)
    .filter((value): value is string => value !== null)
}

/**
 * Provides the old text of an akn:mod element. For "aenderungsbefehl-ersetzen" this is the old text.
 */
export function getQuotedTextFirst(aknModNode: Node) {
  return evaluateXPathOnce(`akn:quotedText[1]`, aknModNode)?.textContent
}

/**
 * Provides the second quoted text of an akn:mod element. For "aenderungsbefehl-ersetzen" this is the new text.
 */
export function getQuotedTextSecond(aknModNode: Node) {
  return evaluateXPathOnce(`akn:quotedText[2]`, aknModNode)?.textContent
}

/**
 * Provides the href of the destination of an akn:mod element.
 */
export function getDestinationHref(aknModNode: Node) {
  return evaluateXPathOnce(`akn:ref/@href`, aknModNode)?.nodeValue
}

/**
 * Provides the upTo of the akn:rref of an akn:mod element.
 */
export function getDestinationRangeUpto(aknModNode: Node) {
  return evaluateXPathOnce(`akn:rref/@upTo`, aknModNode)?.nodeValue
}

/**
 * Provides the from of the akn:rref of an akn:mod element.
 */
export function getDestinationRangeFrom(aknModNode: Node) {
  return evaluateXPathOnce(`akn:rref/@from`, aknModNode)?.nodeValue
}

/**
 * Provides the type of the akn:mod element.
 */
export function getTextualModType(aknModNode: Node) {
  return evaluateXPathOnce(`@refersTo`, aknModNode)?.nodeValue as
    | ModType
    | null
    | undefined
}

/**
 * Get the content of the akn:quotedStructure.
 */
export function getQuotedStructureContent(
  aknModNode: Node,
): string | undefined {
  const quotedStructureNode = evaluateXPathOnce(
    `akn:quotedStructure`,
    aknModNode,
  )
  if (!quotedStructureNode) {
    return undefined
  }

  const innerContent = evaluateXPath(
    `node()[not(self::akn:quote-startQuote) and not(self::akn:quote-endQuote)]`,
    quotedStructureNode,
  )
  return innerContent.map((node) => xmlNodeToString(node)).join("")
}

/**
 * Get the start date of the geltungszeit of the akn:mod.
 */
export function getTimeBoundaryDate(xml: Document, aknModEid: string) {
  const activeModification = getActiveModificationByModEid(xml, aknModEid)
  if (!activeModification) return null

  const temporalGroupEid = getForcePeriod(activeModification)
  if (!temporalGroupEid) return null

  const temporalGroupNode = getNodeByEid(xml, temporalGroupEid)
  if (!temporalGroupNode) return null

  const date = getTemporalGroupDate(temporalGroupNode)
  if (!date) return null

  return { date, temporalGroupEid }
}

/**
 * Composable for saving the updated mod data to the server.
 *
 * @param eli - The ELI of the norm.
 * @param eid - The eId of the akn:mod.
 * @param updatedMods - A mod object.
 * @param dryRun - Should the save operation only be previewed and not actually persisted?
 */
export function useUpdateModData(
  eli: MaybeRefOrGetter<string | null>,
  eid: MaybeRefOrGetter<string | null>,
  updatedMods: MaybeRefOrGetter<ModData | null>,
  dryRun: MaybeRefOrGetter<boolean> = false,
): UseFetchReturn<{
  amendingNormXml: string
  targetNormZf0Xml: string
}> {
  const apiFetch: UseFetchReturn<{
    amendingNormXml: string
    targetNormZf0Xml: string
  }> = useApiFetch(
    computed(() => {
      if (!toValue(eli) || !toValue(eid) || !toValue(updatedMods)) {
        return INVALID_URL
      }

      const params = new URLSearchParams()

      if (toValue(dryRun)) {
        params.set("dryRun", "true")
      }

      return `/norms/${toValue(eli)}/mods/${toValue(eid)}?${params.toString()}`
    }),
    {
      immediate: false,
    },
  )
    .json()
    .put(updatedMods)

  // reset isFinished when data changes
  const isFinished = ref(false)
  watch(apiFetch.isFinished, () => {
    isFinished.value = apiFetch.isFinished.value
  })
  watch(
    [
      () => toValue(eli),
      () => toValue(eid),
      () => toValue(updatedMods),
      () => toValue(dryRun),
    ],
    () => {
      isFinished.value = false
    },
  )

  return {
    ...apiFetch,
    isFinished,
  }
}

/**
 * Composable for saving updated mods to the server.
 *
 * @param eli - The ELI of the norm.
 * @param updatedMods - A object mapping eids to the updated mod objects.
 * @param dryRun - Should the save operation only be previewed and not actually persisted?
 */
export function useUpdateMods(
  eli: MaybeRefOrGetter<string | null>,
  updatedMods: MaybeRefOrGetter<{
    [eid: string]: Pick<ModData, "timeBoundaryEid">
  }>,
  dryRun: MaybeRefOrGetter<boolean> = false,
): UseFetchReturn<{
  amendingNormXml: string
  targetNormZf0Xml: string
}> {
  const apiFetch: UseFetchReturn<{
    amendingNormXml: string
    targetNormZf0Xml: string
  }> = useApiFetch(
    computed(() => {
      if (!toValue(eli) || !toValue(updatedMods)) {
        return INVALID_URL
      }

      const params = new URLSearchParams()

      if (toValue(dryRun)) {
        params.set("dryRun", "true")
      }

      return `/norms/${toValue(eli)}/mods?${params.toString()}`
    }),
    {
      immediate: false,
    },
  )
    .json()
    .patch(updatedMods)

  // reset isFinished when data changes
  const isFinished = ref(false)
  watch(apiFetch.isFinished, () => {
    isFinished.value = apiFetch.isFinished.value
  })
  watch(
    [() => toValue(eli), () => toValue(updatedMods), () => toValue(dryRun)],
    () => {
      isFinished.value = false
    },
  )

  return {
    ...apiFetch,
    isFinished,
  }
}
