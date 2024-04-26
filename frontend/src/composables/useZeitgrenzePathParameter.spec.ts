import { beforeEach, describe, expect, test, vi } from "vitest"
import { reactive } from "vue"

describe("useZeitgrenzePathParameter", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide a valid Zeitgrenze", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {
            zeitgrenze: "unknown-eid-1",
          },
        }),
      ),
      useRouter: vi.fn(),
    }))

    const { useZeitgrenzePathParameter } = await import(
      "./useZeitgrenzePathParameter"
    )
    const zeitgrenze = useZeitgrenzePathParameter()

    expect(zeitgrenze.value).toBe("unknown-eid-1")
  })

  test("should react to route param changes", async () => {
    const route = reactive({
      params: {
        zeitgrenze: "unknown-eid-1",
      },
    })

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(route),
      useRouter: vi.fn(),
    }))

    const { useZeitgrenzePathParameter } = await import(
      "./useZeitgrenzePathParameter"
    )
    const zeitgrenze = useZeitgrenzePathParameter()

    expect(zeitgrenze.value).toBe("unknown-eid-1")
    route.params.zeitgrenze = "unknown-eid-2"
    expect(zeitgrenze.value).toBe("unknown-eid-2")
  })

  test("should update route param when changed", async () => {
    const routerPush = vi.fn()

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn(),
      useRouter: vi.fn().mockReturnValue({
        push: routerPush,
      }),
    }))

    const { useZeitgrenzePathParameter } = await import(
      "./useZeitgrenzePathParameter"
    )
    const zeitgrenze = useZeitgrenzePathParameter()
    zeitgrenze.value = "unknown-eid-2"
    expect(routerPush).toHaveBeenCalledWith({
      params: { zeitgrenze: "unknown-eid-2" },
    })
  })
})
