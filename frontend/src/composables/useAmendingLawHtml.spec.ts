import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useAmendingLawHtml", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the amending law html", async () => {
    const getAmendingLawHtmlByEli = vi.fn().mockResolvedValue("<div></div>")

    vi.doMock("@/services/amendingLawsService", () => ({
      getAmendingLawHtmlByEli,
    }))

    const { useAmendingLawHtml } = await import("./useAmendingLawHtml")

    const eli = ref(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )
    const { html } = useAmendingLawHtml(eli)
    await vi.waitUntil(() => html.value)

    expect(html.value).toBe("<div></div>")
  })

  test("should load the amending law html when the eli changes", async () => {
    const getAmendingLawHtmlByEli = vi.fn()

    vi.doMock("@/services/amendingLawsService", () => ({
      getAmendingLawHtmlByEli,
    }))

    const { useAmendingLawHtml } = await import("./useAmendingLawHtml")

    const eli = ref(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )
    useAmendingLawHtml(eli)

    eli.value = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
    await nextTick()

    eli.value = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
    await nextTick()

    expect(getAmendingLawHtmlByEli).toBeCalledTimes(2)
  })
})
