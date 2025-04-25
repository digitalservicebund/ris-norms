import { evaluateXPathOnce } from "@/lib/xml"

/**
 * Get the node with the given eId.
 */
export function getNodeByEid(xml: Document, eid: string) {
  return evaluateXPathOnce(`//*[@eId="${eid}"]`, xml)
}
