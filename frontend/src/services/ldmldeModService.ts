import { evaluateXPath } from "@/services/xmlService"

/**
 * Find the eIds of all akn:mod elements.
 */
export function getModEIds(node: Node): string[] {
  return evaluateXPath(`//akn:mod/@eId`, node)
    .map((eIdNode) => eIdNode.nodeValue)
    .filter((value): value is string => value !== null)
}
