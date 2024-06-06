package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class ProprietaryTest {
  @Test
  void returnsTheFna() {
    final Proprietary proprietary =
        Proprietary.builder()
            .node(
                XmlMapper.toNode(
                    """
                                      <akn:proprietary eId="meta-1_proprietary-1"
                                                       GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                       source="attributsemantik-noch-undefiniert">
                                          <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                                              <meta:typ>gesetz</meta:typ>
                                              <meta:fna>754-28-1</meta:fna>
                                              <meta:fassung>verkuendungsfassung</meta:fassung>
                                          </meta:legalDocML.de_metadaten>
                                      </akn:proprietary>
                                      """))
            .build();

    assertThat(proprietary.getFna()).contains("754-28-1");
  }

  @Test
  void returnsEmptyOptionalIfFnaIsMissing() {

    final Proprietary proprietary =
        Proprietary.builder()
            .node(
                XmlMapper.toNode(
                    """
                                      <akn:proprietary eId="meta-1_proprietary-1"
                                                       GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                       source="attributsemantik-noch-undefiniert">
                                          <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                                              <meta:typ>gesetz</meta:typ>
                                              <meta:form>stammform</meta:form>
                                              <meta:fassung>verkuendungsfassung</meta:fassung>
                                          </meta:legalDocML.de_metadaten>
                                      </akn:proprietary>
                                      """))
            .build();

    assertThat(proprietary.getFna()).isEmpty();
  }
}
