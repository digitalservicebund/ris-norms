package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.port.input.TransformLegalDocMlToHtmlUseCase;
import de.bund.digitalservice.ris.norms.application.service.exceptions.XmlProcessingException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;

class XsltTransformationServiceTest {
  final Resource xsltResource = mock(Resource.class);
  final XsltTransformationService xsltTransformationService =
      new XsltTransformationService(xsltResource);

  @BeforeAll
  public static void setUp() {
    Locale.setDefault(Locale.ENGLISH);
  }

  @Test
  void shouldReturnTransformedXml() throws IOException {
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
  void shouldReturnTransformedXmlWithMetadata() throws IOException {
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
  void shouldReturnTransformedXmlWithoutMetadata() throws IOException {
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

    assertThat(throwable).isInstanceOf(XmlProcessingException.class);
    assertThat(throwable.getMessage())
        .isEqualToIgnoringWhitespace(
            """
            SXXP0003   Error reported by XML parser: Attribute name "xml" associated with an element type "invalid" must be followed by the ' = ' character.
            """);
  }

  /** To generate new expected files see {@link #generateExpectedHtmlForShouldTransformXml} */
  @ParameterizedTest(name = "{0}")
  @MethodSource("shouldTransformXmlArgumentsProvider")
  void shouldTransformXml(
      String name, String xmlFile, Boolean showMetadata, String expectedHtmlFile)
      throws IOException {
    var xml = loadTestResource(xmlFile);
    var expectedHtml = loadTestResource(expectedHtmlFile);
    var xsltResource =
        new FileUrlResource(
            Objects.requireNonNull(
                XsltTransformationService.class.getResource("/XSLT/html/legislation.xslt")));
    var xsltTransformationService = new XsltTransformationService(xsltResource);

    var result =
        xsltTransformationService.transformLegalDocMlToHtml(
            new TransformLegalDocMlToHtmlUseCase.Query(xml, showMetadata));

    assertThat(result).isEqualToIgnoringWhitespace(expectedHtml);
  }

  /**
   * This test can be used to generate new expected output files for {@link #shouldTransformXml}.
   * The files can be found in build/resources/test/... and then need to be copied to
   * /src/test/resources/...
   */
  @ParameterizedTest(name = "{0}")
  @MethodSource("shouldTransformXmlArgumentsProvider")
  @Disabled
  void generateExpectedHtmlForShouldTransformXml(
      String name, String xmlFile, Boolean showMetadata, String expectedHtmlFile)
      throws IOException {
    var xml = loadTestResource(xmlFile);
    var xsltResource =
        new FileUrlResource(
            Objects.requireNonNull(
                XsltTransformationService.class.getResource("/XSLT/html/legislation.xslt")));
    var xsltTransformationService = new XsltTransformationService(xsltResource);

    var result =
        xsltTransformationService.transformLegalDocMlToHtml(
            new TransformLegalDocMlToHtmlUseCase.Query(xml, showMetadata));

    saveTestResource(expectedHtmlFile, result);
  }

  static String loadTestResource(String fileName) throws IOException {
    var resource =
        XsltTransformationServiceTest.class.getResource("xsltTransformationService/" + fileName);
    assert resource != null;
    return IOUtils.toString(resource, StandardCharsets.UTF_8);
  }

  static void saveTestResource(String fileName, String result) throws IOException {
    var resource =
        XsltTransformationServiceTest.class.getResource("xsltTransformationService/" + fileName);
    assert resource != null;
    FileUtils.writeStringToFile(new File(resource.getFile()), result, StandardCharsets.UTF_8);
  }

  static Stream<Arguments> shouldTransformXmlArgumentsProvider() throws IOException {
    return Stream.of(
        Arguments.arguments(
            "Bundesverfassungsschutzgesetz",
            "Bundesverfassungsschutzgesetz.xml",
            false,
            "Bundesverfassungsschutzgesetz.html"),
        Arguments.arguments(
            "Bundesverfassungsschutzgesetz with metadata",
            "Bundesverfassungsschutzgesetz.xml",
            true,
            "Bundesverfassungsschutzgesetz-with-metadata.html"));
  }
}
