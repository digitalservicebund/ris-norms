package de.bund.digitalservice.ris.norms.timemachine.helper

import java.io.File
import java.io.FileWriter
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import org.w3c.dom.Document

class XmlFileWriter {
  fun writeDocumentToFile(document: Document, fileName: String) {
    val transformer = TransformerFactory.newInstance().newTransformer()
    transformer.setOutputProperty("indent", "yes")
    transformer.setOutputProperty("encoding", "UTF-8")
    val source = DOMSource(document)
    val result = StreamResult(FileWriter(File(fileName)))
    transformer.transform(source, result)
  }
}
