package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.ElementNotFoundException;
import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

/**
 * Implements various operations related to getting generic elements from a {@link Norm}. Note that
 * this is necessarily vague because we allow to work with any element that can be identified by an
 * eId. This can be many different things. If you need something more specific (e.g. specifically
 * related to articles), you should use a more specific service.
 */
@Service
public class ElementService implements LoadElementUseCase, LoadElementHtmlUseCase {

  private final LoadRegelungstextPort loadRegelungstextPort;
  private final XsltTransformationService xsltTransformationService;

  /** The types of elements that can be retrieved from a norm. */
  public enum ElementType {
    ARTICLE("article", "//body//article"),
    BOOK("book", "//body//book"),
    CHAPTER("chapter", "//body//chapter"),
    CONCLUSIONS("conclusions", "//act/conclusions"),
    PART("part", "//body//part"),
    PREAMBLE("preamble", "//act/preamble"),
    PREFACE("preface", "//act/preface"),
    SECTION("section", "//body//section"),
    SUBSECTION("subsection", "//body//subsection"),
    SUBTITLE("subtitle", "//body//subtitle"),
    TITLE("title", "//body//title");

    /** The name of the element. */
    public final String label;

    /** The XPath used for finding elements of that type in a norm. */
    public final String xPath;

    ElementType(String label, String xPath) {
      this.label = label;
      this.xPath = xPath;
    }
  }

  public ElementService(
    LoadRegelungstextPort loadRegelungstextPort,
    XsltTransformationService xsltTransformationService
  ) {
    this.loadRegelungstextPort = loadRegelungstextPort;
    this.xsltTransformationService = xsltTransformationService;
  }

  @Override
  public Node loadElement(final LoadElementUseCase.Options options) {
    final var xPath = getXPathForEid(options.eid());

    final var regelungstext = loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Options(options.eli()))
      .orElseThrow(() -> new RegelungstextNotFoundException(options.eli().toString()));

    return NodeParser.getNodeFromExpression(xPath, regelungstext.getDocument()).orElseThrow(() ->
      new ElementNotFoundException(options.eli().toString(), options.eid().toString())
    );
  }

  @Override
  public String loadElementHtml(final LoadElementHtmlUseCase.Options options) {
    final var elementXml = XmlMapper.toString(
      loadElement(new LoadElementUseCase.Options(options.eli(), options.eid()))
    );

    return xsltTransformationService.transformLegalDocMlToHtml(
      new TransformLegalDocMlToHtmlUseCase.Options(elementXml, false, false)
    );
  }

  private String getXPathForEid(EId eid) {
    return String.format("//*[@eId='%s']", eid.toString());
  }
}
