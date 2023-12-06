package de.bund.digitalservice.ris.norms.timemachine.core

import javax.xml.xpath.XPathConstants.NODE
import javax.xml.xpath.XPathFactory
import org.w3c.dom.Document
import org.w3c.dom.Node

// TODO: Is document parameter passed-as-value or -reference?
fun applyModification(targetLaw: Document, eId: String, newText: String): Document {
fun applyModification(
    targetLaw: Document,
    eId: String,
    oldText: String,
    newText: String
): Document {
  val expression = "//*[@eId='$eId']"
  val node = xpath.evaluate(expression, targetLaw, NODE) as Node?

  // TODO: handle negative case
  node?.let { it.textContent = it.textContent.replaceFirst(oldText, newText) }

  return targetLaw
}

val xpath by lazy { XPathFactory.newInstance().newXPath() }
