import { describe, expect, it } from "vitest"
import { DokumentManifestationEli } from "./DokumentManifestationEli"

describe("dokumentManifestationEli", () => {
  describe("hasPointInTimeManifestation", () => {
    it("should be true if pointInTimeManifestation exists", () => {
      const eli = DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03/regelungstext-verkuendung-1.xml",
      )
      expect(eli.hasPointInTimeManifestation()).toBe(true)
    })

    it("should be false if pointInTimeManifestation does not exist", () => {
      const eli = DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1.xml",
      )
      expect(eli.hasPointInTimeManifestation()).toBe(false)
    })
  })

  describe("toString", () => {
    it("should be correct with pointInTimeManifestation", () => {
      const eli = DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03/regelungstext-verkuendung-1.xml",
      )
      expect(eli.toString()).toBe(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03/regelungstext-verkuendung-1.xml",
      )
    })

    it("should be correct without pointInTimeManifestation", () => {
      const eli = DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1.xml",
      )
      expect(eli.toString()).toBe(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1.xml",
      )
    })
  })

  describe("fromString", () => {
    it("should be correct with pointInTimeManifestation", () => {
      const eli = DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03/regelungstext-verkuendung-1.xml",
      )
      expect(eli.agent).toBe("bgbl-1")
      expect(eli.year).toBe("2021")
      expect(eli.naturalIdentifier).toBe("s4")
      expect(eli.pointInTime).toBe("2021-03-01")
      expect(eli.version).toBe(1)
      expect(eli.language).toBe("deu")
      expect(eli.pointInTimeManifestation).toBe("2021-03-03")
      expect(eli.subtype).toBe("regelungstext-verkuendung-1")
      expect(eli.format).toBe("xml")
    })

    it("should be correct without pointInTimeManifestation", () => {
      const eli = DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1.xml",
      )
      expect(eli.agent).toBe("bgbl-1")
      expect(eli.year).toBe("2021")
      expect(eli.naturalIdentifier).toBe("s4")
      expect(eli.pointInTime).toBe("2021-03-01")
      expect(eli.version).toBe(1)
      expect(eli.language).toBe("deu")
      expect(eli.pointInTimeManifestation).toBe(null)
      expect(eli.subtype).toBe("regelungstext-verkuendung-1")
      expect(eli.format).toBe("xml")
    })
  })

  describe("withoutPointInTimeManifestation", () => {
    it("should remove pointInTimeManifestation", () => {
      const eli = DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/2021-03-03/regelungstext-verkuendung-1.xml",
      )
      expect(eli.withoutPointInTimeManifestation().toString()).toBe(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1.xml",
      )
    })
  })

  describe("asNormWorkEli", () => {
    it("returns a Norm work ELI with a counter", () => {
      const eli = DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/regelungstext-verkuendung-2.xml",
      ).asNormWorkEli()
      expect(eli.toString()).toBe("eli/bund/bgbl-1/2024/17-1")
    })

    it("returns a Norm work ELI without a counter", () => {
      const eli = DokumentManifestationEli.fromString(
        "eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/regelungstext-verkuendung-1.xml",
      ).asNormWorkEli()
      expect(eli.toString()).toBe("eli/bund/bgbl-1/2024/17")
    })
  })
})
