package de.bund.digitalservice.ris.norms.utils.exceptions;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * This exception indicates that a mandatory XML node was not found. If node name is passed, the
 * xpath is relative. If norm eli not passed is because the node containing the eli is not present.
 */
@Getter
public class MandatoryNodeNotFoundException extends RuntimeException implements NormsAppException {

  private final String xpath;
  private final String eli;
  private final String node;

  public MandatoryNodeNotFoundException(String xpath) {
    super(String.format("Element with xpath '%s' not found", xpath));
    this.xpath = xpath;
    this.eli = null;
    this.node = null;
  }

  public MandatoryNodeNotFoundException(String xpath, String normEli) {
    super(String.format("Element with xpath '%s' not found in norm '%s'", xpath, normEli));
    this.xpath = xpath;
    this.eli = normEli;
    this.node = null;
  }

  public MandatoryNodeNotFoundException(String xpath, String nodeName, String normEli) {
    super(
      String.format(
        "Element with xpath '%s' not found in '%s' of norm '%s'",
        xpath,
        nodeName,
        normEli
      )
    );
    this.xpath = xpath;
    this.eli = normEli;
    this.node = nodeName;
  }

  @Override
  public URI getType() {
    return URI.create("/errors/mandatory-node-not-found");
  }

  @Override
  public String getTitle() {
    return "Mandatory node not found";
  }

  @Override
  public Map<String, Object> getProperties() {
    Map<String, Object> properties = new HashMap<>();
    properties.put("xpath", getXpath());
    if (StringUtils.isNotEmpty(getEli())) {
      properties.put("eli", getEli());
    }
    if (StringUtils.isNotEmpty(getNode())) {
      properties.put("nodeName", getNode());
    }
    return properties;
  }
}
