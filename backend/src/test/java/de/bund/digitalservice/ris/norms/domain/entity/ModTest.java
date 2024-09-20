package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;

class ModTest {

  private static final String QUOTED_TEXT_MOD =
    """
        <akn:mod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"
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

  private static final String QUOTED_STRUCTURE_REF_MOD =
    """
              <akn:mod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen"> Der
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

  private static final String QUOTED_STRUCTURE_RREF_MOD =
    """
              <akn:mod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen"> Der
            <akn:rref GUID="4400b9ef-c992-49fe-9bb5-30bfd4519e5d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1" from="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/einleitung-1_doktitel-1.xml" upTo="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/einleitung-1_doktitel-3.xml">Titel</akn:rref> des Gesetzes wird ersetzt durch:
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

  private Mod quotedTextMod;
  private Mod quotedStructureRefMod;
  private Mod quotedStructureRrefMod;

  @BeforeEach
  void setUp() {
    quotedTextMod = Mod.builder().node(XmlMapper.toNode(QUOTED_TEXT_MOD)).build();
    quotedStructureRefMod = Mod.builder().node(XmlMapper.toNode(QUOTED_STRUCTURE_REF_MOD)).build();
    quotedStructureRrefMod =
    Mod.builder().node(XmlMapper.toNode(QUOTED_STRUCTURE_RREF_MOD)).build();
  }

  @Test
  void getEid() {
    // when
    var eid = quotedTextMod.getEid();

    // then
    assertThat(eid)
      .contains("hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1");
  }

  @Test
  void getOldText() {
    // when
    var oldText = quotedTextMod.getOldText();

    // then
    assertThat(oldText).contains("§ 9 Abs. 1 Satz 2, Abs. 2");
  }

  @Test
  void getNewText() {
    // when
    var newContent = quotedTextMod.getNewText();

    // then
    assertThat(newContent).contains("§ 9 Absatz 1 Satz 2, Absatz 2 oder 3");
  }

  @Test
  void setNewText() {
    final String QUOTED_TEXT_MOD_WITH_REF =
      """
          <akn:mod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"
            GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"
            refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1"
               GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206"
               href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml">§ 20 Absatz 1 Satz 2</akn:ref> wird
      die Angabe <akn:quotedText eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-1"
                      GUID="694459c4-ef66-4f87-bb78-a332054a2216"
                      startQuote="„"
                      endQuote="“">§ 9 Abs. 1 Satz 2, Abs. 2</akn:quotedText> durch die
      Wörter <akn:quotedText GUID="c43a085c-536b-457c-b74f-3af9b137b62b" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-2" endQuote="“" startQuote="„">
      <akn:ref GUID="83ea9cc4-a1b0-4b6b-8e57-49b00143f1e2" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-2_ref-1" href="some/crazy/eli">1. Beispiel</akn:ref>
      </akn:quotedText>
      ersetzt.</akn:mod>
      """;

    Mod quotedTextModWithRef = Mod
      .builder()
      .node(XmlMapper.toNode(QUOTED_TEXT_MOD_WITH_REF))
      .build();
    // when
    quotedTextModWithRef.setNewText("new text");
    var newText = quotedTextModWithRef.getNewText();

    // then
    assertThat(newText).contains("new text");
  }

  @Test
  void setNewTextWithGuessedReference() {
    final String QUOTED_TEXT_MOD_WITH_REF =
      """
          <akn:mod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"
            GUID="148c2f06-6e33-4af8-9f4a-3da67c888510"
            refersTo="aenderungsbefehl-ersetzen">In <akn:ref eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_ref-1"
               GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206"
               href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml">§ 20 Absatz 1 Satz 2</akn:ref> wird
      die Angabe <akn:quotedText eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-1"
                      GUID="694459c4-ef66-4f87-bb78-a332054a2216"
                      startQuote="„"
                      endQuote="“">§ 9 Abs. 1 Satz 2, Abs. 2</akn:quotedText> durch die
      Wörter <akn:quotedText GUID="c43a085c-536b-457c-b74f-3af9b137b62b" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-2" endQuote="“" startQuote="„">
      1. <akn:ref GUID="83ea9cc4-a1b0-4b6b-8e57-49b00143f1e2" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-2_ref-1" href="some/crazy/eli">Beispiel</akn:ref>
      </akn:quotedText>
      ersetzt.</akn:mod>
      """;

    Mod quotedTextModWithRef = Mod
      .builder()
      .node(XmlMapper.toNode(QUOTED_TEXT_MOD_WITH_REF))
      .build();
    // when
    quotedTextModWithRef.setNewText("new text");
    var newText = quotedTextModWithRef.getNewText();
    Node ref = quotedTextModWithRef.getSecondQuotedText().get().getFirstChild();

    // then
    assertThat(newText).contains("new text");
    assertThat(ref).isNotNull();
    assertThat(ref.getNodeName()).isEqualTo("akn:ref");
  }

  @Test
  void getTargetRefHref() {
    // when
    var eid = quotedTextMod.getTargetRefHref();

    // then
    assertThat(eid).isPresent();
    assertThat(eid.get().value())
      .contains(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"
      );
  }

  @Test
  void setTargetRefHref() {
    // when
    quotedTextMod.setTargetRefHref("new-target-href");
    var eid = quotedTextMod.getTargetRefHref();

    // then
    assertThat(eid).isPresent();
    assertThat(eid.get().value()).contains("new-target-href");
  }

  @Test
  void getTargetRrefFrom() {
    // when
    var eid = quotedStructureRrefMod.getTargetRrefFrom();

    // then
    assertThat(eid).isPresent();
    assertThat(eid.get().value())
      .contains(
        "eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/einleitung-1_doktitel-1.xml"
      );
  }

  @Test
  void setTargetRrefFrom() {
    // when
    quotedStructureRrefMod.setTargetRrefFrom("new-target-href");
    var eid = quotedStructureRrefMod.getTargetRrefFrom();

    // then
    assertThat(eid).isPresent();
    assertThat(eid.get().value()).contains("new-target-href");
  }

  @Test
  void getTargetRrefUpTo() {
    // when
    var eid = quotedStructureRrefMod.getTargetRrefUpTo();

    // then
    assertThat(eid).isPresent();
    assertThat(eid.get().value())
      .contains(
        "eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/einleitung-1_doktitel-3.xml"
      );
  }

  @Test
  void setTargetRrefUpTo() {
    // when
    quotedStructureRrefMod.setTargetRrefUpTo("new-target-upTo");
    var eid = quotedStructureRrefMod.getTargetRrefUpTo();

    // then
    assertThat(eid).isPresent();
    assertThat(eid.get().value()).contains("new-target-upTo");
  }

  @Test
  void usesQuotedText() {
    // when
    var isQuoted = quotedTextMod.usesQuotedText();

    // then
    assertThat(isQuoted).isTrue();
  }

  @Test
  void getSecondQuotedText() {
    // when
    final Optional<Node> secondQuotedText = quotedTextMod.getSecondQuotedText();

    // then
    assertThat(secondQuotedText).isPresent();
    assertThat(secondQuotedText.get().getAttributes().getNamedItem("GUID").getNodeValue())
      .isEqualTo("dd25bdb6-4ef4-4ef5-808c-27579b6ae196");
    assertThat(secondQuotedText.get().getAttributes().getNamedItem("eId").getNodeValue())
      .isEqualTo(
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1_quottext-2"
      );
    assertThat(secondQuotedText.get().getTextContent())
      .isEqualTo("§ 9 Absatz 1 Satz 2, Absatz 2 oder 3");
  }

  @Test
  void usesQuotedStructure() {
    // when
    var isStructure = quotedStructureRefMod.usesQuotedStructure();

    // then
    assertThat(isStructure).isTrue();
  }

  @Test
  void getQuotedStructure() {
    // when
    final Optional<Node> quotedStructure = quotedStructureRefMod.getQuotedStructure();

    // then
    assertThat(quotedStructure).isPresent();
    assertThat(quotedStructure.get().getAttributes().getNamedItem("GUID").getNodeValue())
      .isEqualTo("9cb0572a-2933-473e-823f-5541ab360561");
    assertThat(quotedStructure.get().getAttributes().getNamedItem("eId").getNodeValue())
      .isEqualTo(
        "hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1"
      );
    assertThat(quotedStructure.get().getChildNodes().getLength()).isGreaterThan(1);
  }

  @Test
  void quotedTextDoesNotContainRef() {
    assertThat(quotedTextMod.containsRef()).isFalse();
  }

  @Test
  void quotedTextContainsRef() {
    final Mod mod = Mod
      .builder()
      .node(
        XmlMapper.toNode(
          """
               <akn:mod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"
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
                          endQuote="“"><akn:ref>§ 9 Absatz 1 Satz 2, Absatz 2 oder 3</akn:ref></akn:quotedText>
          ersetzt.</akn:mod>
          """
        )
      )
      .build();

    assertThat(mod.containsRef()).isTrue();
  }

  @Test
  void quotedStructureDoesNotContainRef() {
    assertThat(quotedStructureRefMod.containsRef()).isFalse();
  }

  @Test
  void quotedStructureContainsRef() {
    final Mod mod = Mod
      .builder()
      .node(
        XmlMapper.toNode(
          """
              <akn:mod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen"> Der
            <akn:ref GUID="4400b9ef-c992-49fe-9bb5-30bfd4519e5d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1" href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/einleitung-1_doktitel-1.xml">Titel</akn:ref> des Gesetzes wird ersetzt durch:
            <akn:quotedStructure GUID="9cb0572a-2933-473e-823f-5541ab360561" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1" endQuote="“" startQuote="„">
              <akn:longTitle GUID="0505f7b3-54c8-4c9d-b456-cd84adfb98f1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1">
                <akn:p GUID="6ad3f708-b3be-4dbf-b149-a61e72678105" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1_text-1">
                  <akn:docTitle GUID="ab481c1a-db58-4b6a-886c-1e9301952c34" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1_text-1_doctitel-1">Fiktives <akn:ref>Beispielgesetz</akn:ref> für das Ersetzen von Strukturen und Gliederungseinheiten mit Änderungsbefehlen</akn:docTitle>
                  <akn:shortTitle GUID="820e7af3-fd8c-4409-949a-1e40ec2cc8e6" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quotstruct-1_doktitel-1_text-1_kurztitel-1"> (Strukturänderungsgesetz) </akn:shortTitle>
                </akn:p>
              </akn:longTitle>
            </akn:quotedStructure>
          </akn:mod>
          """
        )
      )
      .build();

    assertThat(mod.containsRef()).isTrue();
  }

  @Test
  void hasRref() {
    String rangeRefString =
      """
                    <akn:mod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen"> Der
              <akn:rref from="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-1.xml" upTo="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-9_abs-4.xml">§ 9 Absatz 1 bis 4</akn:rref> des Gesetzes wird ersetzt durch:
            </akn:mod>
      """;
    quotedStructureRefMod = Mod.builder().node(XmlMapper.toNode(rangeRefString)).build();

    // when
    var result = quotedStructureRefMod.hasRref();

    // then
    assertThat(result).isTrue();
  }

  @Test
  void hasRrefFalse() {
    String rangeRefString =
      """
                  <akn:mod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="5597b2ca-bc99-42d7-a362-faced3cad1c1" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen"> Der
            <akn:ref GUID="4400b9ef-c992-49fe-9bb5-30bfd4519e5d" eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1" href="eli/bund/bgbl-1/1002/1/1002-01-01/1/deu/regelungstext-1/einleitung-1_doktitel-1.xml">Titel</akn:ref> des Gesetzes wird ersetzt
          </akn:mod>
      """;
    quotedStructureRefMod = Mod.builder().node(XmlMapper.toNode(rangeRefString)).build();

    // when
    var result = quotedStructureRefMod.hasRref();

    // then
    assertThat(result).isFalse();
  }

  @Test
  void setOldText() {
    // given
    var oldText = quotedTextMod.getOldText();
    assertThat(oldText).contains("§ 9 Abs. 1 Satz 2, Abs. 2");

    // when
    quotedTextMod.setOldText("new old text");

    // then
    var updatedText = quotedTextMod.getOldText();
    assertThat(updatedText).contains("new old text");
  }

  @Test
  void replaceRefWithRref() {
    quotedStructureRefMod.replaceRefWithRref("new-destination-href", "new-destination-upto");

    final Optional<Href> targetRefHref = quotedStructureRefMod.getTargetRefHref();
    assertThat(targetRefHref).isEmpty();

    final Optional<Href> targetRrefHref = quotedStructureRefMod.getTargetRrefFrom();
    assertThat(targetRrefHref).isPresent();
    assertThat(targetRrefHref.get().value()).isEqualTo("new-destination-href");

    final Optional<Href> targetRrefUpTo = quotedStructureRefMod.getTargetRrefUpTo();
    assertThat(targetRrefUpTo).isPresent();
    assertThat(targetRrefUpTo.get().value()).isEqualTo("new-destination-upto");
  }

  @Test
  void replaceRrefWithRef() {
    quotedStructureRrefMod.replaceRrefWithRef("new-destination-href");

    final Optional<Href> targetRrefHref = quotedStructureRrefMod.getTargetRrefFrom();
    assertThat(targetRrefHref).isEmpty();

    final Optional<Href> targetRefHref = quotedStructureRrefMod.getTargetRefHref();
    assertThat(targetRefHref).isPresent();
    assertThat(targetRefHref.get().value()).isEqualTo("new-destination-href");
  }
}
