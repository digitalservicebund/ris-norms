import { getNodeByEid } from "@/services/ldmldeService"

/**
 * Is the node an Element?
 */
function isElement(node: Node): node is Element {
  return node.nodeType === Node.ELEMENT_NODE
}

/**
 * Is the node a ChildNode?
 */
function isChildNode(node: Node): node is ChildNode {
  return node.parentNode !== null
}

/**
 * Finds the node in the LDML.de document that corresponds to the given node in the html-render of the LDML.de document
 *
 * @param node the node of the html render whose equivalent should be found
 * @param ldmlDocument the LDML.de document
 */
export function findHtmlNodeInLdml(
  node: Node,
  ldmlDocument: Document,
): Node | null {
  if (isElement(node)) {
    const eId = node.getAttribute("data-eid")
    if (!eId) {
      return null
    }

    return getNodeByEid(ldmlDocument, eId) ?? null
  }

  if (!node.parentElement || !isChildNode(node)) {
    return null
  }

  const eId = node.parentElement.getAttribute("data-eid")
  if (!eId) {
    return null
  }

  const childIndex = Array.from(node.parentElement.childNodes).indexOf(node)

  return getNodeByEid(ldmlDocument, eId)?.childNodes.item(childIndex) ?? null
}

/**
 * Finds the offset that is the corresponds to the same text range in the LDML.de node.
 *
 * This corrects for the spacing differences (additional spaces) between the html render and the ldml.de-xml. This is
 * done by counting multiple directly following whitespace characters as one character.
 *
 * @param htmlNode the node within the html render of the ldml node
 * @param htmlOffset the offset to find in the ldml node
 * @param ldmlNode the ldml node in which the offset should be found
 */
export function findHtmlOffsetInLdmlNode(
  htmlNode: Node,
  htmlOffset: number,
  ldmlNode: Node,
) {
  const textUpToOffset = htmlNode.textContent?.substring(0, htmlOffset)
  const ldmlText = ldmlNode.textContent
  if (textUpToOffset === undefined || ldmlText === null) {
    return
  }

  const textWithNormalizedWhitespace = textUpToOffset.replaceAll(/\s+/g, " ")
  const offsetWithNormalizedWhitespace = textWithNormalizedWhitespace.length

  let ldmlOffset = 0
  let ldmlOffsetWithNormalizedWhitespace = 0
  while (ldmlOffsetWithNormalizedWhitespace < offsetWithNormalizedWhitespace) {
    if (!/\s\s/.exec(ldmlText.substring(ldmlOffset, ldmlOffset + 2))) {
      ldmlOffsetWithNormalizedWhitespace++
    }
    ldmlOffset++
  }

  return ldmlOffset
}

/**
 * Converts a Range within a html-render of a LMDL.de xml to a range within that xml document.
 *
 * This is done by finding the nodes (of the xml) that correspond to the nodes of the html-range
 * {@see findHtmlNodeInLdml} and then converting the character offsets within these nodes
 * {@see findHtmlOffsetInLdmlNode}.
 *
 * @param htmlRange The Range within the html
 * @param ldmlDocument The LDML.de Document in which this range should be found
 */
export function htmlRenderRangeToLdmlDeRange(
  htmlRange: Range,
  ldmlDocument: Document,
) {
  const containerLdmlNode = findHtmlNodeInLdml(
    htmlRange.commonAncestorContainer,
    ldmlDocument,
  )
  const startLdmlNode = findHtmlNodeInLdml(
    htmlRange.startContainer,
    ldmlDocument,
  )
  const endLdmlNode = findHtmlNodeInLdml(htmlRange.endContainer, ldmlDocument)

  if (!containerLdmlNode || !startLdmlNode || !endLdmlNode) {
    return
  }

  const startLdmlOffset = findHtmlOffsetInLdmlNode(
    htmlRange.startContainer,
    htmlRange.startOffset,
    startLdmlNode,
  )
  const endLdmlOffset = findHtmlOffsetInLdmlNode(
    htmlRange.endContainer,
    htmlRange.endOffset,
    endLdmlNode,
  )

  if (startLdmlOffset === undefined || endLdmlOffset === undefined) {
    return
  }

  const ldmlRange = new Range()

  ldmlRange.selectNode(containerLdmlNode)
  ldmlRange.setStart(startLdmlNode, startLdmlOffset)
  ldmlRange.setEnd(endLdmlNode, endLdmlOffset)

  return ldmlRange
}
