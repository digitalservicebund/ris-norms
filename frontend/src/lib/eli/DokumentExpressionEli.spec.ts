import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { describe, expect, it } from "vitest"

describe("dokumentExpressionEli", () => {
  it("fromString", () => {
    const eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
    )
    expect(eli.agent).toBe("bgbl-1")
    expect(eli.year).toBe("2021")
    expect(eli.naturalIdentifier).toBe("s4")
    expect(eli.pointInTime).toBe("2021-03-01")
    expect(eli.version).toBe(1)
    expect(eli.language).toBe("deu")
    expect(eli.subtype).toBe("regelungstext-1")
  })

  it("toString", () => {
    const eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
    )
    expect(eli.toString()).toBe(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
    )
  })

  it("asNormEli", () => {
    const eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
    )
    expect(eli.asNormEli().toString()).toBe(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu",
    )
  })

  it("asNormWorkEli", () => {
    const eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
    )
    expect(eli.asNormWorkEli().toString()).toBe("eli/bund/bgbl-1/2021/s4")
  })
})
