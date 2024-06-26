import { beforeEach, describe, expect, test, vi } from "vitest"
import { reactive } from "vue"

describe("useModEidPathParameter", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide a valid eid", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {
            modEid: "unknown-eid-1",
          },
        }),
      ),
      useRouter: vi.fn(),
    }))

    const { useModEidPathParameter } = await import("./useModEidPathParameter")
    const modEid = useModEidPathParameter()

    expect(modEid.value).toBe("unknown-eid-1")
  })

  test("should react to route param changes", async () => {
    const route = reactive({
      params: {
        modEid: "unknown-eid-1",
      },
    })

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(route),
      useRouter: vi.fn(),
    }))

    const { useModEidPathParameter } = await import("./useModEidPathParameter")
    const modEid = useModEidPathParameter()

    expect(modEid.value).toBe("unknown-eid-1")
    route.params.modEid = "unknown-eid-2"
    expect(modEid.value).toBe("unknown-eid-2")
  })

  test("should update route param when changed", async () => {
    const routerReplace = vi.fn()

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn(),
      useRouter: vi.fn().mockReturnValue({
        replace: routerReplace,
      }),
    }))

    const { useModEidPathParameter } = await import("./useModEidPathParameter")
    const modEid = useModEidPathParameter()
    modEid.value = "unknown-eid-2"
    expect(routerReplace).toHaveBeenCalledWith({
      params: { modEid: "unknown-eid-2" },
    })
  })
})
