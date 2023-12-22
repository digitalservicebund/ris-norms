import de.bund.digitalservice.ris.norms.timemachine.core.getNode
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test

class DocumentEncodingTest {

  @Test
  fun `documentToString() should return an XML document as a string`() {
    // Create a sample document with a root element
    val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument()
    val root = document.createElement("root")
    document.appendChild(root)
    val result = documentToString(document)

    assertThat(result).contains("<?xml")
  }

  @Test
  fun `writeDocumentToFile() should write an XML document to a file`() {
    // Create a sample document with a root element
    val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument()
    val root = document.createElement("root")
    document.appendChild(root)

    // Write the document to a file
    val fileName = "test.xml"
    writeDocumentToFile(document, fileName)

    // Check that the file exists and has the expected content
    val file = File(fileName)
    assertThat(file.exists()).isTrue()
    assertThat(file.readText())
        .contains(
            "<root/>",
        )
  }

  @Test
  fun `fileToDocument() result contains modification node with specific eID`() {
    //    val filePath =
    // this.javaClass.classLoader.getResource("07_01_änderungsgesetz.xml")?.toURI()
    val filePath = object {}.javaClass.classLoader.getResource("07_01_änderungsgesetz.xml")?.toURI()
    val amendingLawFile = File(filePath!!)
    val amendingLaw = readDocumentFromFile(amendingLawFile)
    val modNode = getNode("//*[local-name()='mod']", amendingLaw)

    assertThat(modNode?.attributes?.getNamedItem("eId")?.nodeValue)
        .isEqualTo("art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1")
  }
}
