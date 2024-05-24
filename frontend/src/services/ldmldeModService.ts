import { evaluateXPath } from "@/services/xmlService"
import { ModType, ModData } from "@/types/ModType"
import {
  getActiveModificationByModEid,
  getNodeByEid,
} from "@/services/ldmldeService"
import { getForcePeriod } from "@/services/ldmldeTextualModService"
import { getStartEventRefEid } from "@/services/ldmldeTemporalGroupService"
import { getEventRefDate } from "@/services/ldmldeEventRefService"
import { apiFetch } from "@/services/apiService"

/**
 * Provides the old text of an akn:mod element. For "aenderungsbefehl-ersetzen" this is the old text.
 */
export function getQuotedTextFirst(aknModNode: Node) {
  return evaluateXPath(`akn:quotedText[1]`, aknModNode)?.textContent
}

/**
 * Provides the second quoted text of an akn:mod element. For "aenderungsbefehl-ersetzen" this is the new text.
 */
export function getQuotedTextSecond(aknModNode: Node) {
  return evaluateXPath(`akn:quotedText[2]`, aknModNode)?.textContent
}

/**
 * Provides the href of the destination of an akn:mod element.
 */
export function getDestinationHref(aknModNode: Node) {
  return evaluateXPath(`akn:ref/@href`, aknModNode)?.nodeValue
}

/**
 * Provides the type of the akn:mod element.
 */
export function getTextualModType(aknModNode: Node) {
  return evaluateXPath(`@refersTo`, aknModNode)?.nodeValue as
    | ModType
    | null
    | undefined
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

  const eventRefEid = getStartEventRefEid(temporalGroupNode)
  if (!eventRefEid) return null

  const eventRefNode = getNodeByEid(xml, eventRefEid)
  if (!eventRefNode) return null

  const date = getEventRefDate(eventRefNode)
  if (!date) return null

  return { date, temporalGroupEid }
}
/**
 * Save the updated mod data to the server.
 *
 * @param eli - The ELI of the norm.
 * @param eid - The eId of the akn:mod.
 * @param updatedMods - A mod object.
 * @returns An XML of ZF0 in the response when the save operation is complete.
 */
export async function updateModData(
  eli: string,
  eid: string,
  updatedMods: ModData,
): Promise<string> {
  return await apiFetch<string>(`/norms/${eli}/mods/${eid}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Accept: "application/xml",
    },
    body: JSON.stringify(updatedMods),
  })
}
