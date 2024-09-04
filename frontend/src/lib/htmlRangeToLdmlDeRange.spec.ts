import { describe, expect, test } from "vitest"
import {
  findHtmlNodeInLdml,
  findLdmlNodeInHtml,
  findOffsetInOtherNode,
  htmlRenderRangeToLdmlDeRange,
  ldmlRangeToHtmlRenderRange,
} from "@/lib/htmlRangeToLdmlDeRange"
import { xmlStringToDocument } from "@/services/xmlService"
import { getNodeByEid } from "@/services/ldmldeService"

describe("findHtmlNodeInLdml", () => {
  test("finds the node (element)", () => {
    const htmlElement = document.createElement("div")
    htmlElement.innerHTML = `<div class="akn-ref" data-eId="quot-1_ref-1">Test</div>`

    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1"><akn:ref eId="quot-1_ref-1">Test</akn:ref></akn:quotedText>`,
    )

    const result = findHtmlNodeInLdml(htmlElement.firstChild!, ldmlDocument)
    expect(result?.textContent).toEqual("Test")
    expect(result?.nodeName).toEqual("akn:ref")
    expect((result as Element).getAttribute("eId")).toEqual("quot-1_ref-1")
  })

  test("finds the node (text node)", () => {
    const htmlElement = document.createElement("div")
    htmlElement.innerHTML = `<div class="akn-ref" data-eId="quot-1_ref-1">Test</div>`

    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1"><akn:ref eId="quot-1_ref-1">Test</akn:ref></akn:quotedText>`,
    )

    const result = findHtmlNodeInLdml(
      htmlElement.firstChild!.firstChild!,
      ldmlDocument,
    )
    expect(result?.textContent).toEqual("Test")
    expect(result?.nodeType).toEqual(Node.TEXT_NODE)
  })

  test("finds nothing if eId does not match (element)", () => {
    const htmlElement = document.createElement("div")
    htmlElement.innerHTML = `<div class="akn-ref" data-eId="quot-1_ref-2">Test</div>`

    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1"><akn:ref eId="quot-1_ref-1">Test</akn:ref></akn:quotedText>`,
    )

    const result = findHtmlNodeInLdml(htmlElement.firstChild!, ldmlDocument)
    expect(result).toBeNull()
  })

  test("finds nothing if eId does not match (text node)", () => {
    const htmlElement = document.createElement("div")
    htmlElement.innerHTML = `<div class="akn-ref" data-eId="quot-1_ref-2">Test</div>`

    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1"><akn:ref eId="quot-1_ref-1">Test</akn:ref></akn:quotedText>`,
    )

    const result = findHtmlNodeInLdml(
      htmlElement.firstChild!.firstChild!,
      ldmlDocument,
    )
    expect(result).toBeNull()
  })
})

describe("findLdmlNodeInHtml", () => {
  test("finds the node (element)", () => {
    const htmlElement = document.createElement("div")
    htmlElement.innerHTML = `<div class="akn-ref" data-eId="quot-1_ref-1">Test</div>`

    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1"><akn:ref eId="quot-1_ref-1">Test</akn:ref></akn:quotedText>`,
    )

    const result = findLdmlNodeInHtml(
      ldmlDocument.firstChild!.firstChild!,
      htmlElement,
    )
    expect(result?.textContent).toEqual("Test")
    expect((result as Element).getAttribute("data-eid")).toEqual("quot-1_ref-1")
  })

  test("finds the node (text node)", () => {
    const htmlElement = document.createElement("div")
    htmlElement.innerHTML = `<div class="akn-ref" data-eId="quot-1_ref-1">Test</div>`

    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1"><akn:ref eId="quot-1_ref-1">Test</akn:ref></akn:quotedText>`,
    )

    const result = findLdmlNodeInHtml(
      ldmlDocument.firstChild!.firstChild!.firstChild!,
      htmlElement,
    )
    expect(result?.textContent).toEqual("Test")
  })

  test("finds nothing if eId does not match (element)", () => {
    const htmlElement = document.createElement("div")
    htmlElement.innerHTML = `<div class="akn-ref" data-eId="quot-1_ref-2">Test</div>`

    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1"><akn:ref eId="quot-1_ref-1">Test</akn:ref></akn:quotedText>`,
    )

    const result = findLdmlNodeInHtml(
      ldmlDocument.firstChild!.firstChild!,
      htmlElement,
    )
    expect(result).toBeNull()
  })

  test("finds nothing if eId does not match (text node)", () => {
    const htmlElement = document.createElement("div")
    htmlElement.innerHTML = `<div class="akn-ref" data-eId="quot-1_ref-2">Test</div>`

    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1"><akn:ref eId="quot-1_ref-1">Test</akn:ref></akn:quotedText>`,
    )

    const result = findLdmlNodeInHtml(
      ldmlDocument.firstChild!.firstChild!.firstChild!,
      htmlElement,
    )
    expect(result).toBeNull()
  })
})

describe("findOffsetInOtherNode", () => {
  const htmlElement = document.createElement("div")
  htmlElement.innerHTML = `<div class="akn-ref" data-eId="quot-1_ref-2">Test
        text with some     odd line breaks and   spaces</div>`

  const ldmlDocument = xmlStringToDocument(
    `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1"><akn:ref eId="quot-1_ref-1">Test text     with some odd

            line breaks    and spaces
</akn:ref></akn:quotedText>`,
  )

  for (let i = 0; i < (htmlElement.firstChild?.textContent?.length ?? 0); i++) {
    if (!htmlElement.firstChild?.textContent?.charAt(i).match(/\s/)) {
      test(`finds the same character for non white-space indexes (Index: ${i})`, () => {
        const offset = findOffsetInOtherNode(
          htmlElement.firstChild!,
          i,
          ldmlDocument.firstChild!,
        )
        expect(ldmlDocument.firstChild?.textContent?.charAt(offset! - 1)).toBe(
          htmlElement.firstChild?.textContent?.charAt(i - 1),
        )
      })
    }
  }
})

describe("htmlRenderRangeToLdmlDeRange", () => {
  test("creates correct range for a simple range", () => {
    const htmlElement = document.createElement("div")
    htmlElement.innerHTML = `<div class="akn-ref" data-eId="quot-1_ref-1">Test text 123</div>`

    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1"><akn:ref eId="quot-1_ref-1">Test text    123</akn:ref></akn:quotedText>`,
    )

    const range = new Range()
    range.setStart(htmlElement.firstChild!.firstChild!, 2)
    range.setEnd(htmlElement.firstChild!.firstChild!, 12)

    const result = htmlRenderRangeToLdmlDeRange(range, ldmlDocument)

    expect(result?.startContainer.parentElement?.getAttribute("eId")).toEqual(
      "quot-1_ref-1",
    )
    expect(result?.endContainer.parentElement?.getAttribute("eId")).toEqual(
      "quot-1_ref-1",
    )
    expect(result?.startOffset).toEqual(2)
    expect(result?.endOffset).toEqual(15)
  })

  test("creates correct range for range over nested element", () => {
    const htmlElement = document.createElement("div")
    htmlElement.innerHTML = `<div class="akn-quotedText" data-eId="quot-1">Test <span class="akn:ref" data-eId="quot-1_ref-1">text</span> 123</div>`

    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1">Test <akn:ref eId="quot-1_ref-1">text</akn:ref>    123</akn:quotedText>`,
    )

    const range = new Range()
    range.setStart(htmlElement.firstChild!.firstChild!, 2)
    range.setEnd(htmlElement.firstChild!.lastChild!, 2)

    const result = htmlRenderRangeToLdmlDeRange(range, ldmlDocument)

    expect(result?.startContainer.parentElement?.getAttribute("eId")).toEqual(
      "quot-1",
    )
    expect(result?.endContainer.parentElement?.getAttribute("eId")).toEqual(
      "quot-1",
    )
    expect(result?.startOffset).toEqual(2)
    expect(result?.endOffset).toEqual(5)
    expect(
      result?.intersectsNode(getNodeByEid(ldmlDocument, "quot-1_ref-1")!),
    ).toBe(true)
  })
})

describe("ldmlRenderRangeToHtmlRange", () => {
  test("creates correct range for a simple range", () => {
    const htmlElement = document.createElement("div")
    htmlElement.innerHTML = `<div class="akn-ref" data-eId="quot-1_ref-1">Test text    123</div>`

    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1"><akn:ref eId="quot-1_ref-1">Test text 123</akn:ref></akn:quotedText>`,
    )

    const range = new Range()
    range.setStart(ldmlDocument.firstChild!.firstChild!.firstChild!, 2)
    range.setEnd(ldmlDocument.firstChild!.firstChild!.firstChild!, 12)

    const result = ldmlRangeToHtmlRenderRange(range, htmlElement)

    expect(
      result?.startContainer.parentElement?.getAttribute("data-eid"),
    ).toEqual("quot-1_ref-1")
    expect(
      result?.endContainer.parentElement?.getAttribute("data-eid"),
    ).toEqual("quot-1_ref-1")
    expect(result?.startOffset).toEqual(2)
    expect(result?.endOffset).toEqual(15)
  })

  test("creates correct range for range over nested element", () => {
    const htmlElement = document.createElement("div")
    htmlElement.innerHTML = `<div class="akn-quotedText" data-eId="quot-1">Test <span class="akn:ref" data-eId="quot-1_ref-1">text</span>    123</div>`

    const ldmlDocument = xmlStringToDocument(
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1">Test <akn:ref eId="quot-1_ref-1">text</akn:ref> 123</akn:quotedText>`,
    )

    const range = new Range()
    range.setStart(ldmlDocument.firstChild!.firstChild!, 2)
    range.setEnd(ldmlDocument.firstChild!.lastChild!, 2)

    const result = ldmlRangeToHtmlRenderRange(range, htmlElement)

    expect(
      result?.startContainer.parentElement?.getAttribute("data-eid"),
    ).toEqual("quot-1")
    expect(
      result?.endContainer.parentElement?.getAttribute("data-eid"),
    ).toEqual("quot-1")
    expect(result?.startOffset).toEqual(2)
    expect(result?.endOffset).toEqual(5)
  })
})
