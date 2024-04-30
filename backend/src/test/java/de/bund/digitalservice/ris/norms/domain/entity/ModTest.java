package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class ModTest {

  @Test
  void getEid() {
    // given
    Mod mod =
        Mod.builder()
            .node(
                XmlMapper.toNode(
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
                        """))
            .build();

    // when
    var eid = mod.getEid();

    // then
    assertThat(eid)
        .contains("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1");
  }

  @Test
  void getOldText() {
    // given
    Mod mod =
        Mod.builder()
            .node(
                XmlMapper.toNode(
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
                        """))
            .build();

    // when
    var oldText = mod.getOldText();

    // then
    assertThat(oldText).contains("§ 9 Abs. 1 Satz 2, Abs. 2");
  }

  @Test
  void getNewText() {
    // given
    Mod mod =
        Mod.builder()
            .node(
                XmlMapper.toNode(
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
                        """))
            .build();

    // when
    var newText = mod.getNewText();

    // then
    assertThat(newText).contains("§ 9 Absatz 1 Satz 2, Absatz 2 oder 3");
  }
}
