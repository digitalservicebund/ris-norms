package de.bund.digitalservice.ris.norms.timemachine.core

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants.NODE
import javax.xml.xpath.XPathFactory
import org.w3c.dom.Document
import org.w3c.dom.Node

fun applyModification(
    targetLaw: Document,
    eId: String,
    oldText: String,
    newText: String
): Document {
  val amendedLaw = targetLaw.clone()
  val expression = "//*[@eId='$eId']"
  val node = xpath.evaluate(expression, amendedLaw, NODE) as Node?

  // TODO: handle negative case
  node?.let { it.textContent = it.textContent.replaceFirst(oldText, newText) }

  return amendedLaw
}

private fun Document.clone(): Document {
  val originalRootNode = this.getDocumentElement()
  val clonedDocument = documentBuilder.newDocument()
  val clonedRootNode = clonedDocument.importNode(originalRootNode, true)
  clonedDocument.appendChild(clonedRootNode)
  return clonedDocument
}

private val documentBuilder by lazy { DocumentBuilderFactory.newInstance().newDocumentBuilder() }
private val xpath by lazy { XPathFactory.newInstance().newXPath() }
