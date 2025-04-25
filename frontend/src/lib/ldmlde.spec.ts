import { xmlStringToDocument } from "@/lib/xml"
import { describe, expect, it } from "vitest"
import { getNodeByEid } from "@/lib/ldmlde"

describe("ldmlde", () => {
  describe("getNodeByEid", () => {
    it("should parse xml string", () => {
      const document =
        xmlStringToDocument(`<?xml version="1.0" encoding="UTF-8"?>
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
          <akn:act name="regelungstext" eId="hauptteil-1">
            <akn:body GUID="0B4A8E1F-65EF-4B7C-9E22-E83BA6B73CD8" eId="hauptteil-1">
              <akn:article GUID="cdbfc728-a070-42d9-ba2f-357945afef06" eId="hauptteil-1_art-1" period="#meta-1_geltzeiten-1_geltungszeitgr-1" refersTo="hauptaenderung">
                <akn:num GUID="25a9acae-7463-4490-bc3f-8258b629d7e9" eId="hauptteil-1_art-1_bezeichnung-1">
                   Artikel 1</akn:num>
                <akn:heading GUID="92827aa8-8118-4207-9f93-589345f0bab6" eId="hauptteil-1_art-1_überschrift-1">Änderung des
                   Bundesverfassungsschutzgesetzes</akn:heading>
              </akn:article>
            </akn:body>
          </akn:act>
        </akn:akomaNtoso>
      `)

      const node = getNodeByEid(document, "hauptteil-1_art-1_bezeichnung-1")
      expect(node?.textContent?.trim()).toBe("Artikel 1")
    })
  })
})
