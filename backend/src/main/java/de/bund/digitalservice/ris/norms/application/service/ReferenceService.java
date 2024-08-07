package de.bund.digitalservice.ris.norms.application.service;

import de.bund.digitalservice.ris.norms.application.exception.NormNotFoundException;
import de.bund.digitalservice.ris.norms.application.port.input.*;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Mod;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.NodeCreator;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.List;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/** Service for the reference pattern recognition. */
@Service
@Slf4j
public class ReferenceService implements ReferenceRecognitionUseCase {
  private final LoadNormPort loadNormPort;
  private final UpdateNormPort updateNormPort;

  public ReferenceService(LoadNormPort loadNormPort, UpdateNormPort updateNormPort) {
    this.loadNormPort = loadNormPort;
    this.updateNormPort = updateNormPort;
  }

  @Override
  public String findAndCreateReferences(Query query) {
    final Norm loadedNorm =
        loadNormPort
            .loadNorm(new LoadNormPort.Command(query.eli()))
            .orElseThrow(() -> new NormNotFoundException(query.eli()));

    if (loadedNorm.getMods().stream().anyMatch(Mod::containsRef)) {
      return XmlMapper.toString(loadedNorm.getDocument());
    }

    loadedNorm
        .getMods()
        .forEach(
            mod -> {
              mod.getSecondQuotedText().ifPresent(this::findAndCreateReferencesInNode);
              mod.getQuotedStructure().ifPresent(this::findAndCreateReferencesInNode);
            });

    updateNormPort.updateNorm(new UpdateNormPort.Command(loadedNorm));

    return XmlMapper.toString(loadedNorm.getDocument());
  }

  private void findAndCreateReferencesInNode(final Node node) {
    NodeParser.nodeListToList(node.getChildNodes())
        .forEach(
            child -> {
              final short nodeType = child.getNodeType();
              if (nodeType == Node.TEXT_NODE) {
                final String textContent = child.getTextContent();
                if (textContent != null && !textContent.trim().isEmpty()) {
                  final String cleandText = textContent.trim().replaceAll("\\s+", " ");
                  final List<MatchInfo> matches = findReferences(cleandText);
                  replaceMatchesWithNewNodes(child.getParentNode(), child, matches);
                }
              } else if (nodeType == Node.ELEMENT_NODE) {
                findAndCreateReferencesInNode(child);
              }
            });
  }

  private static void replaceMatchesWithNewNodes(
      final Node parent, final Node textNodeToReplace, final List<MatchInfo> matches) {
    final String originalText = textNodeToReplace.getTextContent().trim().replaceAll("\\s+", " ");
    int currentPositionInText = 0;
    int refCounter = 1;

    // Create fragment to then replace the old node with the fragment
    final Node newChildFragment = parent.getOwnerDocument().createDocumentFragment();

    for (final MatchInfo match : matches) {
      // Text before the match (and also text between matches)
      if (currentPositionInText < match.start()) {
        final String beforeText = originalText.substring(currentPositionInText, match.start());
        if (!beforeText.isBlank()) { // Avoid adding empty text nodes
          final Text beforeTextNode = parent.getOwnerDocument().createTextNode(beforeText);
          newChildFragment.appendChild(beforeTextNode);
        }
      }

      // The matched text, replace with new element
      final Element newElement =
          NodeCreator.createElementWithStaticEidAndGuidNoAppend(
              "akn:ref", "ref-" + refCounter, parent);
      newElement.setAttribute("href", "");
      newElement.setTextContent(match.reference());
      newChildFragment.appendChild(newElement);

      // Update lastIndex and refCounter
      currentPositionInText = match.end() + 1;
      refCounter++;
    }

    // Text after the last match
    if (currentPositionInText < originalText.length()) {
      final String afterText = originalText.substring(currentPositionInText);
      if (!afterText.isBlank()) { // Avoid adding empty text nodes
        final Text afterTextNode = parent.getOwnerDocument().createTextNode(afterText);
        newChildFragment.appendChild(afterTextNode);
      }
    }
    parent.replaceChild(newChildFragment, textNodeToReplace);
  }

  private List<MatchInfo> findReferences(final String text) {
    final Pattern pattern = Pattern.compile(REFERENCE_REGEX);
    return pattern
        .matcher(text)
        .results()
        .map(match -> new MatchInfo(match.group(), match.start(), match.end() - 1))
        .toList();
  }

  record MatchInfo(String reference, int start, int end) {}

  static final String REFERENCE_REGEX =
      "(?:Anhang(?: \\w+(?: Teil \\w+(?: Buchstabe \\w+(?: Unterabsatz \\w+(?: Satz \\w+)?)?)?)?)?|Anlage(?: \\w+)?(?:(?:.*?) vom \\d+\\. (?:.*?) \\d+)?(?: \\((?:R|B)GBl\\.(?: \\d+)? I(?:I)? (?:S\\. \\w+(?:, \\d+)?(?:\\; \\d+ I S\\. \\d+)?|Nr\\. \\d+)\\))?|Artikel(?:s)? \\w+(?: Nummer \\w+(?: Absatz \\w+)?)? der Verordnung vom \\d+\\. (?:.*?) \\d+ \\((?:R|B)GBl\\.(?: \\d+)? I(?:I)? (?:S\\. \\w+(?:, \\d+)?(?:\\; \\d+ I S\\. \\d+)?|Nr\\. \\d+)\\)|Artikel(?:s)? \\w+(?: Nummer \\w+(?: (?:Abs(?:atz|.)?) \\w+)?)?(?: (?:Abs(?:atz|.)?) \\w+(?: Satz \\d+(?: und \\d+)?)?)? des (?:G|(?:.*?)g)esetzes(?: vom \\d+\\. (?:.*?) \\d+ \\((?:R|B)GBl\\.(?: \\d+)? I(?:I)? (?:S\\. \\w+(?:, \\d+)?(?:\\; \\d+ I S\\. \\d+)?|Nr\\. \\d+)\\))?|Artikel \\w+(?: (?:Abs(?:atz|.)?) \\d+(?: (?:und|oder) \\d+)?(?: (?:Unterabs|S)atz \\d+(?: (?:und|oder) \\d+)?(?: [a-zA-Z]+ Gedankenstrich)?| Buchstabe \\w+(?: Satz \\w+)?)?| Buchstabe \\w+| Nummer \\d+(?: Satz \\d+)?)?|(?<!§)§ \\w+(?: (?:(?:Abs(?:atz|.) (?:\\w+(?: (?:und|bis|oder) \\w+)?)(?: Satz (?:\\w+(?: (?:und|bis|oder) \\w+)?)(?: (?:Nummer|Nr.) (?:\\w+(?: (?:und|bis|oder) \\w+)?)(?: Buchstabe \\w+(?: Satz \\w+)?| Satz \\w+(?: Buchstabe \\w+)?)?| Buchstabe \\w+)?| (?:Nummer|Nr.) (?:\\w+(?: (?:und|bis|oder) \\w+)?)(?: Satz (?:\\w+(?: (?:und|bis|oder) \\w+)?)(?: (?:Nummer|Nr.) \\w+| Buchstabe \\w+)?| Buchstabe \\w+(?: Satz \\w+| und \\w+)?)?| Buchstabe \\w+)?)|Nummer \\w+(?: Satz \\w+(?: und \\w+| Buchstabe \\w+(?: Satz \\w+)?)?)?|Satz \\w+)| in der Fassung der Bekanntmachung)?(?:(?:.*?) vom \\d+\\. (?:.*?) \\d+ \\((?:R|B)GBl\\.(?: \\d+)? I(?:I)? (?:S\\. \\w+(?:, \\d+)?(?:\\; \\d+ I S\\. \\d+)?|Nr\\. \\d+)\\)| des (?:.*?)(?:-G|g)esetzes)?|§§ (\\w+), (\\w+) (?:des ((?:.*?)gesetz(?:es)?)|der (?:.*?)ordnung)|Richtlinie(?: \\(EU\\))? \\d+\\/\\d+(?:\\/EG|\\/EWG)?|Richtlinien(?: \\(EU\\))? \\d+\\/\\d+(?:\\/EG|\\/EWG)? und \\d+\\/\\d+(?:\\/EG|\\/EWG)?|(?:(?:[a-zA-ZüöäÜÄÖ]*?)v|V)erordnung \\((?:EG|EU)\\)(?: Nr.)? \\d+\\/\\d+(?:(?:.*?) \\(ABl\\. L \\w+ vom \\d+\\.\\d+\\.\\d+\\, S\\. \\d+\\))?|(?:Verordnungen|und|,) \\((?:EG|EU|EWG)\\)(?: Nr.)? \\d+\\/\\d+|(?:[a-z0-9A-Z\\-üöäßÜÖÄ]+(?: und [a-z0-9A-Z\\-üöäßÜÖÄ]+)?)?(?:[gG]esetz|[vV]erordnung|Protokoll zum|ordnung)(?:.*?) vom \\d+\\. (?:.*?) \\d+ \\((?:R|B)GBl\\.(?: \\d+)? I(?:I)? (?:S\\. \\w+(?:, \\d+)?(?:\\; \\d+ I S\\. \\d+)?|Nr\\. \\d+)\\)(?: in der Fassung des § \\d+ Abs\\. \\d+ des (?:.*?)gesetzes vom \\d+\\. (?:.*?) \\d+ \\((?:R|B)GBl\\.(?: \\d+)? I(?:I)? (?:S\\. \\w+(?:, \\d+)?(?:\\; \\d+ I S\\. \\d+)?|Nr\\. \\d+)\\)|, (?:die|das)(?: zuletzt)? durch Artikel \\d+(?: Absatz \\d+)? (?:des Gesetzes|der Verordnung) vom \\d+\\. (?:.*?) \\d+ \\((?:R|B)GBl\\.(?: \\d+)? I(?:I)? (?:S\\. \\w+(?:, \\d+)?(?:\\; \\d+ I S\\. \\d+)?|Nr\\. \\d+)\\))?(?: geändert worden ist)?)";
}
