import { evaluateXPath, evaluateXPathOnce } from "@/lib/xml"

/**
 * Get the node with the given eId.
 *
 * @param xml Document from which the information should be returned
 * @param eid Identifier of the node to find
 * @returns Node if found
 */
export function getNodeByEid(xml: Document, eid: string) {
  return evaluateXPathOnce(`//*[@eId="${eid}"]`, xml)
}

/**
 * Returns a list of eIds of all elements of the given type inside the given
 * node.
 *
 * @param node Node to search elements in
 * @param type Type name of the elements to look up
 * @returns List of eIds of all results
 */
export function getEidsOfElementType(node: Node, type: string): string[] {
  return evaluateXPath(`//akn:${type}/@eId`, node)
    .map((eIdNode) => eIdNode.nodeValue)
    .filter((value): value is string => value !== null)
}
