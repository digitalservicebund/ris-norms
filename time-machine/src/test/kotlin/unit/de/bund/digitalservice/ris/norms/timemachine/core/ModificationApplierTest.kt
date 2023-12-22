package de.bund.digitalservice.ris.norms.timemachine.core

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
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
             In <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph 2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.
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

    val result = applyModification(amendingLaw.asXml(), targetLaw.asXml())

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
  fun `it does not modify the original target law`() { // check object equality
    val targetLawXml = anyTargetLaw.asXml()
    val modifiedTargetLaw = applyModification(anyAmendingLaw.asXml(), targetLawXml)

    assertThat(modifiedTargetLaw).isNotEqualTo(targetLawXml)
  }

  @Test
  fun `it throws an exception if the amending law has no modifications`() {
    val amendingLaw = "<akn:body></akn:body>"

    assertThatThrownBy { applyModification(amendingLaw.asXml(), anyTargetLaw.asXml()) }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("Amending law does not include any modification")
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

    assertThatThrownBy { applyModification(amendingLaw.asXml(), targetLaw.asXml()) }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("Could not find element to modify with eId: 'none'")
  }

  @Test
  fun `it throws an exception if the text to be replaced can't be found`() {
    val amendingLaw =
        """
      <akn:body>
        <akn:mod>
            In <akn:ref href="any">paragraph 2</akn:ref> .
        </akn:mod>
      </akn:body>
    """

    assertThatThrownBy { applyModification(amendingLaw.asXml(), anyTargetLaw.asXml()) }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("Could not find text that should be replaced")
  }

  @Test
  fun `it throws an exception if the replacement text can't be found`() {
    val amendingLaw =
        """
      <akn:body>
        <akn:mod>
            In <akn:ref href="any">paragraph 2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with.
        </akn:mod>
      </akn:body>
    """

    assertThatThrownBy { applyModification(amendingLaw.asXml(), anyTargetLaw.asXml()) }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("Could not find replacement text")
  }

  @Test
  fun `it throws an exception if no href is found in modification`() {
    val amendingLaw =
        """
      <akn:body>
        <akn:mod>
        </akn:mod>
      </akn:body>
    """

    assertThatThrownBy { applyModification(amendingLaw.asXml(), anyTargetLaw.asXml()) }
        .isInstanceOf(IllegalArgumentException::class.java)
        .hasMessage("Could not find href in modification")
  }

  @Test
  fun `it gets the lokale Komponente from an ELI`() {
    val eli =
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-34.xml"

    val result = extractEid(eli)

    assertThat(result).isEqualTo("para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1")
  }

  @Test
  fun `it does not print to STDOUT`() {
    val amendingLaw =
        """
          <akn:body>
            <akn:mod>
             In <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/two/9-34.xml">paragraph 2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.
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

    val output = tapSystemOut({ applyModification(amendingLaw.asXml(), targetLaw.asXml()) })

    assertThat(output).isEmpty()
  }
}

private const val anyAmendingLaw =
    """
      <akn:body>
        <akn:mod>
            In <akn:ref href="any">paragraph 2</akn:ref> replace <akn:quotedText>old</akn:quotedText> with <akn:quotedText>new</akn:quotedText>.
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
