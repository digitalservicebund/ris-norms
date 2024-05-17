/**
 * Convert the text representation of an xml document to a Document
 */
export function xmlStringToDocument(xmlString: string): Document {
  return new DOMParser().parseFromString(xmlString, "application/xml")
}

/**
 * Convert a xml document to a text representation of it.
 */
export function xmlDocumentToString(xmlDoc: Document): string {
  return new XMLSerializer().serializeToString(xmlDoc)
}

/**
 * Evaluate a xpath expression on the given node.
 *
 * When using this method in a unit test this method might need to be overwritten by a mock implementation using the library "xpath".
 * The DOM implementation used by our unit tests (jsdom) does not have great xpath support and might fail at certain expressions.
 * This is done globally using the vitest-setup.ts
 */
export function evaluateXPath(xpath: string, node: Node) {
  const evaluator = new XPathEvaluator()
  return evaluator
    .createExpression(xpath, evaluator.createNSResolver(node))
    .evaluate(node, XPathResult.ANY_TYPE)
    .iterateNext()
}