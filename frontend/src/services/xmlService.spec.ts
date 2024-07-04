import { describe, expect, it, vi } from "vitest"
import {
  evaluateXPath,
  evaluateXPathOnce,
  xmlNodeToString,
  xmlStringToDocument,
} from "@/services/xmlService"

// the evaluateXPathOnce and evaluateXPath methods are mocked in the vitest-setup, to test it here we unmock it.
vi.unmock("@/services/xmlService")

describe("xmlService", () => {
  describe("xmlStringToDocument", () => {
    it("should parse xml string", () => {
      const document =
        xmlStringToDocument(`<?xml version="1.0" encoding="UTF-8"?>
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
          <akn:act name="regelungstext">

          </akn:act>
        </akn:akomaNtoso>
      `)

      expect(document.getRootNode().childNodes.length).to.eq(2)
      expect(document.getRootNode().childNodes.item(1).nodeName).eq(
        "akn:akomaNtoso",
      )
    })
  })

  describe("xmlDocumentToString", () => {
    it("should recreate an equal document after converting it to a string and back to a document", () => {
      const document =
        xmlStringToDocument(`<?xml version="1.0" encoding="UTF-8"?>
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
          <akn:act name="regelungstext">

          </akn:act>
        </akn:akomaNtoso>
      `)
      const result = xmlNodeToString(document)
      const document2 = xmlStringToDocument(result)

      expect(document2.isEqualNode(document)).to.be.true
    })
  })

  describe("evaluateXPathOnce", () => {
    it("should evaluate a xpath", () => {
      const document =
        xmlStringToDocument(`<?xml version="1.0" encoding="UTF-8"?>
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
          <akn:act name="regelungstext">
          </akn:act>
        </akn:akomaNtoso>
      `)

      const result = evaluateXPathOnce("//akn:act/@name", document)
      expect(result?.nodeValue).to.eq("regelungstext")
    })
  })

  describe("evaluateXPath", () => {
    it("should evaluate a xpath", () => {
      const document =
        xmlStringToDocument(`<?xml version="1.0" encoding="UTF-8"?>
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd                        http://Inhaltsdaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-regelungstextverkuendungsfassung.xsd">
          <akn:act name="regelungstext">
          </akn:act>
          <akn:act name="regelungstext-2">
          </akn:act>
        </akn:akomaNtoso>
      `)

      const result = evaluateXPath("//akn:act/@name", document)
      expect(result).toHaveLength(2)
      expect(result[0].nodeValue).to.eq("regelungstext")
      expect(result[1].nodeValue).to.eq("regelungstext-2")
    })
  })
})
