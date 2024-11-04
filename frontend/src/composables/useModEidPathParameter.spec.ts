import { beforeEach, describe, expect, it, vi } from "vitest"
import { reactive } from "vue"

describe("useModEidPathParameter", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("should provide a valid eid", async () => {
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

  it("should react to route param changes", async () => {
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

  it("should update route when changed", async () => {
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
      name: "AmendingLawArticleEditorSingleMod",
    })
  })

  it("should update route when changed to empty", async () => {
    const routerReplace = vi.fn()

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn(),
      useRouter: vi.fn().mockReturnValue({
        replace: routerReplace,
      }),
    }))

    const { useModEidPathParameter } = await import("./useModEidPathParameter")
    const modEid = useModEidPathParameter()
    modEid.value = ""
    expect(routerReplace).toHaveBeenCalledWith({
      name: "AmendingLawArticleEditorMultiMod",
    })
  })
})
