import { xmlStringToDocument } from "@/services/xmlService"
import { describe, expect, it } from "vitest"
import { getEventRefDate } from "@/services/ldmldeEventRefService"

describe("ldmldeEventRefService", () => {
  describe("getEventRefDate", () => {
    it("should find the eid of the event ref of the start of the period", () => {
      const node = xmlStringToDocument(`
          <akn:eventRef xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30" eId="meta-1_lebzykl-1_ereignis-2" refersTo="inkrafttreten" source="attributsemantik-noch-undefiniert" type="generation"/>
      `).childNodes.item(0)

      expect(getEventRefDate(node)).to.eq("2023-12-30")
    })
  })
})
