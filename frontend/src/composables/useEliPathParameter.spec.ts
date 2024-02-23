import { beforeEach, describe, expect, test, vi } from "vitest"
import { reactive } from "vue"

describe("useEliPathParameter", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide a valid eli", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {
            eliJurisdiction: "bund",
            eliAgent: "bgbl-1",
            eliYear: "2017",
            eliNaturalIdentifier: "s419",
          },
        }),
      ),
    }))

    const { useEliPathParameter } = await import("./useEliPathParameter")
    const eli = useEliPathParameter()

    expect(eli.value).toBe("eli/bund/bgbl-1/2017/s419")
  })

  test("should react to route param changes", async () => {
    const route = reactive({
      params: {
        eliJurisdiction: "bund",
        eliAgent: "bgbl-1",
        eliYear: "2017",
        eliNaturalIdentifier: "s419",
      },
    })

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(route),
    }))

    const { useEliPathParameter } = await import("./useEliPathParameter")
    const eli = useEliPathParameter()

    expect(eli.value).toBe("eli/bund/bgbl-1/2017/s419")

    route.params.eliYear = "2023"
    route.params.eliNaturalIdentifier = "413"

    expect(eli.value).toBe("eli/bund/bgbl-1/2023/413")
  })

  test("should throw if eli parameters are missing", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {},
        }),
      ),
    }))

    const { useEliPathParameter } = await import("./useEliPathParameter")
    const eli = useEliPathParameter()

    expect(() => eli.value).toThrowError(
      "useEliPathParameter: You can only use this composables on pages which have an eli in their route",
    )
  })
})
