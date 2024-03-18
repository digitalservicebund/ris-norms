package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

class XsltTransformationServiceTest {
  final Resource xsltResource = mock(Resource.class);
  final XsltTransformationService xsltTransformationService =
      new XsltTransformationService(xsltResource);

  @Test
  void shouldReturnTransformedXml()
      throws TransformLegalDocMlToHtmlUseCase.XmlTransformationException, IOException {
    when(xsltResource.getInputStream())
        .thenReturn(
            new ByteArrayInputStream(
                """
                    <?xml version="1.0" encoding="UTF-8"?>
                    <xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
                      <xsl:output method="html" encoding="UTF-8" />

                      <xsl:template match="/data">
                        <span>
                          <xsl:apply-templates />
                        </span>
                      </xsl:template>
                    </xsl:stylesheet>
                    """
                    .getBytes()));
    var result =
        xsltTransformationService.transformLegalDocMlToHtml(
            new TransformLegalDocMlToHtmlUseCase.Query(
                "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1", "<data>Test</data>"));

    assertThat(result).isEqualToIgnoringWhitespace("<span>Test</span>");
  }

  @Test
  void shouldReturnTransformedXmlWithEli()
      throws TransformLegalDocMlToHtmlUseCase.XmlTransformationException, IOException {
    when(xsltResource.getInputStream())
        .thenReturn(
            new ByteArrayInputStream(
                """
                    <?xml version="1.0" encoding="UTF-8"?>
                    <xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema">
                      <xsl:param name="legislation-identifier" as="xs:string" />

                      <xsl:output method="html" encoding="UTF-8" />

                       <xsl:template match="/">
                        <body>
                          <h1><xsl:value-of select="$legislation-identifier" /></h1>
                          <xsl:apply-templates />
                        </body>
                      </xsl:template>

                      <xsl:template match="/data">
                        <span>
                          <xsl:apply-templates />
                        </span>
                      </xsl:template>

                    </xsl:stylesheet>
                    """
                    .getBytes()));
    var result =
        xsltTransformationService.transformLegalDocMlToHtml(
            new TransformLegalDocMlToHtmlUseCase.Query(
                "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1", "<data>Test</data>"));

    assertThat(result)
        .isEqualToIgnoringWhitespace(
            """
            <body xmlns:xs="http://www.w3.org/2001/XMLSchema">
              <h1>eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1</h1>
              <span>Test</span>
            </body>
            """);
  }

  @Test
  void shouldThrowXmlTransformationExceptionWhenXmlIsNotValid() throws IOException {
    when(xsltResource.getInputStream())
        .thenReturn(
            new ByteArrayInputStream(
                """
                    <?xml version="1.0" encoding="UTF-8"?>
                    <xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
                      <xsl:output method="html" encoding="UTF-8" />

                      <xsl:template match="/data">
                        <span>
                          <xsl:apply-templates />
                        </span>
                      </xsl:template>
                    </xsl:stylesheet>
                    """
                    .getBytes()));

    var throwable =
        catchThrowable(
            () ->
                xsltTransformationService.transformLegalDocMlToHtml(
                    new TransformLegalDocMlToHtmlUseCase.Query(
                        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
                        "<data><invalid xml</data>")));

    assertThat(throwable)
        .isInstanceOf(TransformLegalDocMlToHtmlUseCase.XmlTransformationException.class);
    assertThat(throwable.getMessage())
        .isEqualToIgnoringWhitespace(
            """
            SXXP0003   Error reported by XML parser: Attribute name "xml" associated with an element type "invalid" must be followed by the ' = ' character.
            """);
  }
}
