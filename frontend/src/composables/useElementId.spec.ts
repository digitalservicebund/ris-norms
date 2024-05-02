import { afterEach, describe, expect, test, vi } from "vitest"
import { useElementId } from "./useElementId"

describe("useElementId", () => {
  afterEach(() => {
    vi.unstubAllGlobals()
  })

  test("returns an identifier", () => {
    const identifier = useElementId()
    expect(identifier.value).toBeTruthy()
  })

  test("returns different identifiers when called multiple times", () => {
    const identifiers = new Array(1000)
      .fill(undefined)
      .map(() => useElementId().value)
      .reduce((all, current) => all.add(current), new Set())

    expect(identifiers.size).toBe(1000)
  })

  test("does not return an ID that is already in use", () => {
    vi.stubGlobal("Math", {
      round: Math.round,
      max: Math.max,
      min: Math.min,
      random: vi
        .fn()
        .mockReturnValueOnce(0.11111111)
        .mockReturnValueOnce(0.11111111)
        .mockReturnValueOnce(0.22222222),
    })

    const id1 = useElementId().value
    const id2 = useElementId().value

    expect(id1).not.toBe(id2)
  })
})
