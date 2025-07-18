package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.RegelungstextNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.LoadTocFromRegelungstextUseCase;
import de.bund.digitalservice.ris.norms.application.port.output.LoadRegelungstextPort;
import de.bund.digitalservice.ris.norms.domain.entity.Regelungstext;
import de.bund.digitalservice.ris.norms.domain.entity.TableOfContentsItem;
import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentEli;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** Implements operations for loading the table of contents of a {@link Regelungstext} */
@Service
public class TableOfContentsService implements LoadTocFromRegelungstextUseCase {

  private static final Set<String> TOC_ELEMENTS = Set.of(
    "book",
    "part",
    "chapter",
    "subchapter",
    "section",
    "subsection",
    "title",
    "subtitle",
    "article"
  );

  final LoadRegelungstextPort loadRegelungstextPort;

  TableOfContentsService(LoadRegelungstextPort loadRegelungstextPort) {
    this.loadRegelungstextPort = loadRegelungstextPort;
  }

  @Override
  public List<TableOfContentsItem> loadTocFromRegelungstext(Options options) {
    return loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Options(options.dokumentExpressionEli()))
      .map(this::loadToc)
      .orElseThrow(() ->
        new RegelungstextNotFoundException(options.dokumentExpressionEli().toString())
      );
  }

  private List<TableOfContentsItem> loadToc(final Regelungstext regelungstext) {
    final Element body = NodeParser.getMandatoryElementFromExpression(
      "//body",
      regelungstext.getDocument()
    );
    return getChildren(body);
  }

  private List<TableOfContentsItem> getChildren(final Element element) {
    final List<TableOfContentsItem> tableOfContents = new ArrayList<>();

    final NodeList nodes = element.getChildNodes();
    for (int i = 0; i < nodes.getLength(); i++) {
      final Node node = nodes.item(i);
      // Ensures only Element nodes are processed, we don't care about text or comment nodes, that are TOC elements
      if (
        node instanceof Element childElement && TOC_ELEMENTS.contains(childElement.getLocalName())
      ) {
        tableOfContents.add(getTableOfContentsItem(childElement));
      }
    }
    return tableOfContents;
  }

  private TableOfContentsItem getTableOfContentsItem(final Element element) {
    // eId
    final EId eId = new EId(element.getAttribute("eId").trim());

    // marker
    final String marker = cleanText(
      NodeParser.getMandatoryElementFromExpression("./num", element).getTextContent()
    );

    // heading (article has it optional)
    String heading = NodeParser.getElementFromExpression("./heading", element)
      .map(e -> cleanText(e.getTextContent()))
      .orElse(null);

    // type
    final String type = element.getLocalName();

    // children
    final List<TableOfContentsItem> children = type.equals("article")
      ? List.of()
      : getChildren(element);

    // eingebundene Stammform
    var hasEingebundeneStammform = false;
    var stammformEli = getEingebundeneStammformEli(element);
    if (stammformEli.isPresent()) {
      hasEingebundeneStammform = true;
      heading = getHeadingForEingebundeneStammform(stammformEli.get()).orElse(null);
    }

    return new TableOfContentsItem(eId, marker, heading, type, hasEingebundeneStammform, children);
  }

  private Optional<DokumentEli> getEingebundeneStammformEli(Element element) {
    return NodeParser.getElementFromExpression(
      "componentRef[@showAs=\"regelungstext-eingebundene-stammform\"]",
      element
    ).map(componentRef -> {
        var stammformEliRaw = componentRef.getAttribute("src");
        return DokumentManifestationEli.fromString(stammformEliRaw);
      });
  }

  private Optional<String> getHeadingForEingebundeneStammform(DokumentEli stammformEli) {
    return loadRegelungstextPort
      .loadRegelungstext(new LoadRegelungstextPort.Options(stammformEli))
      .map(stammformRegelungstext ->
        NodeParser.getElementFromExpression("//longTitle", stammformRegelungstext.getDocument())
          .orElse(null)
          .getTextContent()
      )
      .map(this::cleanText);
  }

  private String cleanText(String text) {
    return text == null ? "" : text.trim().replace("\n", "").replaceAll("\\s+", " ");
  }
}
