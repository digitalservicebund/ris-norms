package de.bund.digitalservice.ris.norms;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.w3c.dom.Node;

public class XmlMatcher extends TypeSafeMatcher<String> {

  private final Matcher<Node> matcher;

  public XmlMatcher(Matcher<Node> matcher) {
    this.matcher = matcher;
  }

  public void describeTo(Description description) {
    description.appendText("a string representing an xml document");
  }

  public static Matcher<String> xml(Matcher<Node> matcher) {
    return new XmlMatcher(matcher);
  }

  @Override
  protected boolean matchesSafely(String str) {
    return matcher.matches(XmlMapper.toDocument(str));
  }
}
