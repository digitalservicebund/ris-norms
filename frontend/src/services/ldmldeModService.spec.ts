import { xmlStringToDocument } from "@/services/xmlService"
import { describe, expect, it } from "vitest"
import {
  getChangeType,
  getDestinationHref,
  getNewText,
  getOldText,
} from "@/services/ldmldeModService"

describe("ldmldeModService", () => {
  describe("getNewText", () => {
    it("should find new text", () => {
      const node = xmlStringToDocument(`
        <akn:mod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="148c2f06-6e33-4af8-9f4a-3da67c888510" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen">
          In
          <akn:ref GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1/100-126.xml">§6 Absatz 3 Satz 5</akn:ref>
          werden die Wörter
          <akn:quotedText GUID="694459c4-ef66-4f87-bb78-a332054a2216" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-1" endQuote="“" startQuote="„">am Ende des Kalenderjahres, das dem Jahr der Protokollierung folgt,</akn:quotedText>
          durch die Wörter
          <akn:quotedText GUID="dd25bdb6-4ef4-4ef5-808c-27579b6ae196" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-2" endQuote="“" startQuote="„">nach Ablauf von fünf Jahren</akn:quotedText>
          ersetzt.
        </akn:mod>
      `).childNodes.item(0)

      expect(getNewText(node)).to.eq("nach Ablauf von fünf Jahren")
    })
  })

  describe("getOldText", () => {
    it("should find old text", () => {
      const node = xmlStringToDocument(`
        <akn:mod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="148c2f06-6e33-4af8-9f4a-3da67c888510" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen">
          In
          <akn:ref GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1/100-126.xml">§6 Absatz 3 Satz 5</akn:ref>
          werden die Wörter
          <akn:quotedText GUID="694459c4-ef66-4f87-bb78-a332054a2216" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-1" endQuote="“" startQuote="„">am Ende des Kalenderjahres, das dem Jahr der Protokollierung folgt,</akn:quotedText>
          durch die Wörter
          <akn:quotedText GUID="dd25bdb6-4ef4-4ef5-808c-27579b6ae196" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-2" endQuote="“" startQuote="„">nach Ablauf von fünf Jahren</akn:quotedText>
          ersetzt.
        </akn:mod>
      `).childNodes.item(0)

      expect(getOldText(node)).to.eq(
        "am Ende des Kalenderjahres, das dem Jahr der Protokollierung folgt,",
      )
    })
  })

  describe("getDestinationHref", () => {
    it("should find the correct href", () => {
      const node = xmlStringToDocument(`
        <akn:mod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="148c2f06-6e33-4af8-9f4a-3da67c888510" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen">
          In
          <akn:ref GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1/100-126.xml">§6 Absatz 3 Satz 5</akn:ref>
          werden die Wörter
          <akn:quotedText GUID="694459c4-ef66-4f87-bb78-a332054a2216" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-1" endQuote="“" startQuote="„">am Ende des Kalenderjahres, das dem Jahr der Protokollierung folgt,</akn:quotedText>
          durch die Wörter
          <akn:quotedText GUID="dd25bdb6-4ef4-4ef5-808c-27579b6ae196" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-2" endQuote="“" startQuote="„">nach Ablauf von fünf Jahren</akn:quotedText>
          ersetzt.
        </akn:mod>
      `).childNodes.item(0)

      expect(getDestinationHref(node)).to.eq(
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1/100-126.xml",
      )
    })
  })

  describe("getChangeType", () => {
    it("should find the correct href", () => {
      const node = xmlStringToDocument(`
        <akn:mod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="148c2f06-6e33-4af8-9f4a-3da67c888510" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen">
          In
          <akn:ref GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1/100-126.xml">§6 Absatz 3 Satz 5</akn:ref>
          werden die Wörter
          <akn:quotedText GUID="694459c4-ef66-4f87-bb78-a332054a2216" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-1" endQuote="“" startQuote="„">am Ende des Kalenderjahres, das dem Jahr der Protokollierung folgt,</akn:quotedText>
          durch die Wörter
          <akn:quotedText GUID="dd25bdb6-4ef4-4ef5-808c-27579b6ae196" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_quottext-2" endQuote="“" startQuote="„">nach Ablauf von fünf Jahren</akn:quotedText>
          ersetzt.
        </akn:mod>
      `).childNodes.item(0)

      expect(getChangeType(node)).to.eq("aenderungsbefehl-ersetzen")
    })
  })
})
