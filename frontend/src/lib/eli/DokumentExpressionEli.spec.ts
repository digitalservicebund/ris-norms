import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { describe, expect, it } from "vitest"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"

describe("dokumentExpressionEli", () => {
  it("fromString", () => {
    const eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
    )
    expect(eli.agent).toBe("bgbl-1")
    expect(eli.year).toBe("2021")
    expect(eli.naturalIdentifier).toBe("s4")
    expect(eli.pointInTime).toBe("2021-03-01")
    expect(eli.version).toBe(1)
    expect(eli.language).toBe("deu")
    expect(eli.subtype).toBe("regelungstext-verkuendung-1")
  })

  it("toString", () => {
    const eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
    )
    expect(eli.toString()).toBe(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
    )
  })

  it("asNormEli", () => {
    const eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
    )
    expect(eli.asNormEli().toString()).toBe(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu",
    )
  })

  it("asNormWorkEli", () => {
    const eli = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
    )
    expect(eli.asNormWorkEli().toString()).toBe("eli/bund/bgbl-1/2021/s4")
  })

  it("fromNormExpressionEli", () => {
    const normExpressionEli = NormExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu",
    )
    const dokumentEli =
      DokumentExpressionEli.fromNormExpressionEli(normExpressionEli)

    expect(dokumentEli.agent).toBe("bgbl-1")
    expect(dokumentEli.year).toBe("2021")
    expect(dokumentEli.naturalIdentifier).toBe("s4")
    expect(dokumentEli.pointInTime).toBe("2021-03-01")
    expect(dokumentEli.version).toBe(1)
    expect(dokumentEli.language).toBe("deu")
    expect(dokumentEli.subtype).toBe("regelungstext-verkuendung-1")
    expect(dokumentEli.toString()).toBe(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
    )
  })

  it("fromNormExpressionEli with custom subtype", () => {
    const normExpressionEli = NormExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu",
    )
    const dokumentEli = DokumentExpressionEli.fromNormExpressionEli(
      normExpressionEli,
      "custom-subtype",
    )

    expect(dokumentEli.subtype).toBe("custom-subtype")
    expect(dokumentEli.toString()).toBe(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/custom-subtype",
    )
  })
})
