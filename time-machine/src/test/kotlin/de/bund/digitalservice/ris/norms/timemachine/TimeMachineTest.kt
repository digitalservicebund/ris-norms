package de.bund.digitalservice.ris.norms.timemachine

import com.github.ajalt.clikt.testing.test
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TimeMachineTest {
  private val filePathAmendingLaw =
      this.javaClass.classLoader.getResource("07_01_änderungsgesetz.xml")!!.toURI()
  private val filePathToBeAmendedLaw =
      this.javaClass.classLoader.getResource("07_01_zuänderndesgesetz.xml")!!.toURI()

  // TODO: remove if there is no strong objection
  // @Test
  // fun `store amended law with changed text to disk when given to-be-amended law and amending
  // law`() {
  //   val command = TimeMachine()
  //   val workingDir = Paths.get("").toAbsolutePath().toString()
  //   val absolutePathResult =
  //       workingDir + "/" + fileNameToBeAmendedLaw.substringBeforeLast(".") + "_amended.xml"
  //   val resultFromFile = File(Paths.get(absolutePathResult).toUri())

  //   command.test("${filePathAmendingLaw?.path} ${filePathToBeAmendedLaw?.path}")

  //   assertThat(resultFromFile).exists()
  //   assertThat(resultFromFile).isFile()
  //   val changedLine = resultFromFile.readLines().firstOrNull { it.contains("§ 9 Absatz 1 Satz") }
  //   assertThat(changedLine).contains("§ 9 Absatz 1 Satz")
  // }

  // TODO: remove if there is no strong objection
  // @Test
  // fun `store amended law with changed text to disk when given to-be-amended law and amending law
  // and using '--stdout'`() {
  //   val command = TimeMachine()
  //   val workingDir = Paths.get("").toAbsolutePath().toString()
  //   val absolutePathResult =
  //       workingDir + "/" + fileNameToBeAmendedLaw.substringBeforeLast(".") + "_amended.xml"
  //   val resultFromFile = File(Paths.get(absolutePathResult).toUri())

  //   command.test("${filePathAmendingLaw.path} ${filePathToBeAmendedLaw.path}")

  //   assertThat(resultFromFile).exists()
  //   assertThat(resultFromFile).isFile()
  //   val changedLine = resultFromFile.readLines().firstOrNull { it.contains("§ 9 Absatz 1 Satz") }
  //   assertThat(changedLine).contains("§ 9 Absatz 1 Satz")
  // }

  @Test
  fun `return changed text on STDOUT when no parameter or option is given`() {
    val command = TimeMachine()
    val result = command.test("${filePathAmendingLaw?.path} ${filePathToBeAmendedLaw?.path}")

    assertThat(result.stdout).contains("§ 9 Absatz 1 Satz")
  }
}
