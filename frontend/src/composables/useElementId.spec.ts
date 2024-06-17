import { afterEach, describe, expect, test, vi } from "vitest"
import { useElementId } from "./useElementId"

describe("useElementId", () => {
  afterEach(() => {
    vi.unstubAllGlobals()
  })

  test("returns an identifier", () => {
    const { identifier } = useElementId()
    expect(identifier).toBeTruthy()
  })

  test("returns multiple identifiers with destructuring", () => {
    const { id1, id2, id3, id4, id5 } = useElementId()
    expect(id1).toBeTruthy()
    expect(typeof id1).toBe("string")
    expect(id2).toBeTruthy()
    expect(typeof id2).toBe("string")
    expect(id3).toBeTruthy()
    expect(typeof id3).toBe("string")
    expect(id4).toBeTruthy()
    expect(typeof id4).toBe("string")
    expect(id5).toBeTruthy()
    expect(typeof id5).toBe("string")
  })

  test("returns multiple identifiers with object access", () => {
    const ids = useElementId()
    expect(ids.id1).toBeTruthy()
    expect(typeof ids.id1).toBe("string")
    expect(ids.id2).toBeTruthy()
    expect(typeof ids.id2).toBe("string")
    expect(ids.id3).toBeTruthy()
    expect(typeof ids.id3).toBe("string")
    expect(ids.id4).toBeTruthy()
    expect(typeof ids.id4).toBe("string")
    expect(ids.id5).toBeTruthy()
    expect(typeof ids.id5).toBe("string")
  })

  test("returns different identifiers when called multiple times", () => {
    const ids = new Array(1000)
      .fill(undefined)
      .map(() => useElementId().someId)
      .reduce((all, current) => all.add(current), new Set())

    expect(Array.from(ids).every((i) => typeof i === "string")).toBe(true)
    expect(ids.size).toBe(1000)
  })

  test("returns an ID with the default prefix", () => {
    const { identifier } = useElementId("element")
    expect(identifier.startsWith("element-")).toBe(true)
  })

  test("returns an ID with a custom prefix", () => {
    const { identifier } = useElementId("test")
    expect(identifier.startsWith("test-")).toBe(true)
  })
})
