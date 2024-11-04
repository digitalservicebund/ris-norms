import { beforeEach, describe, expect, it, vi } from "vitest"
import { reactive } from "vue"

describe("useEidPathParameter", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("should provide the eId from the route", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: { eid: "foo" },
        }),
      ),
    }))

    const { useEidPathParameter } = await import("./useEidPathParameter")
    const eid = useEidPathParameter()

    expect(eid.value).toBe("foo")
  })

  it("should return undefined if the route doesn't include an eId", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {},
        }),
      ),
    }))

    const { useEidPathParameter } = await import("./useEidPathParameter")
    const eid = useEidPathParameter()

    expect(eid.value).toBeUndefined()
  })
})
