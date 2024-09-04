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
 * Finds the node in the html-render of a LDML.de document that corresponds to the given node in the LDML.de document
 *
 * @param node the node of the LDML.de document whose equivalent should be found
 * @param htmlElement the HTML element containing the render
 */
export function findLdmlNodeInHtml(
  node: Node,
  htmlElement: Element,
): Node | null {
  if (isElement(node)) {
    const eId = node.getAttribute("eId")
    if (!eId) {
      return null
    }

    return htmlElement.querySelector(`[data-eId="${eId}"]`) ?? null
  }

  if (!node.parentElement || !isChildNode(node)) {
    return null
  }

  const eId = node.parentElement.getAttribute("eId")
  if (!eId) {
    return null
  }

  const childIndex = Array.from(node.parentElement.childNodes).indexOf(node)

  return (
    htmlElement
      .querySelector(`[data-eId="${eId}"]`)
      ?.childNodes.item(childIndex) ?? null
  )
}

/**
 * Finds the offset that is the corresponds to the same text range in the LDML.de or HTML node.
 *
 * This corrects for the spacing differences (additional spaces) between the html render and the ldml.de-xml. This is
 * done by counting multiple directly following whitespace characters as one character.
 *
 * @param originalNode the node with the current offset
 * @param offset the offset to find in the new node
 * @param newNode the node in which the offset should be found
 */
export function findOffsetInOtherNode(
  originalNode: Node,
  offset: number,
  newNode: Node,
) {
  const textUpToOffset = originalNode.textContent?.substring(0, offset)
  const ldmlText = newNode.textContent
  if (textUpToOffset === undefined || ldmlText === null) {
    return
  }

  const textWithNormalizedWhitespace = textUpToOffset.replaceAll(/\s+/g, " ")
  const offsetWithNormalizedWhitespace = textWithNormalizedWhitespace.length

  let newOffset = 0
  let newOffsetWithNormalizedWhitespace = 0
  while (newOffsetWithNormalizedWhitespace < offsetWithNormalizedWhitespace) {
    if (!/\s\s/.exec(ldmlText.substring(newOffset, newOffset + 2))) {
      newOffsetWithNormalizedWhitespace++
    }
    newOffset++
  }

  return newOffset
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

  const startLdmlOffset = findOffsetInOtherNode(
    htmlRange.startContainer,
    htmlRange.startOffset,
    startLdmlNode,
  )
  const endLdmlOffset = findOffsetInOtherNode(
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

/**
 * Converts a Range within a LMDL.de xml to a range within a html-render of that xml.
 *
 * @param ldmlRange The Range within the xml
 * @param htmlElement The HTML Element that contains the render
 */
export function ldmlRangeToHtmlRenderRange(
  ldmlRange: Range,
  htmlElement: Element,
) {
  const containerLdmlNode = findLdmlNodeInHtml(
    ldmlRange.commonAncestorContainer,
    htmlElement,
  )
  const startLdmlNode = findLdmlNodeInHtml(
    ldmlRange.startContainer,
    htmlElement,
  )
  const endLdmlNode = findLdmlNodeInHtml(ldmlRange.endContainer, htmlElement)

  if (!containerLdmlNode || !startLdmlNode || !endLdmlNode) {
    return
  }

  const startLdmlOffset = findOffsetInOtherNode(
    ldmlRange.startContainer,
    ldmlRange.startOffset,
    startLdmlNode,
  )
  const endLdmlOffset = findOffsetInOtherNode(
    ldmlRange.endContainer,
    ldmlRange.endOffset,
    endLdmlNode,
  )

  if (startLdmlOffset === undefined || endLdmlOffset === undefined) {
    return
  }

  const htmlRange = new Range()

  htmlRange.selectNode(containerLdmlNode)
  htmlRange.setStart(startLdmlNode, startLdmlOffset)
  htmlRange.setEnd(endLdmlNode, endLdmlOffset)

  return htmlRange
}
