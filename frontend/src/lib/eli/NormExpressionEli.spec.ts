import { describe, expect, it } from "vitest"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"

describe("normExpressionEli", () => {
  it("fromString", () => {
    const eli = NormExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu",
    )
    expect(eli.agent).toBe("bgbl-1")
    expect(eli.year).toBe("2021")
    expect(eli.naturalIdentifier).toBe("s4")
    expect(eli.pointInTime).toBe("2021-03-01")
    expect(eli.version).toBe(1)
    expect(eli.language).toBe("deu")
  })

  it("toString", () => {
    const eli = NormExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu",
    )
    expect(eli.toString()).toBe("eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu")
  })

  it("asNormWorkEli", () => {
    const eli = NormExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu",
    )
    expect(eli.asNormWorkEli().toString()).toBe("eli/bund/bgbl-1/2021/s4")
  })
})
