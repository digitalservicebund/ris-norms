import { evaluateXPath } from "@/services/xmlService"
import {
  getActiveModificationByModEid,
  getNodeByEid,
} from "@/services/ldmldeService"
import { getForcePeriod } from "@/services/ldmldeTextualModService"
import { getTemporalGroupDate } from "@/services/ldmldeTemporalGroupService"

/**
 * Find the eIds of all akn:mod elements.
 */
export function getModEIds(node: Node): string[] {
  return evaluateXPath(`//akn:mod/@eId`, node)
    .map((eIdNode) => eIdNode.nodeValue)
    .filter((value): value is string => value !== null)
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
