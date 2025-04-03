import { describe, it, expect } from "vitest"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"

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
})
