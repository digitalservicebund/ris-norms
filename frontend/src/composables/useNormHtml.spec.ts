import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useNormHtml", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the norm html", async () => {
    const getNormHtmlByEli = vi.fn().mockResolvedValue("<div></div>")

    vi.doMock("@/services/normService", () => ({
      getNormHtmlByEli,
    }))

    const { useNormHtml } = await import("./useNormHtml")

    const eli = ref(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )
    const html = useNormHtml(eli)
    await vi.waitUntil(() => html.value)

    expect(html.value).toBe("<div></div>")
  })

  test("should load the norm html when the eli changes", async () => {
    const getNormHtmlByEli = vi.fn()

    vi.doMock("@/services/normService", () => ({
      getNormHtmlByEli,
    }))

    const { useNormHtml } = await import("./useNormHtml")

    const eli = ref(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )
    useNormHtml(eli)

    eli.value = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
    await nextTick()

    eli.value = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
    await nextTick()

    expect(getNormHtmlByEli).toBeCalledTimes(2)
  })

  test("should load the norm html when the date changes", async () => {
    const getNormHtmlByEli = vi.fn()

    vi.doMock("@/services/normService", () => ({
      getNormHtmlByEli,
    }))

    const { useNormHtml } = await import("./useNormHtml")

    const date = ref(new Date())
    useNormHtml(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      date,
    )

    date.value = new Date()
    await nextTick()

    expect(getNormHtmlByEli).toBeCalledTimes(2)
  })
})

describe("useNormHtmlByEli", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the norm html", async () => {
    const mockData = ref("<div></div>")
    const mockError = ref(null)
    const mockIsFetching = ref(false)

    vi.doMock("@/services/normService", () => ({
      useGetNormHtmlByEli: vi.fn().mockReturnValue({
        data: mockData,
        error: mockError,
        isFetching: mockIsFetching,
      }),
    }))

    const { useNormHtmlByEli } = await import("./useNormHtml")
    const eli = ref(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )
    const { normHtml } = useNormHtmlByEli(eli)
    await vi.waitUntil(() => normHtml.value)
    expect(normHtml.value).toBe("<div></div>")
  })

  test.skip("should load the norm html when the eli changes", async () => {
    const mockData = ref("")
    const mockError = ref(null)
    const mockIsFetching = ref(false)

    const useGetNormHtmlByEliMock = vi.fn().mockReturnValue({
      data: mockData,
      error: mockError,
      isFetching: mockIsFetching,
    })

    vi.doMock("@/services/normService", () => ({
      useGetNormHtmlByEli: useGetNormHtmlByEliMock,
    }))

    const { useNormHtmlByEli } = await import("./useNormHtml")
    const eli = ref(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )

    useNormHtmlByEli(eli)
    expect(useGetNormHtmlByEliMock).toHaveBeenCalledWith(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      false,
      undefined,
    )

    eli.value = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
    await nextTick()

    // Ensure the watcher reacts to the change in `eli`
    await vi.waitFor(() => {
      expect(useGetNormHtmlByEliMock).toHaveBeenCalledWith(
        "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1",
        false,
        undefined,
      )
    })

    expect(useGetNormHtmlByEliMock).toBeCalledTimes(2)
  })

  test.skip("should load the norm html when the date changes", async () => {
    const mockData = ref("")
    const mockError = ref(null)
    const mockIsFetching = ref(false)

    const useGetNormHtmlByEliMock = vi.fn().mockReturnValue({
      data: mockData,
      error: mockError,
      isFetching: mockIsFetching,
    })

    vi.doMock("@/services/normService", () => ({
      useGetNormHtmlByEli: useGetNormHtmlByEliMock,
    }))

    const { useNormHtmlByEli } = await import("./useNormHtml")

    const date = ref(new Date())
    useNormHtmlByEli(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      date,
    )

    date.value = new Date()
    await nextTick()

    expect(useGetNormHtmlByEliMock).toBeCalledTimes(2)
  })
})
