import { evaluateXPath, evaluateXPathOnce } from "@/services/xmlService"
import { getNodeByEid } from "@/services/ldmldeService"
import { getEventRefDate } from "@/services/ldmldeEventRefService"

/**
 * Get the eid of the akn:eventRef of the beginning of the given temporal group.
 */
export function getStartEventRefEid(temporalGroupNode: Node) {
  return evaluateXPathOnce(
    `akn:timeInterval[@refersTo="geltungszeit"]/@start`,
    temporalGroupNode,
  )?.nodeValue?.replace("#", "")
}

/**
 * Get all temporal groups of a document
 */
export function getTemporalGroupNodes(xml: Document) {
  return evaluateXPath("//akn:temporalGroup", xml)
}

/**
 * Get the start date of a temporal group
 */
export function getTemporalGroupDate(temporalGroupNode: Node) {
  if (!temporalGroupNode.ownerDocument) {
    return null
  }

  const eventRefEid = getStartEventRefEid(temporalGroupNode)
  if (!eventRefEid) return null

  const eventRefNode = getNodeByEid(
    temporalGroupNode.ownerDocument,
    eventRefEid,
  )
  if (!eventRefNode) return null

  return getEventRefDate(eventRefNode)
}

/**
 * Get the eId of a temporal group
 */
export function getTemporalGroupEId(temporalGroupNode: Node) {
  return evaluateXPathOnce("./@eId", temporalGroupNode)?.nodeValue
}
