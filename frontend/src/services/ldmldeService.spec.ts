import { evaluateXPath, xmlStringToDocument } from "@/services/xmlService"
import { describe, expect, it } from "vitest"
import {
  getActiveModificationByModEid,
  getNodeByEid,
} from "@/services/ldmldeService"

describe("ldmldeService", () => {
  describe("getNodeByEid", () => {
    it("should parse xml string", () => {
      const document =
        xmlStringToDocument(`<?xml version="1.0" encoding="UTF-8"?>
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
          <akn:act name="regelungstext" eId="hauptteil-1">
            <akn:body GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8" eId="hauptteil-1">
              <akn:article GUID="cdbfc728-a070-42d9-ba2f-357945afef06" eId="hauptteil-1_art-1" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                <akn:num GUID="25a9acae-7463-4490-bc3f-8258b629d7e9" eId="hauptteil-1_art-1_bezeichnung-1">
                   <akn:marker GUID="81c9c481-9427-4f03-9f51-099aa9b2201e" eId="hauptteil-1_art-1_bezeichnung-1_zaehlbez-1" name="1"/>Artikel 1</akn:num>
                <akn:heading GUID="92827aa8-8118-4207-9f93-589345f0bab6" eId="hauptteil-1_art-1_überschrift-1">Änderung des
                   Bundesverfassungsschutzgesetzes</akn:heading>
              </akn:article>
            </akn:body>
          </akn:act>
        </akn:akomaNtoso>
      `)

      const node = getNodeByEid(document, "hauptteil-1_art-1_bezeichnung-1")
      expect(node?.textContent?.trim()).to.eq("Artikel 1")
    })
  })

  describe("getActiveModificationByModEid", () => {
    it("should parse xml string", () => {
      const document =
        xmlStringToDocument(`<?xml version="1.0" encoding="UTF-8"?>
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
          <akn:act name="regelungstext" eId="hauptteil-1">
            <akn:meta GUID="7e5837c8-b967-45be-924b-c95956c4aa94" eId="meta-1">
              <akn:analysis GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" eId="meta-1_analysis-1" source="attributsemantik-noch-undefiniert">
                <akn:activeModifications GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3ac" eId="meta-1_analysis-1_activemod-1">
                   <akn:textualMod GUID="2e5533d3-d0e3-43ba-aa1a-5859d108eb46" eId="meta-1_analysis-1_activemod-1_textualmod-1" type="substitution">
                      <akn:source GUID="8b3e1841-5d63-4400-96ae-214f6ee28db6" eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"/>
                      <akn:destination GUID="94c1e417-e849-4269-8320-9f0173b39626" eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-6_abs-3/100-126.xml"/><!-- To check-->
                      <akn:force GUID="6f5eabe9-1102-4d29-9d25-a44643354519" eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                   </akn:textualMod>
                </akn:activeModifications>
              </akn:analysis>
            </akn:meta>
            <akn:body GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8" eId="hauptteil-1">
              <akn:article GUID="cdbfc728-a070-42d9-ba2f-357945afef06" eId="hauptteil-1_art-1" period="#geltungszeitgr-1" refersTo="hauptaenderung">
                <akn:paragraph GUID="48ead358-f901-41d3-a135-e372d5ef97b1" eId="hauptteil-1_art-1_abs-1">
                   <akn:list GUID="41675622-ed62-46e3-869f-94d99908b010" eId="hauptteil-1_art-1_abs-1_untergl-1">
                      <akn:point GUID="49983c1a-c952-4ab1-b926-2f414c05da7d" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1">
                         <akn:content GUID="8d3973be-9df6-47fa-b1ea-1cf6273d82e6" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1">
                            <akn:p GUID="c3d0ef20-52d5-4baa-9e1a-b2c63cd21ccc" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1">
                               <akn:mod GUID="148c2f06-6e33-4af8-9f4a-3da67c888510" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen">In <akn:ref GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1/100-126.xml">§
                                  6 Absatz 3 Satz 5</akn:ref>
                                  <!-- How to reference the sentence 5?--> werden die Wörter <akn:quotedText GUID="694459c4-ef66-4f87-bb78-a332054a2216" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-1" endQuote="“" startQuote="„">am Ende des Kalenderjahres, das dem Jahr der Protokollierung folgt,</akn:quotedText> durch die Wörter <akn:quotedText GUID="dd25bdb6-4ef4-4ef5-808c-27579b6ae196" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-2" endQuote="“" startQuote="„">nach Ablauf von fünf Jahren</akn:quotedText>
                                  ersetzt.</akn:mod>
                            </akn:p>
                         </akn:content>
                      </akn:point>
                   </akn:list>
                </akn:paragraph>
              </akn:article>
            </akn:body>
          </akn:act>
        </akn:akomaNtoso>
      `)

      const node = getActiveModificationByModEid(
        document,
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
      )

      expect(node).not.to.be.null
      expect(evaluateXPath("@eId", node!)?.nodeValue).to.eq(
        "meta-1_analysis-1_activemod-1_textualmod-1",
      )
    })
  })
})
