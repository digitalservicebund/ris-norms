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
    val amendingLaw =
        """
          <akn:body>
            <akn:mod>
              Change <akn:ref href="two">paragraph 2</akn:ref>
            </akn:mod>
          </akn:body>
        """

    val targetLaw =
        """
          <akn:body>
            <akn:p eId="one">old text</akn:p>
            <akn:p eId="two">old text</akn:p>
          </akn:body>
        """

    val result = applyModification(amendingLaw.asXml(), targetLaw.asXml(), "old", "new")

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
    val targetLawXml = anyTargetLaw.asXml()
    val modifiedTargetLaw = applyModification(anyAmendingLaw.asXml(), targetLawXml, "", "")

    assertThat(modifiedTargetLaw).isNotEqualTo(targetLawXml)
  }

  @Test
  fun `it throws an exception if the amending law has no modifications`() {
    val amendingLaw = "<akn:body></akn:body>"

    assertThatThrownBy { applyModification(amendingLaw.asXml(), anyTargetLaw.asXml(), "", "") }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("Amending law does not include any modification")
  }

  @Test
  fun `it throws an exception if the modification does not include a reference with an eId`() {
    val amendingLaw =
        """
          <akn:body>
            <akn:mod>Change paragraph 2</akn:mod>
          </akn:body>
        """

    assertThatThrownBy { applyModification(amendingLaw.asXml(), anyTargetLaw.asXml(), "", "") }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("Modification has no target reference")
  }

  @Test
  fun `it throws an exception if there is no element with the eId to modify`() {
    val amendingLaw =
        """
          <akn:body>
            <akn:mod>
              Change <akn:ref href="none">paragraph 2</akn:ref>
            </akn:mod>
          </akn:body>
        """

    val targetLaw =
        """
          <akn:body>
            <akn:p eId="any">text</akn:p>
          </akn:body>
        """

    assertThatThrownBy { applyModification(amendingLaw.asXml(), targetLaw.asXml(), "", "") }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("Could not find element to modify with eId: 'none'")
  }
}

private const val anyAmendingLaw =
    """
      <akn:body>
        <akn:mod>
          Change <akn:ref href="any">paragraph 2</akn:ref>
        </akn:mod>
      </akn:body>
    """

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
