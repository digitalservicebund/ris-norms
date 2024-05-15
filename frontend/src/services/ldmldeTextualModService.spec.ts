import { xmlStringToDocument } from "@/services/xmlService"
import { describe, expect, it } from "vitest"
import { getForcePeriod } from "@/services/ldmldeTextualModService"

describe("ldmldeTextualModService", () => {
  describe("getForcePeriod", () => {
    it("should find the force period", () => {
      const node = xmlStringToDocument(`
        <akn:textualMod xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="2e5533d3-d0e3-43ba-aa1a-5859d108eb46" eId="meta-1_analysis-1_activemod-1_textualmod-1" type="substitution">
            <akn:source GUID="8b3e1841-5d63-4400-96ae-214f6ee28db6" eId="meta-1_analysis-1_activemod-1_textualmod-1_source-1" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1"/>
            <akn:destination GUID="94c1e417-e849-4269-8320-9f0173b39626" eId="meta-1_analysis-1_activemod-1_textualmod-1_destination-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-6_abs-3/100-126.xml"/><!-- To check-->
            <akn:force GUID="6f5eabe9-1102-4d29-9d25-a44643354519" eId="meta-1_analysis-1_activemod-1_textualmod-1_gelzeitnachw-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
        </akn:textualMod>
      `).childNodes.item(0)

      expect(getForcePeriod(node)).to.eq("meta-1_geltzeiten-1_geltungszeitgr-1")
    })
  })
})
