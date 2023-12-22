package de.bund.digitalservice.ris.norms.timemachine

import com.github.ajalt.clikt.testing.test
import com.github.stefanbirkner.systemlambda.SystemLambda
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TimeMachineTest {
  private val filePathAmendingLaw =
      this.javaClass.classLoader.getResource("07_01_änderungsgesetz.xml")!!.toURI()
  private val filePathToBeAmendedLaw =
      this.javaClass.classLoader.getResource("07_01_zuänderndesgesetz.xml")!!.toURI()

  @Test
  fun `return changed text on STDOUT when no parameter or option is given`() {
    val command = TimeMachine()
    val result = command.test("${filePathAmendingLaw?.path} ${filePathToBeAmendedLaw?.path}")

    assertThat(result.stdout).contains("§ 9 Absatz 1 Satz")
  }

  @Test
  fun `do not return anything via println()`() {
    val command = TimeMachine()
    val output =
        SystemLambda.tapSystemOut({
          command.test("${filePathAmendingLaw?.path} ${filePathToBeAmendedLaw?.path}")
        })
    assertThat(output).isEmpty()
  }
}
