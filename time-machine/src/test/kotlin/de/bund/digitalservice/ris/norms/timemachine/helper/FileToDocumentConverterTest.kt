package de.bund.digitalservice.ris.norms.timemachine.helper

import de.bund.digitalservice.ris.norms.timemachine.core.getNode
import java.io.File
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FileToDocumentConverterTest {

  private val filePath = this.javaClass.classLoader.getResource("07_01_aenderungsgesetz.xml")?.path
  private val amendingLawFile = File(filePath!!)

  @Test
  fun `Document contains modification node with specific eID`() {
    val amendingLaw = FileToDocumentConverter().convert(amendingLawFile)
    val modNode = getNode("//*[local-name()='mod']", amendingLaw)

    assertThat(modNode?.attributes?.getNamedItem("eId")?.nodeValue)
        .isEqualTo("art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1")
  }
}
