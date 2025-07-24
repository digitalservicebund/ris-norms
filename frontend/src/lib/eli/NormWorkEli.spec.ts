import { describe, it, expect } from "vitest"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"
import { NormExpressionEli } from "./NormExpressionEli"

describe("normWorkEli", () => {
  it("fromString", () => {
    const eli = NormWorkEli.fromString("eli/bund/bgbl-1/2021/s4")
    expect(eli.agent).toBe("bgbl-1")
    expect(eli.year).toBe("2021")
    expect(eli.naturalIdentifier).toBe("s4")
  })

  it("toString", () => {
    const eli = new NormWorkEli("bgbl-1", "2021", "s4")
    expect(eli.toString()).toBe("eli/bund/bgbl-1/2021/s4")
  })

  it("throws on invalid ELI", () => {
    expect(() => NormWorkEli.fromString("not-a-valid-eli")).toThrow(
      "Invalid work-level ELI",
    )
  })

  describe("equals", () => {
    it("returns true when identical", () => {
      const a = NormWorkEli.fromString("eli/bund/bgbl-1/2021/s4")
      expect(a.equals(a)).toBe(true)
    })

    it("returns true when equal", async () => {
      const a = NormWorkEli.fromString("eli/bund/bgbl-1/2021/s4")
      const b = NormWorkEli.fromString("eli/bund/bgbl-1/2021/s4")

      expect(a.equals(b)).toBe(true)
    })

    it("returns false when compared to an instance of something else", async () => {
      const a = NormWorkEli.fromString("eli/bund/bgbl-1/2021/s4")
      const b = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2024-01-01/1/deu",
      )

      expect(a.equals(b)).toBe(false)
    })

    it("returns false when compared to a different norm work ELI", async () => {
      const a = NormWorkEli.fromString("eli/bund/bgbl-1/2021/s4")
      const b = NormWorkEli.fromString("eli/bund/bgbl-1/2025/s100")

      expect(a.equals(b)).toBe(false)
    })
  })
})
