package de.bund.digitalservice.ris.norms.timemachine.helper

import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class XmlFileWriterTest {

  val testOutputPath = "./src/test/output"

  private val documentWriter = XmlFileWriter()

  @Test
  fun testWriteDocumentToFile() {
    // Create a sample document with a root element
    val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument()
    val root = document.createElement("root")
    document.appendChild(root)

    // Write the document to a file
    val fileName = "test.xml"
    documentWriter.writeDocumentToFile(document, "${testOutputPath}/${fileName}")

    // Check that the file exists and has the expected content
    val file = File(fileName)
    assertThat(file.exists()).isTrue()
    assertThat(file.readText())
        .contains(
            "<root/>",
        )
  }
}
