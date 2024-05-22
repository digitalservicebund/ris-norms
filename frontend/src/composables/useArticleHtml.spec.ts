import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useArticleHtml", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the article", async () => {
    const getArticleRenderByEliAndEid = vi.fn().mockResolvedValue("<div></div>")

    vi.doMock("@/services/articleService", () => ({
      getArticleRenderByEliAndEid: getArticleRenderByEliAndEid,
    }))

    const { useArticleHtml } = await import("./useArticleHtml")

    const article = useArticleHtml(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      "hauptteil-1_art-1",
    )

    await vi.waitUntil(() => article.value)

    expect(article.value).toBe("<div></div>")
    expect(getArticleRenderByEliAndEid).toHaveBeenCalled()
  })

  test("should load the article when the identifier changes", async () => {
    const getArticleRenderByEliAndEid = vi.fn()

    vi.doMock("@/services/articleService", () => ({
      getArticleRenderByEliAndEid: getArticleRenderByEliAndEid,
    }))

    const { useArticleHtml } = await import("./useArticleHtml")

    const eli = ref(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )
    const eid = ref("hauptteil-1_art-1")
    useArticleHtml(eli, eid)

    eli.value = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-2"
    await nextTick()

    eli.value = "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-2"
    await nextTick()

    expect(getArticleRenderByEliAndEid).toBeCalledTimes(2)
  })

  test("should load the article when the date changes", async () => {
    const getArticleRenderByEliAndEid = vi.fn()

    vi.doMock("@/services/articleService", () => ({
      getArticleRenderByEliAndEid: getArticleRenderByEliAndEid,
    }))

    const { useArticleHtml } = await import("./useArticleHtml")

    const date = ref(new Date())
    useArticleHtml(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      "hauptteil-1_art-1",
      date,
    )

    date.value = new Date()
    await nextTick()

    expect(getArticleRenderByEliAndEid).toBeCalledTimes(2)
  })
})
