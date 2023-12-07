package de.bund.digitalservice.ris.norms.timemachine.core

import java.io.ByteArrayInputStream
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilderFactory
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.w3c.dom.Document
import org.xmlunit.assertj3.XmlAssert

class ModificationApplierTest {
  @Test
  fun `it replaces the text value of the node with the matching eId attribute of the amending law`() {
    val targetLaw =
        """
          <akn:body>
            <akn:p eId="one">old text</akn:p>
            <akn:p eId="two">old text</akn:p>
          </akn:body>
        """

    val result = applyModification(targetLaw.asXml(), "two", "old", "new")

    XmlAssert.assertThat(result)
        .and(
            """
              <akn:body>
                <akn:p eId="one">old text</akn:p>
                <akn:p eId="two">new text</akn:p>
              </akn:body>
            """
                .asXml(),
        )
        .areIdentical()
  }

  @Test
  fun `it does not modify the orginal target law`() {
    val targetLaw = anyTargetLaw.asXml()
    val modifiedTargetLaw = applyModification(targetLaw, anyEId, "", "")

    assertThat(modifiedTargetLaw).isNotEqualTo(targetLaw)
  }

  @Test
  fun `it throws an exception if there is no element with the given eId`() {
    val targetLaw =
        """
          <akn:body>
            <akn:p eId="any">text</akn:p>
          </akn:body>
        """

    assertThatThrownBy { applyModification(targetLaw.asXml(), "none", "", "") }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("Could not find element with eId: 'none'")
  }
}

private const val anyEId = "any"
private const val anyTargetLaw =
    """
        <akn:body>
          <akn:p eId="any">text</akn:p>
        </akn:body>
      """

private const val xmlHeader = "<?xml version='1.0'?>"

private fun String.asXml(): Document {
  val withHeader = xmlHeader + this.trimIndent()
  val inputStream = ByteArrayInputStream(withHeader.toByteArray())
  val factory = DocumentBuilderFactory.newInstance()
  factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
  return factory.newDocumentBuilder().parse(inputStream)
}
