package de.bund.digitalservice.ris.norms.timemachine.core

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory
import org.w3c.dom.Node

fun applyModification(newNodeValue: String, node: String): String {
  val inputStream = ByteArrayInputStream(node.toByteArray())
  val factory = DocumentBuilderFactory.newInstance()
  factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
  val document = factory.newDocumentBuilder().parse(inputStream)
  val xpath = XPathFactory.newInstance().newXPath()

  val node = xpath.evaluate("//*[name()='akn:p']", document, XPathConstants.NODE) as Node

  node.textContent = newNodeValue
  document.xmlStandalone = true

  val outputStream = ByteArrayOutputStream()
  val streamResult = StreamResult(outputStream)
  TransformerFactory.newInstance().newTransformer().transform(DOMSource(document), streamResult)
  val result = outputStream.toString(Charsets.UTF_8)

  return result
}
