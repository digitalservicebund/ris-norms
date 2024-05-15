import { xmlStringToDocument } from "@/services/xmlService"
import { describe, expect, it } from "vitest"
import { getStartEventRefEid } from "@/services/ldmldeTemporalGroupService"

describe("ldmldeTemporalGroupModService", () => {
  describe("getStartEventRefEid", () => {
    it("should find the eid of the event ref of the start of the period", () => {
      const node = xmlStringToDocument(`
        <akn:temporalGroup xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="ac311ee1-33d3-4b9b-a974-776e55a88396" eId="meta-1_geltzeiten-1_geltungszeitgr-1">
            <akn:timeInterval GUID="ca9f53aa-d374-4bec-aca3-fff4e3485179" eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" refersTo="geltungszeit" start="#meta-1_lebzykl-1_ereignis-2"/>
        </akn:temporalGroup>
      `).childNodes.item(0)

      expect(getStartEventRefEid(node)).to.eq("meta-1_lebzykl-1_ereignis-2")
    })
  })
})
