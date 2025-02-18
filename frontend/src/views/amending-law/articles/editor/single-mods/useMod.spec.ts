import { beforeEach, describe, expect, it, vi } from "vitest"

describe("useMod", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("should provide the data about a mod", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getTimeBoundaryDate: vi.fn().mockReturnValue("2020-01-01"),
    }))
    vi.doMock("@/services/ldmldeService", () => ({
      getNodeByEid: vi.fn().mockReturnValue({ object: "that is not null" }),
    }))
    const { useMod } = await import("./useMod")

    const { timeBoundary } = useMod("eli", "eid", `<xml></xml>`)

    expect(timeBoundary.value).toBe("2020-01-01")
  })

  it("should provide default values without a mod eid", async () => {
    const { useMod } = await import("./useMod")

    const { timeBoundary } = useMod(null, null, `<xml></xml>`)

    expect(timeBoundary.value).toBeUndefined()
  })

  it("should support changing the values of the returned refs", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getTimeBoundaryDate: vi.fn().mockReturnValue({
        date: "2020-01-01",
        temporalGroupEid: "timeboundary-1",
      }),
    }))
    vi.doMock("@/services/ldmldeService", () => ({
      getNodeByEid: vi.fn().mockReturnValue({ object: "that is not null" }),
    }))
    const { useMod } = await import("./useMod")

    const { timeBoundary } = useMod("eli", "eid", `<xml></xml>`)

    expect(timeBoundary.value).toStrictEqual({
      date: "2020-01-01",
      temporalGroupEid: "timeboundary-1",
    })
    timeBoundary.value = {
      date: "2021-02-02",
      temporalGroupEid: "timeboundary-2",
    }
    expect(timeBoundary.value).toStrictEqual({
      date: "2021-02-02",
      temporalGroupEid: "timeboundary-2",
    })
  })
})
