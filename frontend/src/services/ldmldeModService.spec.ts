import { xmlStringToDocument } from "@/services/xmlService"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { getTimeBoundaryDate, getModEIds } from "@/services/ldmldeModService"

vi.mock("@/lib/auth", () => {
  return {
    useAuthentication: () => ({
      addAuthorizationHeader: (init: HeadersInit) => ({ ...init }),
      tryRefresh: vi.fn().mockReturnValue(true),
    }),
  }
})

describe("ldmldeModService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("getModEIds", () => {
    it("should find the eIds", () => {
      const node = xmlStringToDocument(`
        <akn:article  xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/">
            <akn:mod GUID="148c2f06-6e33-4af8-9f4a-3da67c888510" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen">
                <akn:ref GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3_text-1/100-126.xml">§6 Absatz 3 Satz 5</akn:ref>
            </akn:mod>
            <akn:mod GUID="148c2f06-6e33-4af8-9f4a-3da67c888510" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen"></akn:mod>
        </akn:article>
      `).childNodes.item(0)

      const eIds = getModEIds(node)
      expect(eIds).toHaveLength(2)
      expect(eIds).toContain(
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
      )
      expect(eIds).toContain(
        "hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1",
      )
    })
  })

  describe("getTimeBoundaryDate", () => {
    it("should find the correct date", () => {
      const xml = xmlStringToDocument(`<?xml version="1.0" encoding="UTF-8"?>
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
          <akn:act name="regelungstext" eId="hauptteil-1">
            <akn:meta GUID="7e5837c8-b967-45be-924b-c95956c4aa94" eId="meta-1">
              <akn:lifecycle GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" eId="meta-1_lebzykl-1" source="attributsemantik-noch-undefiniert">
                <akn:eventRef GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29" eId="meta-1_lebzykl-1_ereignis-1" refersTo="ausfertigung" source="attributsemantik-noch-undefiniert" type="generation"/>
                <akn:eventRef GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30" eId="meta-1_lebzykl-1_ereignis-2" refersTo="inkrafttreten" source="attributsemantik-noch-undefiniert" type="generation"/>
              </akn:lifecycle>
              <akn:analysis GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" eId="meta-1_analysis-1" source="attributsemantik-noch-undefiniert">
                 <akn:activeModifications GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3ac" eId="meta-1_analysis-1_activemod-1">
                    <akn:textualMod GUID="2e5533d3-d0e3-43ba-aa1a-5859d108eb46" eId="meta-1_analysis-1_activemod-1_textualmod-1" type="substitution">
                       <akn:source GUID="8b3e1841-5d63-4400-96ae-214f6ee28db6" eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"/>
                       <akn:destination GUID="94c1e417-e849-4269-8320-9f0173b39626" eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_art-6_abs-3/100-126.xml"/><!-- To check-->
                       <akn:force GUID="6f5eabe9-1102-4d29-9d25-a44643354519" eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                    </akn:textualMod>
                 </akn:activeModifications>
              </akn:analysis>
              <akn:temporalData GUID="82854d32-d922-43d7-ac8c-612c07219336" eId="meta-1_geltzeiten-1" source="attributsemantik-noch-undefiniert">
                 <akn:temporalGroup GUID="ac311ee1-33d3-4b9b-a974-776e55a88396" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
                    <akn:timeInterval GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2"/>
                 </akn:temporalGroup>
              </akn:temporalData>
            </akn:meta>
          </akn:act>
        </akn:akomaNtoso>
      `)

      expect(
        getTimeBoundaryDate(
          xml,
          "hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1",
        ),
      ).toEqual({
        date: "2023-12-30",
        temporalGroupEid: "meta-1_geltzeiten-1_geltungszeitgr-1",
      })
    })
  })
})
