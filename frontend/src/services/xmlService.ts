/**
 * Convert the text representation of a xml document to a Document
 */
export function xmlStringToDocument(xmlString: string): Document {
  return new DOMParser().parseFromString(xmlString, "application/xml")
}

/**
 * Convert a xml node to a text representation of it.
 */
export function xmlNodeToString(node: Node): string {
  return new XMLSerializer().serializeToString(node)
}

/**
 * Evaluate a xpath expression on the given node once.
 *
 * When using this method in a unit test this method might need to be overwritten by a mock implementation using the library "xpath".
 * The DOM implementation used by our unit tests (jsdom) does not have great xpath support and might fail at certain expressions.
 * This is done globally using the vitest-setup.ts
 */
export function evaluateXPathOnce(xpath: string, node: Node) {
  const evaluator = new XPathEvaluator()
  return evaluator
    .createExpression(xpath, evaluator.createNSResolver(node))
    .evaluate(node, XPathResult.ANY_TYPE)
    .iterateNext()
}

/**
 * Evaluate a xpath expression on the given node and return all results.
 *
 * When using this method in a unit test this method might need to be overwritten by a mock implementation using the library "xpath".
 * The DOM implementation used by our unit tests (jsdom) does not have great xpath support and might fail at certain expressions.
 * This is done globally using the vitest-setup.ts
 */
export function evaluateXPath(xpath: string, node: Node): Node[] {
  const evaluator = new XPathEvaluator()

  const result = evaluator
    .createExpression(xpath, evaluator.createNSResolver(node))
    .evaluate(node, XPathResult.ANY_TYPE)

  const nodes: Node[] = []

  let nextNode = result.iterateNext()
  while (nextNode !== null) {
    nodes.push(nextNode)
    nextNode = result.iterateNext()
  }

  return nodes
}
