package de.bund.digitalservice.ris.norms.timemachine

import java.io.File
import java.io.FileWriter
import java.io.StringReader
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import org.w3c.dom.Document
import org.xml.sax.InputSource

fun documentToString(document: Document): String {
  val domSource = DOMSource(document)
  val writer = StringWriter()
  val result = StreamResult(writer)
  val transformerFactory = TransformerFactory.newInstance()
  val transformer = transformerFactory.newTransformer()
  transformer.transform(domSource, result)
  return writer.toString()
}

fun writeDocumentToFile(document: Document, fileName: String) {
  val transformer = TransformerFactory.newInstance().newTransformer()
  transformer.setOutputProperty("indent", "yes")
  transformer.setOutputProperty("encoding", "UTF-8")
  val source = DOMSource(document)
  val result = StreamResult(FileWriter(File(fileName)))
  transformer.transform(source, result)
}

fun readDocumentFromFile(amendingLaw: File): Document {
  val dbFactory = DocumentBuilderFactory.newInstance()
  val dBuilder = dbFactory.newDocumentBuilder()

  val xmlInput = InputSource(StringReader(amendingLaw.readText()))
  val amendingLawDoc: Document = dBuilder.parse(xmlInput)
  return amendingLawDoc
}
