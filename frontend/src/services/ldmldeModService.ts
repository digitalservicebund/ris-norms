import { evaluateXPath } from "@/services/xmlService"
import { ModType } from "@/types/ModType"

/**
 * Provides the old text of an akn:mod element. For "aenderungsbefehl-ersetzen" this is the old text.
 */
export function getQuotedTextFirst(aknModNode: Node) {
  return evaluateXPath(`akn:quotedText[1]`, aknModNode)?.textContent
}

/**
 * Provides the second quoted text of an akn:mod element. For "aenderungsbefehl-ersetzen" this is the new text.
 */
export function getQuotedTextSecond(aknModNode: Node) {
  return evaluateXPath(`akn:quotedText[2]`, aknModNode)?.textContent
}

/**
 * Provides the href of the destination of an akn:mod element.
 */
export function getDestinationHref(aknModNode: Node) {
  return evaluateXPath(`akn:ref/@href`, aknModNode)?.nodeValue
}

/**
 * Provides the type of the akn:mod element.
 */
export function getTextualModType(aknModNode: Node) {
  return evaluateXPath(`@refersTo`, aknModNode)?.nodeValue as
    | ModType
    | null
    | undefined
}
