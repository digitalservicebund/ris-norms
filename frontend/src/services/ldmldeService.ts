import { evaluateXPath } from "@/services/xmlService"

export function getNodeByEid(xml: Document, eid: string) {
  return evaluateXPath(`//*[@eId="${eid}"]`, xml)
}
