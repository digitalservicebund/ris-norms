import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useArticlesChangedAtTimeBoundary", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the articles", async () => {
    const getArticlesByEli = vi.fn().mockReturnValue([
      {
        eid: "hauptteil-1_para-9",
        title: "Kennzeichenverbot",
        enumeration: "9",
      },
    ])
    vi.doMock("@/services/articlesService", () => ({
      getArticlesByEli: getArticlesByEli,
    }))

    const { useArticlesChangedAtTimeBoundary } = await import(
      "./useArticlesChangedAtTimeBoundary"
    )

    const articles = useArticlesChangedAtTimeBoundary(
      ref("eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1"),
      ref("unknown-eid-1"),
    )

    await vi.waitUntil(() => articles.value)

    expect(articles.value).toHaveLength(1)
    expect(articles.value?.[0].eid).toBe("hauptteil-1_para-9")
    expect(getArticlesByEli).toHaveBeenCalled()
  })

  test("should load the article when the identifier changes", async () => {
    const getArticlesByEli = vi.fn()
    vi.doMock("@/services/articlesService", () => ({
      getArticlesByEli: getArticlesByEli,
    }))

    const { useArticlesChangedAtTimeBoundary } = await import(
      "./useArticlesChangedAtTimeBoundary"
    )

    const eli = ref(
      "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1",
    )
    const timeBoundaryEid = ref("unknown-eid-1")
    useArticlesChangedAtTimeBoundary(eli, timeBoundaryEid)

    eli.value = "eli/bund/bgbl-1/1964/s593/2020-03-15/1/deu/regelungstext-1"
    await nextTick()

    timeBoundaryEid.value = "unknown-eid-2"
    await nextTick()

    timeBoundaryEid.value = "unknown-eid-2"
    await nextTick()

    expect(getArticlesByEli).toBeCalledTimes(3)
  })

  test("should do nothing if time boundary is an empty string", async () => {
    const getArticlesByEli = vi.fn()
    vi.doMock("@/services/articlesService", () => ({
      getArticlesByEli: getArticlesByEli,
    }))

    const { useArticlesChangedAtTimeBoundary } = await import(
      "./useArticlesChangedAtTimeBoundary"
    )

    useArticlesChangedAtTimeBoundary(
      ref("eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1"),
      ref(""),
    )

    expect(getArticlesByEli).not.toHaveBeenCalled()
  })
})
