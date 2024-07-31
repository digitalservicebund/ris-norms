import { describe, expect, test } from "vitest"
import { createNewRefElement, deleteRef, getNextRefEId } from "@/lib/ref"
import { xmlNodeToString, xmlStringToDocument } from "@/services/xmlService"
import { getNodeByEid } from "@/services/ldmldeService"

describe("getNextRefEId", () => {
  test("creates the correct eId if no ref exists", () => {
    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1"></akn:quotedText>`,
    )

    const result = getNextRefEId(ldmlDocument.firstChild!)

    expect(result).toEqual("quot-1_ref-1")
  })

  test("creates the correct eId if a ref already exists", () => {
    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1">
                    <akn:ref eId="quot-1_ref-1">Test</akn:ref>
                </akn:quotedText>`,
    )

    const result = getNextRefEId(ldmlDocument.firstChild!)

    expect(result).toEqual("quot-1_ref-2")
  })

  test("creates the correct eId if the parent is a text node and it's parent has an eId", () => {
    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1">
                    <akn:ref eId="quot-1_ref-1">Test</akn:ref>
                </akn:quotedText>`,
    )

    const result = getNextRefEId(ldmlDocument.firstChild!.firstChild!)

    expect(result).toEqual("quot-1_ref-2")
  })
})

describe("createNewRefElement", () => {
  test("creates a akn:ref element with an eId and GUID", () => {
    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1">
                </akn:quotedText>`,
    )

    const result = createNewRefElement(ldmlDocument.firstChild!)

    expect(result?.nodeName).toEqual("ref")
    expect(result?.getAttribute("eId")).toEqual("quot-1_ref-1")
    expect(result?.getAttribute("GUID")).toHaveLength(36)
  })
})

describe("deleteRef", () => {
  test("deletes a akn:ref element", () => {
    const ldmlDocument = xmlStringToDocument(
      "<akn:quotedText xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.6/\" eId='quot-1'>Render of <akn:ref eId='quot-1_ref-1'>a ref</akn:ref> and <akn:ref eId='quot-1_ref-2'>a second ref</akn:ref></akn:quotedText>",
    )

    const ref = getNodeByEid(ldmlDocument, "quot-1_ref-2")
    expect(ref).exist

    deleteRef(ref!)

    expect(xmlNodeToString(ldmlDocument)).toEqual(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1">Render of <akn:ref eId="quot-1_ref-1">a ref</akn:ref> and a second ref</akn:quotedText>`,
    )
  })
})
