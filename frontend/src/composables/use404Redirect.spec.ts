import { afterEach, describe, expect, it, vi } from "vitest"
import { nextTick, ref } from "vue"
import { use404Redirect } from "./use404Redirect"

const { replaceMock } = vi.hoisted(() => ({
  replaceMock: vi.fn(),
}))

vi.mock("vue-router", () => ({
  useRouter: () => ({ replace: replaceMock }),
}))

describe("use404Redirect", () => {
  afterEach(() => {
    vi.resetAllMocks()
  })

  it("does nothing if the list of errors is empty", async () => {
    const errors = ref([])
    use404Redirect(errors)
    await nextTick()
    expect(replaceMock).not.toHaveBeenCalled()
  })

  it("does nothing if the list of errors only contains non-404 errors", async () => {
    const errors = ref([{ status: 300 }, { status: 401 }])
    use404Redirect(errors)
    await nextTick()
    expect(replaceMock).not.toHaveBeenCalled()
  })

  it("does nothing for a mixed list without non-404 errors", async () => {
    const errors = ref([undefined, { status: 401 }, undefined])
    use404Redirect(errors)
    await nextTick()
    expect(replaceMock).not.toHaveBeenCalled()
  })

  it("redirects for a single 404 error", async () => {
    const errors = ref([{ status: 404 }])
    use404Redirect(errors)
    await nextTick()
    expect(replaceMock).toHaveBeenCalledOnce()
  })

  it("redirects with multiple 404 errors", async () => {
    const errors = ref([undefined, { status: 404 }, { status: 404 }])
    use404Redirect(errors)
    await nextTick()
    expect(replaceMock).toHaveBeenCalledOnce()
  })

  it("redirects as soon as one error is a 404", async () => {
    const errors = ref([{ status: 300 }, { status: 404 }])
    use404Redirect(errors)
    await nextTick()
    expect(replaceMock).toHaveBeenCalledOnce()
  })

  it("redirects when the list of errors is changed", async () => {
    const errors = ref<{ status: number }[]>([])
    use404Redirect(errors)
    await nextTick()
    expect(replaceMock).not.toHaveBeenCalled()
    errors.value = [{ status: 404 }]
    await nextTick()
    expect(replaceMock).toHaveBeenCalled()
  })
})
