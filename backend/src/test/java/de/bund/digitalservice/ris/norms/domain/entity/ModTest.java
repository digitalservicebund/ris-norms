package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModTest {

  private static final String COMMON_XML =
      """
                        <akn:mod eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"
                              GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"
                              refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1"
                                 GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206"
                                 href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml">§ 20 Absatz 1 Satz 2</akn:ref> wird
                        die Angabe <akn:quotedText eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-1"
                                        GUID="694459c4-ef66-4f87-bb78-a332054a2216"
                                        startQuote="„"
                                        endQuote="“">§ 9 Abs. 1 Satz 2, Abs. 2</akn:quotedText> durch die
                        Wörter <akn:quotedText eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-2"
                                        GUID="dd25bdb6-4ef4-4ef5-808c-27579b6ae196"
                                        startQuote="„"
                                        endQuote="“">§ 9 Absatz 1 Satz 2, Absatz 2 oder 3</akn:quotedText>
                        ersetzt.</akn:mod>
              """;

  private Mod mod;

  @BeforeEach
  void setUp() {
    mod = Mod.builder().node(XmlMapper.toNode(COMMON_XML)).build();
  }

  @Test
  void getEid() {

    // when
    var eid = mod.getEid();

    // then
    assertThat(eid)
        .contains("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1");
  }

  @Test
  void getOldText() {
    // when
    var oldText = mod.getOldText();

    // then
    assertThat(oldText).contains("§ 9 Abs. 1 Satz 2, Abs. 2");
  }

  @Test
  void getNewText() {
    // when
    var newText = mod.getNewText();

    // then
    assertThat(newText).contains("§ 9 Absatz 1 Satz 2, Absatz 2 oder 3");
  }

  @Test
  void setNewText() {
    // when
    mod.setNewText("new text");
    var eid = mod.getNewText();

    // then
    assertThat(eid).contains("new text");
  }

  @Test
  void getTargetHref() {
    // when
    var eid = mod.getTargetHref();

    // then
    assertThat(eid).isPresent();
    assertThat(eid.get().value())
        .contains(
            "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml");
  }

  @Test
  void getTargetEid() {
    // when
    var eid = mod.getTargetEid();

    // then
    assertThat(eid).contains("para-20_abs-1");
  }

  @Test
  void setTargetHref() {
    // when
    mod.setTargetHref("new-target-href");
    var eid = mod.getTargetHref();

    // then
    assertThat(eid).isPresent();
    assertThat(eid.get().value()).contains("new-target-href");
  }
}
