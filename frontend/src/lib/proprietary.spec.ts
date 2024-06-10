import { describe, expect, test } from "vitest"
import {
  DocumentTypeValues,
  MetaArtValues,
  MetaSubtypValues,
  MetaTypValues,
  getDocumentTypeFromMetadata,
  isMetaArtValue,
  isMetaSubtypValue,
  isMetaTypValue,
} from "./proprietary"

describe("getDocumentTypeFromMetadata", () => {
  test("returns the document type", () => {
    const combinations = Object.entries(DocumentTypeValues)

    combinations.forEach(([expectedResult, input]) => {
      expect(
        getDocumentTypeFromMetadata(input.art, input.typ, input.subtyp),
      ).toBe(expectedResult)
    })
  })
})

describe("isMetaArtValue", () => {
  test("identifies valid values", () => {
    MetaArtValues.forEach((value) => {
      expect(isMetaArtValue(value)).toBe(true)
    })
  })

  test("does not identify undefined as a valid value", () => {
    expect(isMetaArtValue(undefined)).toBe(false)
  })
})

describe("isMetaTypValue", () => {
  test("identifies valid values", () => {
    MetaTypValues.forEach((value) => {
      expect(isMetaTypValue(value)).toBe(true)
    })
  })

  test("does not identify undefined as a valid value", () => {
    expect(isMetaTypValue(undefined)).toBe(false)
  })
})

describe("isMetaSubtypValue", () => {
  test("identifies valid values", () => {
    MetaSubtypValues.forEach((value) => {
      expect(isMetaSubtypValue(value)).toBe(true)
    })
  })

  test("identifies null as a valid value", () => {
    expect(isMetaSubtypValue(null)).toBe(true)
  })

  test("does not identify undefined as a valid value", () => {
    expect(isMetaSubtypValue(undefined)).toBe(false)
  })
})
