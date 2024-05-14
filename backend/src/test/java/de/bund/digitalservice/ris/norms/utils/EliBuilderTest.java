package de.bund.digitalservice.ris.norms.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EliBuilderTest {
  @Test
  void itShouldReturnEli() {
    // given
    String agent = "agent";
    String year = "year";
    String naturalIdentifier = "naturalIdentifier";
    String pointInTime = "pointInTime";
    String version = "version";
    String language = "language";
    String subtype = "subtype";

    // when
    String eli =
        EliBuilder.buildEli(
            agent, year, naturalIdentifier, pointInTime, version, language, subtype);

    // then
    Assertions.assertEquals(
        eli, "eli/bund/agent/year/naturalIdentifier/pointInTime/version/language/subtype");
  }
}
