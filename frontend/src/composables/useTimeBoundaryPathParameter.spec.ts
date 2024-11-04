import { beforeEach, describe, expect, it, vi } from "vitest"
import { reactive } from "vue"

describe("useTimeBoundaryPathParameter", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("should provide a valid time boundary", async () => {
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

  it("should react to route param changes", async () => {
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

  it("should update route param when changed", async () => {
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

  it("returns the current value as a date", async () => {
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

  it("should not set the time boundary to an empty value", async () => {
    const routerReplace = vi.fn()

    vi.doMock("vue-router", () => ({
      useRoute: vi
        .fn()
        .mockReturnValue(reactive({ params: { timeBoundary: "2024-05-10" } })),
      useRouter: vi.fn().mockReturnValue(reactive({ replace: routerReplace })),
    }))

    const { useTimeBoundaryPathParameter } = await import(
      "./useTimeBoundaryPathParameter"
    )
    const { timeBoundary } = useTimeBoundaryPathParameter()

    timeBoundary.value = ""
    expect(routerReplace).not.toBeCalled()
  })
})
