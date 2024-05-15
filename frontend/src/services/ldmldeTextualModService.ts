import { evaluateXPath } from "@/services/xmlService"

/**
 * Provides the eid of the temporalGroup of the akn:textualMod element.
 */
export function getForcePeriod(aknTextualModNode: Node) {
  return evaluateXPath(
    `akn:force/@period`,
    aknTextualModNode,
  )?.nodeValue?.replace("#", "")
}
