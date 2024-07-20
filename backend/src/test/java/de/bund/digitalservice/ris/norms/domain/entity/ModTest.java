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

  private static final String QUOTED_STRUCTURE_MOD =
      """
              <akn:mod GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen"> Der
                <akn:ref GUID="4400b9ef-c992-49fe-9bb5-30bfd4519e5d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1" href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/einleitung-1_doktitel-1.xml">Titel</akn:ref> des Gesetzes wird ersetzt durch:
                <akn:quotedStructure GUID="9cb0572a-2933-473e-823f-5541ab360561" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1" endQuote="“" startQuote="„">
                  <akn:longTitle GUID="0505f7b3-54c8-4c9d-b456-cd84adfb98f1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1">
                    <akn:p GUID="6ad3f708-b3be-4dbf-b149-a61e72678105" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1_text-1">
                      <akn:docTitle GUID="ab481c1a-db58-4b6a-886c-1e9301952c34" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1_text-1_doctitel-1">Fiktives Beispielgesetz für das Ersetzen von Strukturen und Gliederungseinheiten mit Änderungsbefehlen</akn:docTitle>
                      <akn:shortTitle GUID="820e7af3-fd8c-4409-949a-1e40ec2cc8e6" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1_text-1_kurztitel-1"> (Strukturänderungsgesetz) </akn:shortTitle>
                    </akn:p>
                  </akn:longTitle>
                </akn:quotedStructure>
              </akn:mod>
        """;

  private Mod mod;
  private Mod refMod;

  @BeforeEach
  void setUp() {

    mod = Mod.builder().node(XmlMapper.toNode(COMMON_XML)).build();
    refMod = Mod.builder().node(XmlMapper.toNode(QUOTED_STRUCTURE_MOD)).build();
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
    var newContent = mod.getNewText();

    // then
    assertThat(newContent).contains("§ 9 Absatz 1 Satz 2, Absatz 2 oder 3");
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
  void setTargetHref() {
    // when
    mod.setTargetHref("new-target-href");
    var eid = mod.getTargetHref();

    // then
    assertThat(eid).isPresent();
    assertThat(eid.get().value()).contains("new-target-href");
  }

  @Test
  void usesQuotedText() {
    // when
    var isQuoted = mod.usesQuotedText();

    // then
    assertThat(isQuoted).isTrue();
  }

  @Test
  void usesQuotedStructure() {
    // when
    var isStructure = refMod.usesQuotedStructure();

    // then
    assertThat(isStructure).isTrue();
  }

  @Test
  void hasRangeRef() {
    String rangeRefString =
        """
              <akn:mod GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen"> Der
                <akn:rref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-1.xml" upTo="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-4.xml">§ 9 Absatz 1 bis 4</akn:rref> des Gesetzes wird ersetzt durch:
              </akn:mod>
        """;
    refMod = Mod.builder().node(XmlMapper.toNode(rangeRefString)).build();

    // when
    var result = refMod.hasRangeRef();

    // then
    assertThat(result).isTrue();
  }

  @Test
  void hasRangeRefFalse() {
    String rangeRefString =
        """
            <akn:mod GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen"> Der
              <akn:ref GUID="4400b9ef-c992-49fe-9bb5-30bfd4519e5d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1" href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/einleitung-1_doktitel-1.xml">Titel</akn:ref> des Gesetzes wird ersetzt
            </akn:mod>
        """;
    refMod = Mod.builder().node(XmlMapper.toNode(rangeRefString)).build();

    // when
    var result = refMod.hasRangeRef();

    // then
    assertThat(result).isFalse();
  }

  @Test
  void hasRef() {
    String refString =
        """
              <akn:mod GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen"> Der
                <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-1.xml">§ 9 Absatz 1 bis 4</akn:ref> des Gesetzes wird ersetzt durch:
              </akn:mod>
        """;
    refMod = Mod.builder().node(XmlMapper.toNode(refString)).build();

    // when
    var result = refMod.hasRef();

    // then
    assertThat(result).isTrue();
  }

  @Test
  void getRangeRef() {
    String rangeRefString =
        """
              <akn:mod GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen"> Der
                <akn:rref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-1.xml" upTo="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-4.xml">§ 9 Absatz 1 bis 4</akn:rref> des Gesetzes wird ersetzt durch:
              </akn:mod>
        """;
    refMod = Mod.builder().node(XmlMapper.toNode(rangeRefString)).build();

    // when
    var result = refMod.getRangeRef();

    // then
    assertThat(result).isPresent();
    assertThat(result.get().getAttributes().getNamedItem("upTo").getNodeValue())
        .isEqualTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-4.xml");
  }

  @Test
  void getRef() {
    String rangeRefString =
        """
              <akn:mod GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen"> Der
                <akn:ref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-1.xml">§ 9 Absatz 1 bis 4</akn:ref> des Gesetzes wird ersetzt durch:
              </akn:mod>
        """;
    refMod = Mod.builder().node(XmlMapper.toNode(rangeRefString)).build();

    // when
    var result = refMod.getRef();

    // then
    assertThat(result).isPresent();
    assertThat(result.get().getAttributes().getNamedItem("href").getNodeValue())
        .isEqualTo("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-1.xml");
  }

  @Test
  void setRangeRef() {
    String rangeRefString =
        """
              <akn:mod GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen"> Der
                <akn:rref href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-1.xml" upTo="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-4.xml">§ 9 Absatz 1 bis 4</akn:rref> des Gesetzes wird ersetzt durch:
              </akn:mod>
        """;
    refMod = Mod.builder().node(XmlMapper.toNode(rangeRefString)).build();
    String updated = "updated";

    // when
    refMod.setRangeRefUpTo(updated);

    var result = refMod.getRangeRef();

    // then
    assertThat(result).isPresent();
    assertThat(result.get().getAttributes().getNamedItem("upTo").getNodeValue()).isEqualTo(updated);
  }

  @Test
  void convertRefToRangeRef() {
    // TODO make it work
    //    String rangeRefString = """
    //              <akn:mod GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1"
    // eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"
    // refersTo="aenderungsbefehl-ersetzen"> Der
    //                <akn:ref
    // href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-1.xml">§ 9 Absatz
    // 1 bis 4</akn:ref> des Gesetzes wird ersetzt durch:
    //              </akn:mod>
    //        """;
    //    refMod = Mod.builder().node(XmlMapper.toNode(rangeRefString)).build();
    //    String updated = "updated";
    //
    //    // when
    //    refMod.convertRefToRangeRef(updated);
    //
    //    var result = refMod.getRangeRef();
    //
    //    // then
    //    assertThat(result).isPresent();
    //    assertThat(result.get().getNodeName()).isEqualTo("akn:rref");
    //
    // assertThat(result.get().getAttributes().getNamedItem("upTo").getNodeValue()).isEqualTo(updated);
  }

  @Test
  void setOldText() {
    // given
    var oldText = mod.getOldText();
    assertThat(oldText).contains("§ 9 Abs. 1 Satz 2, Abs. 2");

    // when
    mod.setOldText("new old text");

    // then
    var updatedText = mod.getOldText();
    assertThat(updatedText).contains("new old text");
  }
}
