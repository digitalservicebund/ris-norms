import { evaluateXPathOnce } from "@/services/xmlService"

/**
 * Provides the eid of the temporalGroup of the akn:textualMod element.
 */
export function getForcePeriod(aknTextualModNode: Node) {
  return evaluateXPathOnce(
    `akn:force/@period`,
    aknTextualModNode,
  )?.nodeValue?.replace("#", "")
}
