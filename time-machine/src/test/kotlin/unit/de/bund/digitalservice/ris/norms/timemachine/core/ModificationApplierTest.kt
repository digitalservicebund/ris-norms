package de.bund.digitalservice.ris.norms.timemachine.core

import java.io.ByteArrayInputStream
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilderFactory
import org.junit.jupiter.api.Test
import org.w3c.dom.Document
import org.xmlunit.assertj3.XmlAssert.assertThat

class ModificationApplierTest {
  @Test
  fun `it replaces the text value of the node with the matching eId attribute`() {
    val targetLaw =
        """
          <?xml version='1.0'?>
          <akn:body>
            <akn:p eId="one">old text</akn:p>
            <akn:p eId="two">old text</akn:p>
          </akn:body>
        """

    val result = applyModification(targetLaw.asXml(), "two", "new text")

    assertThat(result)
        .and(
            """
              <?xml version='1.0'?>
              <akn:body>
                <akn:p eId="one">old text</akn:p>
                <akn:p eId="two">new text</akn:p>
              </akn:body>
            """
                .asXml(),
        )
        .areIdentical()
  }
}

private fun String.asXml(): Document {
  val inputStream = ByteArrayInputStream(this.trimIndent().toByteArray())
  val factory = DocumentBuilderFactory.newInstance()
  factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
  return factory.newDocumentBuilder().parse(inputStream)
}
