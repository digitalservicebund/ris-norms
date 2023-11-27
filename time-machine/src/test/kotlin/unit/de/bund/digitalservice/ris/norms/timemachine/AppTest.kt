package de.bund.digitalservice.ris.norms.timemachine

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AppTest {
  @Test
  fun `it greets correctly`() {
    val app = App()
    assertThat(app.greeting).isEqualTo("Hello World!")
  }
}
