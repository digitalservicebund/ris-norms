package de.bund.digitalservice.ris.norms.timemachine.core

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants.NODE
import javax.xml.xpath.XPathFactory
import org.w3c.dom.Document
import org.w3c.dom.Node

fun applyModification(amendingLaw: Document, targetLaw: Document): Document {
  val amendedLaw = targetLaw.clone()

  val modification = getFirstModification(amendingLaw)
  requireNotNull(modification) { "Amending law does not include any modification" }

  val eli = findHrefInModification(modification)
  requireNotNull(eli) { "Could not find href in modification" }

  val eId = extractEid(eli)

  val elementToModify = findElementToModify(eId, amendedLaw)
  requireNotNull(elementToModify) { "Could not find element to modify with eId: '$eId'" }

  val oldText = extractOldText(modification)
  requireNotNull(oldText) { "Could not find text that should be replaced" }

  val newText = extractNewText(modification)
  requireNotNull(newText) { "Could not find replacement text" }

  val modifiedText = elementToModify.textContent.replaceFirst(oldText, newText)
  elementToModify.textContent = modifiedText

  return amendedLaw
}

fun getFirstModification(amendingLaw: Document) = getNode("//*[local-name()='mod']", amendingLaw)

fun findHrefInModification(modification: Node): String? =
    getNode("//*[local-name()='ref']/@href", modification)?.nodeValue

fun extractEid(eli: String) = eli.split("/").takeLast(2)[0]

fun findElementToModify(eId: String, amendedLaw: Document) = getNode("//*[@eId='$eId']", amendedLaw)

fun extractOldText(modification: Node): String? =
    getNode("//*[local-name()='quotedText']", modification)?.textContent

fun extractNewText(modification: Node): String? =
    getNode("(//*[local-name()='quotedText'])[2]", modification)?.textContent

fun getNode(expression: String, document: Node) =
    xpath.evaluate(expression, document, NODE) as Node?

private fun Document.clone(): Document {
  val originalRootNode = this.getDocumentElement()
  val clonedDocument = documentBuilder.newDocument()
  val clonedRootNode = clonedDocument.importNode(originalRootNode, true)
  clonedDocument.appendChild(clonedRootNode)
  return clonedDocument
}

private val documentBuilder by lazy { DocumentBuilderFactory.newInstance().newDocumentBuilder() }
private val xpath by lazy { XPathFactory.newInstance().newXPath() }
