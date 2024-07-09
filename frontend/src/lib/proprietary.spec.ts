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
  udpateArtNorm,
  isArtNormTypePresent,
  UNKNOWN_DOCUMENT_TYPE,
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

  test("returns unknown if no combination matches", () => {
    expect(
      getDocumentTypeFromMetadata(
        "regelungstext",
        "verwaltungsvorschrift",
        "Satzung",
      ),
    ).toBe(UNKNOWN_DOCUMENT_TYPE)
  })

  test("returns unknown if all inputs are empty", () => {
    // @ts-expect-error breaking on purpose for testing
    expect(getDocumentTypeFromMetadata(undefined, undefined, undefined)).toBe(
      UNKNOWN_DOCUMENT_TYPE,
    )

    expect(getDocumentTypeFromMetadata("", "", "")).toBe(UNKNOWN_DOCUMENT_TYPE)
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

  test("does not identify undefined as a valid value", () => {
    expect(isMetaSubtypValue(undefined)).toBe(false)
  })
})

describe("isArtNormTypPresent", () => {
  test("finds type with defined artNorm", () => {
    const artNorm = "SN,ÄN,ÜN"
    expect(isArtNormTypePresent(artNorm, "SN")).toBeTruthy()
  })
  test("does not find type with defined artNorm", () => {
    const artNorm = "SN,ÜN"
    expect(isArtNormTypePresent(artNorm, "ÄN")).toBeFalsy()
  })
  test("does not find type with undefined artNorm", () => {
    expect(isArtNormTypePresent(undefined, "SN")).toBeFalsy()
  })
})

describe("udpateArtNorm", () => {
  test("adds type with defined artNorm", () => {
    const artNorm = "ÄN,ÜN"
    expect(udpateArtNorm(artNorm, "SN", true)).toContain("SN")
  })
  test("adds type with undefined artNorm", () => {
    expect(udpateArtNorm(undefined, "SN", true)).toContain("SN")
  })
  test("removes type with defined artNorm", () => {
    const artNorm = "SN,ÄN,ÜN"
    expect(udpateArtNorm(artNorm, "SN", false)).not.toContain("SN")
  })
  test("does not remove type with undefined artNorm", () => {
    expect(udpateArtNorm(undefined, "SN", false)).toBeUndefined()
  })
  test("does not add type because is already present", () => {
    const artNorm = "ÄN,ÜN"
    expect(udpateArtNorm(artNorm, "ÜN", true)).toBe(artNorm)
  })
})
