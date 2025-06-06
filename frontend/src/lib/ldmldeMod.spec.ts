import { xmlStringToDocument } from "@/lib/xml"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { getModEIds } from "@/lib/ldmldeMod"

describe("ldmldeMod", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("getModEIds", () => {
    it("should find the eIds", () => {
      const node = xmlStringToDocument(`
        <akn:article  xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8/">
            <akn:mod GUID="148c2f06-6e33-4af8-9f4a-3da67c888510" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1" refersTo="aenderungsbefehl-ersetzen">
                <akn:ref GUID="61d3036a-d7d9-4fa5-b181-c3345caa3206" eId="hauptteil-1_art-1_abs-1_untergl-1_listenelem-1_inhalt-1_text-1_ändbefehl-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1/hauptteil-1_abschnitt-erster_art-6_abs-3_inhalt-3_text-1/100-126.xml">§6 Absatz 3 Satz 5</akn:ref>
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
})
