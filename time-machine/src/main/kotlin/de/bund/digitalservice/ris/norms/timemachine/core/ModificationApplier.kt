package de.bund.digitalservice.ris.norms.timemachine.core

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants.NODE
import javax.xml.xpath.XPathFactory
import org.w3c.dom.Document
import org.w3c.dom.Node

fun applyModification(
    amendingLaw: Document,
    targetLaw: Document,
    oldText: String,
    newText: String
): Document {
  val amendedLaw = targetLaw.clone()

  val modification = getFirstModification(amendingLaw)
  requireNotNull(modification) { "Amending law does not include any modification" }

  val eId = getReferenceEid(modification)
  requireNotNull(eId) { "Modification has no target reference" }

  val elementToModify = findElementToModify(eId, amendedLaw)
  requireNotNull(elementToModify) { "Could not find element to modify with eId: '$eId'" }

  val modifiedText = elementToModify.textContent.replaceFirst(oldText, newText)
  elementToModify.textContent = modifiedText

  return amendedLaw
}

fun getFirstModification(amendingLaw: Document) = getNode("//*[local-name()='mod']", amendingLaw)

fun getReferenceEid(modification: Node) =
    getNode("//*[local-name()='ref']/@href", modification)?.nodeValue

fun findElementToModify(eId: String, amendedLaw: Document) = getNode("//*[@eId='$eId']", amendedLaw)

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
