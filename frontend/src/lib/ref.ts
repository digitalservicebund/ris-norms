import { evaluateXPath, evaluateXPathOnce } from "@/services/xmlService"
import { v4 as uuidv4 } from "uuid"

/**
 * Calculates the next possible eId for an akn:ref element
 * @param parentNode
 */
export function getNextRefEId(parentNode: Node) {
  if (parentNode.nodeType === Node.TEXT_NODE && parentNode.parentElement) {
    return getNextRefEId(parentNode.parentElement)
  }

  const parentEId = evaluateXPathOnce("./@eId", parentNode)?.nodeValue

  if (!parentEId) {
    return "ref-1"
  }

  const nestedEIds = evaluateXPath(".//@eId", parentNode).map(
    (node) => node.nodeValue,
  )

  let eIdCandidate = ""
  let index = 1
  do {
    eIdCandidate = `${parentEId}_ref-${index}`
    index++
  } while (nestedEIds.includes(eIdCandidate))

  return eIdCandidate
}

/**
 * Creates a new akn:ref elment that should then be used as a child of the given parent element. The node is not inserted into the document.
 * @param parentElement the parent element the new akn:ref element should be nested under (used for determining the eId)
 */
export function createNewRefElement(parentElement: Node) {
  if (!parentElement.ownerDocument) {
    return
  }

  const aknRef = parentElement.ownerDocument.createElementNS(
    "http://Inhaltsdaten.LegalDocML.de/1.6/",
    "ref",
  )
  aknRef.setAttribute("eId", getNextRefEId(parentElement))
  aknRef.setAttribute("GUID", uuidv4())

  return aknRef
}
