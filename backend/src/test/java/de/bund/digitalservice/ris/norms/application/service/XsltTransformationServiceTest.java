package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
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
    when(xsltResource.getURL()).thenReturn(URL.of(URI.create("https://example.com/"), null));

    var result =
        xsltTransformationService.transformLegalDocMlToHtml(
            new TransformLegalDocMlToHtmlUseCase.Query("<data>Test</data>", false));

    assertThat(result).isEqualToIgnoringWhitespace("<span>Test</span>");
  }

  @Test
  void shouldReturnTransformedXmlWithMetadata()
      throws TransformLegalDocMlToHtmlUseCase.XmlTransformationException, IOException {
    when(xsltResource.getInputStream())
        .thenReturn(
            new ByteArrayInputStream(
                """
                    <?xml version="1.0" encoding="UTF-8"?>
                    <xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
                      <xsl:param name="show-metadata" />

                      <xsl:output method="html" encoding="UTF-8" />

                       <xsl:template match="/">
                        <body>
                          <xsl:if test="$show-metadata">
                            <h1>METADATA</h1>
                          </xsl:if>
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
    when(xsltResource.getURL()).thenReturn(URL.of(URI.create("https://example.com/"), null));

    var result =
        xsltTransformationService.transformLegalDocMlToHtml(
            new TransformLegalDocMlToHtmlUseCase.Query("<data>Test</data>", true));

    assertThat(result)
        .isEqualToIgnoringWhitespace(
            """
            <body>
              <h1>METADATA</h1>
              <span>Test</span>
            </body>
            """);
  }

  @Test
  void shouldReturnTransformedXmlWithoutMetadata()
      throws TransformLegalDocMlToHtmlUseCase.XmlTransformationException, IOException {
    when(xsltResource.getInputStream())
        .thenReturn(
            new ByteArrayInputStream(
                """
                                <?xml version="1.0" encoding="UTF-8"?>
                                <xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
                                  <xsl:param name="show-metadata" />

                                  <xsl:output method="html" encoding="UTF-8" />

                                   <xsl:template match="/">
                                    <body>
                                      <xsl:if test="$show-metadata">
                                        <h1>METADATA</h1>
                                      </xsl:if>
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
    when(xsltResource.getURL()).thenReturn(URL.of(URI.create("https://example.com/"), null));

    var result =
        xsltTransformationService.transformLegalDocMlToHtml(
            new TransformLegalDocMlToHtmlUseCase.Query("<data>Test</data>", false));

    assertThat(result)
        .isEqualToIgnoringWhitespace(
            """
                    <body>
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
    when(xsltResource.getURL()).thenReturn(URL.of(URI.create("https://example.com/"), null));

    var throwable =
        catchThrowable(
            () ->
                xsltTransformationService.transformLegalDocMlToHtml(
                    new TransformLegalDocMlToHtmlUseCase.Query(
                        "<data><invalid xml</data>", false)));

    assertThat(throwable)
        .isInstanceOf(TransformLegalDocMlToHtmlUseCase.XmlTransformationException.class);
    assertThat(throwable.getMessage())
        .isEqualToIgnoringWhitespace(
            """
            SXXP0003   Error reported by XML parser: Attribute name "xml" associated with an element type "invalid" must be followed by the ' = ' character.
            """);
  }
}
