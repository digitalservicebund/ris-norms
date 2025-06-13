import { describe, expect, it, vi } from "vitest"
import {
  evaluateXPath,
  evaluateXPathOnce,
  xmlNodeToString,
  xmlStringToDocument,
} from "@/lib/xml"

// the evaluateXPathOnce and evaluateXPath methods are mocked in the vitest-setup, to test it here we unmock it.
vi.unmock("@/lib/xml")

describe("xml", () => {
  describe("xmlStringToDocument", () => {
    it("should parse xml string", () => {
      const document =
        xmlStringToDocument(`<?xml version="1.0" encoding="UTF-8"?>
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/">
          <akn:act name="regelungstext">

          </akn:act>
        </akn:akomaNtoso>
      `)

      expect(document.getRootNode().childNodes.length).toBe(2)
      expect(document.getRootNode().childNodes.item(1).nodeName).toBe(
        "akn:akomaNtoso",
      )
    })
  })

  describe("xmlDocumentToString", () => {
    it("should recreate an equal document after converting it to a string and back to a document", () => {
      const document =
        xmlStringToDocument(`<?xml version="1.0" encoding="UTF-8"?>
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/">
          <akn:act name="regelungstext">

          </akn:act>
        </akn:akomaNtoso>
      `)
      const result = xmlNodeToString(document)
      const document2 = xmlStringToDocument(result)

      expect(document2.isEqualNode(document)).toBe(true)
    })
  })

  describe("evaluateXPathOnce", () => {
    it("should evaluate a xpath", () => {
      const document =
        xmlStringToDocument(`<?xml version="1.0" encoding="UTF-8"?>
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/">
          <akn:act name="regelungstext">
          </akn:act>
        </akn:akomaNtoso>
      `)

      const result = evaluateXPathOnce("//akn:act/@name", document)
      expect(result?.nodeValue).toBe("regelungstext")
    })
  })

  describe("evaluateXPath", () => {
    it("should evaluate a xpath", () => {
      const document =
        xmlStringToDocument(`<?xml version="1.0" encoding="UTF-8"?>
        <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
        <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/">
          <akn:act name="regelungstext">
          </akn:act>
          <akn:act name="regelungstext-verkuendung-2">
          </akn:act>
        </akn:akomaNtoso>
      `)

      const result = evaluateXPath("//akn:act/@name", document)
      expect(result).toHaveLength(2)
      expect(result[0].nodeValue).toBe("regelungstext")
      expect(result[1].nodeValue).toBe("regelungstext-verkuendung-2")
    })
  })
})
