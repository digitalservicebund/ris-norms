package de.bund.digitalservice.ris.norms.timemachine

import com.github.ajalt.clikt.testing.test
import java.io.File
import java.nio.file.Paths
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TimeMachineTest {
  private val filePathAmendingLaw =
      this.javaClass.classLoader.getResource("07_01_aenderungsgesetz.xml")?.path
  private val filePathToBeAmendedLaw =
      this.javaClass.classLoader
          .getResource("07_01_geaendertesGesetz_V1.1_Metadatenaenderung.xml")
          ?.path

  @Test
  fun `test command line time machine`() {
    val command = TimeMachine()
    command.test("$filePathAmendingLaw $filePathToBeAmendedLaw")
    val workingDir = Paths.get("").toAbsolutePath().toString()
    val fileNameWithoutType =
        filePathToBeAmendedLaw?.substringBeforeLast(".")?.substringAfterLast("/")
    val resultFromFile = File(workingDir + "/" + fileNameWithoutType + "_amended.xml")

    assertThat(resultFromFile).exists()
    assertThat(resultFromFile).isFile()

    val changedLine = resultFromFile.readLines().firstOrNull { it.contains("§ 9 Absatz 1 Satz") }
    println(changedLine)
    assertThat(changedLine).contains("§ 9 Absatz 1 Satz")
  }
}
