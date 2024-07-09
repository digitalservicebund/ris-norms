import { evaluateXPathOnce } from "@/services/xmlService"

/**
 * Get the node with the given eId.
 */
export function getNodeByEid(xml: Document, eid: string) {
  return evaluateXPathOnce(`//*[@eId="${eid}"]`, xml)
}

/**
 * Get the node of the active modification associated with the akn:mod element of the given eId.
 */
export function getActiveModificationByModEid(xml: Document, modEid: string) {
  return evaluateXPathOnce(
    `//akn:activeModifications/akn:textualMod[akn:source[@href="#${modEid}"]]`,
    xml,
  )
}
