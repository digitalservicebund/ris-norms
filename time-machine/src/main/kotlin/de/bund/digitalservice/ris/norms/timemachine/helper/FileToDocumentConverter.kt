package de.bund.digitalservice.ris.norms.timemachine.helper

import java.io.File
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Document
import org.xml.sax.InputSource

class FileToDocumentConverter {
  fun convert(amendingLaw: File): Document {
    val dbFactory = DocumentBuilderFactory.newInstance()
    val dBuilder = dbFactory.newDocumentBuilder()

    val xmlInput = InputSource(StringReader(amendingLaw.readText()))
    val amendingLawDoc: Document = dBuilder.parse(xmlInput)
    return amendingLawDoc
  }
}
