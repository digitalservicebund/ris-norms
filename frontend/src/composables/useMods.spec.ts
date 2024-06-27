import { beforeEach, describe, expect, test, vi } from "vitest"
import { ref } from "vue"

describe("useMods", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the data about the mods", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getTimeBoundaryDate: vi.fn().mockImplementation((xml, eid) => {
        switch (eid) {
          case "eid-1":
            return "2020-01-01"
          case "eid-2":
            return "2022-02-02"
        }
        throw new Error("Called with wrong eid")
      }),
    }))
    const { useMods } = await import("./useMods")

    const mods = useMods(["eid-1", "eid-2"], `<xml></xml>`)

    expect(mods.value).toHaveLength(2)
    expect(mods.value[0].timeBoundary).toBe("2020-01-01")
    expect(mods.value[1].timeBoundary).toBe("2022-02-02")
  })

  test("should react if the eids change", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getTimeBoundaryDate: vi.fn().mockImplementation((xml, eid) => {
        switch (eid) {
          case "eid-1":
            return "2020-01-01"
          case "eid-2":
            return "2022-02-02"
          case "eid-3":
            return "2024-04-04"
        }
        throw new Error("Called with wrong eid")
      }),
    }))
    const { useMods } = await import("./useMods")

    const eids = ref(["eid-1", "eid-2"])

    const mods = useMods(eids, `<xml></xml>`)
    expect(mods.value).toHaveLength(2)

    eids.value = ["eid-2", "eid-3"]
    expect(mods.value[0].timeBoundary).toBe("2022-02-02")
    expect(mods.value[1].timeBoundary).toBe("2024-04-04")
  })
})
