package de.bund.digitalservice.ris.norms;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.w3c.dom.Node;

public class XPathMatcher extends TypeSafeMatcher<Node> {

  private final String xPath;
  private final Matcher<String> matcher;

  public XPathMatcher(String xPath, Matcher<String> matcher) {
    this.xPath = xPath;
    this.matcher = matcher;
  }

  public XPathMatcher(String xPath) {
    this(xPath, Matchers.any(String.class));
  }

  public void describeTo(Description description) {
    description.appendText("XPath %s to match: ".formatted(xPath));
    matcher.describeTo(description);
  }

  public static Matcher<Node> hasXPath(String xPath, Matcher<String> matcher) {
    return new XPathMatcher(xPath, matcher);
  }

  public static Matcher<Node> hasXPath(String xPath) {
    return new XPathMatcher(xPath);
  }

  @Override
  protected boolean matchesSafely(Node node) {
    return NodeParser.getValueFromExpression(this.xPath, node).map(matcher::matches).orElse(false);
  }
}
