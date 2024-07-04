import { evaluateXPathOnce } from "@/services/xmlService"

/**
 * Get the eid of the akn:eventRef of the beginning of the given temporal group.
 */
export function getStartEventRefEid(temporalGroupNode: Node) {
  return evaluateXPathOnce(
    `akn:timeInterval[@refersTo="geltungszeit"]/@start`,
    temporalGroupNode,
  )?.nodeValue?.replace("#", "")
}
