import { describe, expect, it } from "vitest"
import {
  DocumentTypeValues,
  MetaSubtypValues,
  getDocumentTypeFromMetadata,
  isMetaSubtypValue,
  udpateArtNorm,
  isArtNormTypePresent,
  UNKNOWN_DOCUMENT_TYPE,
} from "./proprietary"

describe("getDocumentTypeFromMetadata", () => {
  it("returns the document type", () => {
    const combinations = Object.entries(DocumentTypeValues)

    combinations.forEach(([expectedResult, input]) => {
      expect(getDocumentTypeFromMetadata(input.subtyp)).toBe(expectedResult)
    })
  })

  it("returns unknown if no combination matches", () => {
    expect(getDocumentTypeFromMetadata("Satzung oder so")).toBe(
      UNKNOWN_DOCUMENT_TYPE,
    )
  })

  it("returns unknown if all inputs are empty", () => {
    // @ts-expect-error breaking on purpose for testing
    expect(getDocumentTypeFromMetadata(undefined)).toBe(UNKNOWN_DOCUMENT_TYPE)

    expect(getDocumentTypeFromMetadata("")).toBe(UNKNOWN_DOCUMENT_TYPE)
  })
})

describe("isMetaSubtypValue", () => {
  it("identifies valid values", () => {
    MetaSubtypValues.forEach((value) => {
      expect(isMetaSubtypValue(value)).toBe(true)
    })
  })

  it("does not identify undefined as a valid value", () => {
    expect(isMetaSubtypValue(undefined)).toBe(false)
  })
})

describe("isArtNormTypPresent", () => {
  it("finds type with defined artNorm", () => {
    const artNorm = "SN,ÄN,ÜN"
    expect(isArtNormTypePresent(artNorm, "SN")).toBeTruthy()
  })
  it("does not find type with defined artNorm", () => {
    const artNorm = "SN,ÜN"
    expect(isArtNormTypePresent(artNorm, "ÄN")).toBeFalsy()
  })
  it("does not find type with undefined artNorm", () => {
    expect(isArtNormTypePresent(undefined, "SN")).toBeFalsy()
  })
})

describe("udpateArtNorm", () => {
  it("adds type with defined artNorm", () => {
    const artNorm = "ÄN,ÜN"
    expect(udpateArtNorm(artNorm, "SN", true)).toContain("SN")
  })
  it("adds type with undefined artNorm", () => {
    expect(udpateArtNorm(undefined, "SN", true)).toContain("SN")
  })
  it("removes type with defined artNorm", () => {
    const artNorm = "SN,ÄN,ÜN"
    expect(udpateArtNorm(artNorm, "SN", false)).not.toContain("SN")
  })
  it("does not remove type with undefined artNorm", () => {
    expect(udpateArtNorm(undefined, "SN", false)).toBeUndefined()
  })
  it("does not add type because is already present", () => {
    const artNorm = "ÄN,ÜN"
    expect(udpateArtNorm(artNorm, "ÜN", true)).toBe(artNorm)
  })
})
