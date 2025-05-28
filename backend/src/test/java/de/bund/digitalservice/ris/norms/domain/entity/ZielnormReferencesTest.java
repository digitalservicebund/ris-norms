package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.eid.EId;
import de.bund.digitalservice.ris.norms.domain.entity.eli.NormWorkEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class ZielnormReferencesTest {

  @Test
  void remove() {
    var zielnormReferences = new ZielnormReferences(
      XmlMapper.toElement(
        """
         <norms:zielnorm-references xmlns:norms='http://MetadatenMods.LegalDocML.de/1.7.2/'>
              <norms:zielnorm-reference>
                  <norms:typ>Änderungsvorschrift</norms:typ>
                  <norms:geltungszeit>gz-1</norms:geltungszeit>
                  <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n1</norms:eid>
                  <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
              </norms:zielnorm-reference>
              <norms:zielnorm-reference>
                  <norms:typ>Aufhebung</norms:typ>
                  <norms:geltungszeit>gz-2</norms:geltungszeit>
                  <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n2</norms:eid>
                  <norms:zielnorm>eli/bund/bgbl-1/2019/789</norms:zielnorm>
              </norms:zielnorm-reference>
        </norms:zielnorm-references>
        """
      )
    );

    var ref = zielnormReferences.iterator().next();
    System.out.println(ref);
    zielnormReferences.remove(ref);

    assertThat(zielnormReferences).hasSize(1);
  }

  @Test
  void add() {
    var zielnormReferences = new ZielnormReferences(
      XmlMapper.toElement(
        """
         <norms:zielnorm-references xmlns:norms='http://MetadatenMods.LegalDocML.de/1.7.2/'>
              <norms:zielnorm-reference>
                  <norms:typ>Änderungsvorschrift</norms:typ>
                  <norms:geltungszeit>gz-1</norms:geltungszeit>
                  <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n1</norms:eid>
                  <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
              </norms:zielnorm-reference>
              <norms:zielnorm-reference>
                  <norms:typ>Aufhebung</norms:typ>
                  <norms:geltungszeit>gz-2</norms:geltungszeit>
                  <norms:eid>art-z1_abs-n1_untergl-n1_listenelem-n2</norms:eid>
                  <norms:zielnorm>eli/bund/bgbl-1/2019/789</norms:zielnorm>
              </norms:zielnorm-reference>
        </norms:zielnorm-references>
        """
      )
    );

    zielnormReferences.add(
      "Änderungsvorschrift",
      new Zeitgrenze.Id("gz-2"),
      new EId("art-z1_abs-n1_untergl-n1_listenelem-n3"),
      NormWorkEli.fromString("eli/bund/bgbl-1/2021/123")
    );

    assertThat(zielnormReferences).hasSize(3);
  }
}
