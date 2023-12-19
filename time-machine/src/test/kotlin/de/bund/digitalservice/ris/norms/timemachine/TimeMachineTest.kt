package de.bund.digitalservice.ris.norms.timemachine

import com.github.ajalt.clikt.testing.test
import java.io.File
import java.nio.file.Paths
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TimeMachineTest {
  private val filePathAmendingLaw =
      this.javaClass.classLoader.getResource("07_01_änderungsgesetz.xml")?.toURI()
  private val fileNameToBeAmendedLaw = "07_01_zuänderndesgesetz.xml"
  private val filePathToBeAmendedLaw =
      this.javaClass.classLoader.getResource(fileNameToBeAmendedLaw)?.toURI()

  @Test
  fun `return amended law with changed text when given to-be-amended law and amending law`() {
    val command = TimeMachine()
    val workingDir = Paths.get("").toAbsolutePath().toString()
    val absolutePathResult =
        workingDir + "/" + fileNameToBeAmendedLaw.substringBeforeLast(".") + "_amended.xml"
    val resultFromFile = File(Paths.get(absolutePathResult).toUri())

    command.test("${filePathAmendingLaw?.path} ${filePathToBeAmendedLaw?.path}")

    assertThat(resultFromFile).exists()
    assertThat(resultFromFile).isFile()
    val changedLine = resultFromFile.readLines().firstOrNull { it.contains("§ 9 Absatz 1 Satz") }
    println(changedLine)
    assertThat(changedLine).contains("§ 9 Absatz 1 Satz")
  }

  @Test
  fun `return changed text on STDOUT`() {
    val command = TimeMachine()
    val result =
        command.test("--stdout ${filePathAmendingLaw?.path} ${filePathToBeAmendedLaw?.path}")

    assertThat(result.stdout).contains("§ 9 Absatz 1 Satz")
  }
}
