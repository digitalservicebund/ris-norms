import { beforeEach, describe, expect, test, vi } from "vitest"
import { reactive } from "vue"

describe("useTimeBoundaryPathParameter", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide a valid time boundary", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi
        .fn()
        .mockReturnValue(reactive({ params: { timeBoundary: "2024-05-10" } })),
      useRouter: vi.fn(),
    }))

    const { useTimeBoundaryPathParameter } = await import(
      "./useTimeBoundaryPathParameter"
    )
    const { timeBoundary } = useTimeBoundaryPathParameter()

    expect(timeBoundary.value).toBe("2024-05-10")
  })

  test("should react to route param changes", async () => {
    const route = reactive({ params: { timeBoundary: "2024-05-10" } })

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(route),
      useRouter: vi.fn(),
    }))

    const { useTimeBoundaryPathParameter } = await import(
      "./useTimeBoundaryPathParameter"
    )
    const { timeBoundary } = useTimeBoundaryPathParameter()

    expect(timeBoundary.value).toBe("2024-05-10")
    route.params.timeBoundary = "2024-05-11"
    expect(timeBoundary.value).toBe("2024-05-11")
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
    const { timeBoundary } = useTimeBoundaryPathParameter()
    timeBoundary.value = "2024-05-11"
    expect(routerReplace).toHaveBeenCalledWith({
      params: { timeBoundary: "2024-05-11" },
    })
  })

  test("returns the current value as a date", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi
        .fn()
        .mockReturnValue(reactive({ params: { timeBoundary: "2024-05-10" } })),
      useRouter: vi.fn(),
    }))

    const { useTimeBoundaryPathParameter } = await import(
      "./useTimeBoundaryPathParameter"
    )
    const { timeBoundaryAsDate } = useTimeBoundaryPathParameter()

    expect(timeBoundaryAsDate.value).toEqual(new Date("2024-05-10"))
  })
})
