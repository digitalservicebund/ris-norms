import { evaluateXPathOnce } from "@/services/xmlService"

/**
 * Get the date of an akn:eventRef.
 */
export function getEventRefDate(eventRefNode: Node) {
  return evaluateXPathOnce(`@date`, eventRefNode)?.nodeValue
}
