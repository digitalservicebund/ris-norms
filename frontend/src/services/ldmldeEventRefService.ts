import { evaluateXPath } from "@/services/xmlService"

/**
 * Get the date of an akn:eventRef.
 */
export function getEventRefDate(eventRefNode: Node) {
  return evaluateXPath(`@date`, eventRefNode)?.nodeValue
}
