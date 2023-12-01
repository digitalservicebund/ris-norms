package de.bund.digitalservice.ris.norms.timemachine.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ModificationApplierTest {
  @Test
  fun `it replaces the text value of the same element with the modifications`() {
    val newNodeText = "new text"
    val targetLawNode =
        """
            <?xml version="1.0" encoding="UTF-8"?>
            <akn:p eId="theEId">old text</akn:p>
        """
            .trimIndent()

    val result: String = applyModification(newNodeText, targetLawNode)

    assertThat(result)
        .isEqualToIgnoringWhitespace(
            """
            <?xml version="1.0" encoding="UTF-8"?>
            <akn:p eId="theEId">new text</akn:p>
        """
                .trimIndent())
  }
  // TODO: replace text of the correct <akn:p>
  // IDEA: move to XML documents for input + output (check if test output is helpful, if equality
  // behaves as expected)
}
