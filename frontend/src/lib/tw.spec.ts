import { describe, expect, it } from "vitest"
import { tw } from "./tw"

describe("tw", () => {
  it("should return the content of a plain string", () => {
    expect(tw`a b`).toBe("a b")
  })

  it("should return the content of an interpolated string", () => {
    expect(tw`a ${"b"}`).toBe("a b")
  })
})
