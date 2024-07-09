import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useMods", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the data about the mods", async () => {
    vi.doMock("@/services/ldmldeService", () => ({
      getNodeByEid: vi.fn().mockReturnValue({}),
    }))
    vi.doMock("@/services/ldmldeModService", () => ({
      getTimeBoundaryDate: vi.fn().mockImplementation((xml, eid) => {
        switch (eid) {
          case "eid-1":
            return { date: "2020-01-01", temporalGroupEid: "temporal-eid-1" }
          case "eid-2":
            return { date: "2022-02-02", temporalGroupEid: "temporal-eid-2" }
        }
        throw new Error("Called with wrong eid")
      }),
      getTextualModType: vi.fn().mockReturnValue("aenderungsbefehl-ersetzen"),
      useUpdateMods: vi.fn(),
    }))
    const { useMods } = await import("./useMods")

    const { data: mods } = useMods("eli", ["eid-1", "eid-2"], `<xml></xml>`)

    await nextTick()

    expect(mods.value).toHaveLength(2)
    expect(mods.value[0].timeBoundary?.date).toBe("2020-01-01")
    expect(mods.value[0].textualModType).toBe("aenderungsbefehl-ersetzen")
    expect(mods.value[1].timeBoundary?.date).toBe("2022-02-02")
    expect(mods.value[1].textualModType).toBe("aenderungsbefehl-ersetzen")
  })

  test("should react if the eids change", async () => {
    vi.doMock("@/services/ldmldeService", () => ({
      getNodeByEid: vi.fn().mockReturnValue({}),
    }))
    vi.doMock("@/services/ldmldeModService", () => ({
      getTimeBoundaryDate: vi.fn().mockImplementation((xml, eid) => {
        switch (eid) {
          case "eid-1":
            return { date: "2020-01-01", temporalGroupEid: "temporal-eid-1" }
          case "eid-2":
            return { date: "2022-02-02", temporalGroupEid: "temporal-eid-2" }
          case "eid-3":
            return { date: "2024-04-04", temporalGroupEid: "temporal-eid-3" }
        }
        throw new Error("Called with wrong eid")
      }),
      getTextualModType: vi.fn().mockReturnValue("aenderungsbefehl-ersetzen"),
      useUpdateMods: vi.fn(),
    }))
    const { useMods } = await import("./useMods")

    const eids = ref(["eid-1", "eid-2"])

    const { data: mods } = useMods("eli", eids, `<xml></xml>`)
    await nextTick()

    expect(mods.value).toHaveLength(2)

    eids.value = ["eid-2", "eid-3"]
    await nextTick()
    expect(mods.value[0].timeBoundary?.date).toBe("2022-02-02")
    expect(mods.value[1].timeBoundary?.date).toBe("2024-04-04")
  })

  test("should support changing the value of the returned data", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getTimeBoundaryDate: vi.fn().mockImplementation((xml, eid) => {
        switch (eid) {
          case "eid-1":
            return { date: "2020-01-01", temporalGroupEid: "eid-1" }
          case "eid-2":
            return { date: "2022-02-02", temporalGroupEid: "eid-2" }
        }
        throw new Error("Called with wrong eid")
      }),
      useUpdateMods: vi.fn(),
    }))
    const { useMods } = await import("./useMods")

    const { data: mods } = useMods("eli", ["eid-1", "eid-2"], `<xml></xml>`)
    await nextTick()

    expect(mods.value[0].timeBoundary?.date).toBe("2020-01-01")
    mods.value = mods.value.map((mod) => {
      if (mod.eid === "eid-1") {
        return {
          ...mod,
          timeBoundary: {
            ...mod.timeBoundary,
            date: "2022-03-03",
          },
        }
      }
      return mod
    })
    expect(mods.value[0].timeBoundary?.date).toBe("2022-03-03")
  })

  test("should overwrite the changed values when the eid changes", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getTimeBoundaryDate: vi.fn().mockImplementation((xml, eid) => {
        switch (eid) {
          case "eid-1":
            return { date: "2020-01-01", temporalGroupEid: "eid-1" }
          case "eid-2":
            return { date: "2022-02-02", temporalGroupEid: "eid-2" }
        }
        throw new Error("Called with wrong eid")
      }),
      useUpdateMods: vi.fn(),
    }))

    const { useMods } = await import("./useMods")

    const eids = ref(["eid-1"])
    const { data: mods } = useMods("eli", eids, `<xml></xml>`)
    await nextTick()

    expect(mods.value[0].timeBoundary?.date).toBe("2020-01-01")

    mods.value = [
      {
        eid: "eid-1",
        timeBoundary: {
          date: "2022-03-03",
          temporalGroupEid: "temporal-eid-1",
        },
        textualModType: "aenderungsbefehl-ersetzen",
      },
    ]
    expect(mods.value[0].timeBoundary?.date).toBe("2022-03-03")

    eids.value = ["eid-2"]
    await nextTick()
    expect(mods.value[0].timeBoundary?.date).toBe("2022-02-02")
  })

  test("should create preview and update using useUpdateMods", async () => {
    const useUpdateMods = vi.fn()

    vi.doMock("@/services/ldmldeModService", () => ({
      getTimeBoundaryDate: vi.fn(),
      useUpdateMods,
    }))

    const { useMods } = await import("./useMods")
    useMods("eli", ["eid-1", "eid-2"], `<xml></xml>`)

    expect(useUpdateMods).toHaveBeenCalledWith("eli", expect.anything(), true)
    expect(useUpdateMods).toHaveBeenCalledWith("eli", expect.anything(), false)
  })
})
