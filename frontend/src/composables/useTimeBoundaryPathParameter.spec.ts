import { beforeEach, describe, expect, test, vi } from "vitest"
import { reactive } from "vue"

describe("useTimeBoundaryPathParameter", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide a valid time boundary", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {
            timeBoundary: "unknown-eid-1",
          },
        }),
      ),
      useRouter: vi.fn(),
    }))

    const { useTimeBoundaryPathParameter } = await import(
      "./useTimeBoundaryPathParameter"
    )
    const timeBoundary = useTimeBoundaryPathParameter()

    expect(timeBoundary.value).toBe("unknown-eid-1")
  })

  test("should react to route param changes", async () => {
    const route = reactive({
      params: {
        timeBoundary: "unknown-eid-1",
      },
    })

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(route),
      useRouter: vi.fn(),
    }))

    const { useTimeBoundaryPathParameter } = await import(
      "./useTimeBoundaryPathParameter"
    )
    const timeBoundary = useTimeBoundaryPathParameter()

    expect(timeBoundary.value).toBe("unknown-eid-1")
    route.params.timeBoundary = "unknown-eid-2"
    expect(timeBoundary.value).toBe("unknown-eid-2")
  })

  test("should update route param when changed", async () => {
    const routerReplace = vi.fn()

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn(),
      useRouter: vi.fn().mockReturnValue({
        replace: routerReplace,
      }),
    }))

    const { useTimeBoundaryPathParameter } = await import(
      "./useTimeBoundaryPathParameter"
    )
    const timeBoundary = useTimeBoundaryPathParameter()
    timeBoundary.value = "unknown-eid-2"
    expect(routerReplace).toHaveBeenCalledWith({
      params: { timeBoundary: "unknown-eid-2" },
    })
  })
})
