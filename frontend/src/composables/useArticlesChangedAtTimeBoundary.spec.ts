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

    const { useAffectedArticles } = await import("./useAffectedArticles")

    const articles = useAffectedArticles(
      ref("eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1"),
      ref("unknown-eid-1"),
      ref("eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1"),
    )

    await vi.waitUntil(() => articles.value)

    expect(articles.value).toHaveLength(1)
    expect(articles.value?.[0].eid).toBe("hauptteil-1_para-9")
    expect(getArticlesByEli).toHaveBeenCalled()
  })

  test("should load the articles when the identifiers change", async () => {
    const getArticlesByEli = vi.fn()
    vi.doMock("@/services/articlesService", () => ({
      getArticlesByEli: getArticlesByEli,
    }))

    const { useAffectedArticles } = await import("./useAffectedArticles")

    const eli = ref(
      "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1",
    )
    const timeBoundaryEid = ref("unknown-eid-1")
    const amendingLawEli = ref(
      "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1",
    )
    useAffectedArticles(eli, timeBoundaryEid, amendingLawEli)

    eli.value = "eli/bund/bgbl-1/1964/s593/2020-03-15/1/deu/regelungstext-1"
    await nextTick()

    timeBoundaryEid.value = "unknown-eid-2"
    await nextTick()

    timeBoundaryEid.value = "unknown-eid-2"
    await nextTick()

    amendingLawEli.value =
      "eli/bund/bgbl-1/1964/s593/2020-03-15/1/deu/regelungstext-1"
    await nextTick()

    expect(getArticlesByEli).toBeCalledTimes(4)
  })

  test("should do nothing if ELI is an empty string", async () => {
    const getArticlesByEli = vi.fn()
    vi.doMock("@/services/articlesService", () => ({
      getArticlesByEli: getArticlesByEli,
    }))

    const { useAffectedArticles } = await import("./useAffectedArticles")

    useAffectedArticles(
      ref(""),
      ref("unknown-eid-2"),
      ref("eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1"),
    )

    expect(getArticlesByEli).not.toHaveBeenCalled()
  })

  test("should do nothing if time boundary and amending law ELI are empty strings", async () => {
    const getArticlesByEli = vi.fn()
    vi.doMock("@/services/articlesService", () => ({
      getArticlesByEli: getArticlesByEli,
    }))

    const { useAffectedArticles } = await import("./useAffectedArticles")

    useAffectedArticles(
      ref("eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1"),
      ref(""),
      ref(""),
    )

    expect(getArticlesByEli).not.toHaveBeenCalled()
  })

  test("should load the articles if only a time boundary is specified", async () => {
    const getArticlesByEli = vi.fn()
    vi.doMock("@/services/articlesService", () => ({
      getArticlesByEli: getArticlesByEli,
    }))

    const { useAffectedArticles } = await import("./useAffectedArticles")

    useAffectedArticles(
      ref("eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1"),
      ref(undefined),
      ref("foo"),
    )

    expect(getArticlesByEli).toHaveBeenCalledWith(
      "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1",
      { amendedAt: undefined, amendedBy: "foo" },
    )
  })

  test("should load the articles if only an amending law is specified", async () => {
    const getArticlesByEli = vi.fn()
    vi.doMock("@/services/articlesService", () => ({
      getArticlesByEli: getArticlesByEli,
    }))

    const { useAffectedArticles } = await import("./useAffectedArticles")

    useAffectedArticles(
      ref("eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1"),
      ref("foo"),
      ref(undefined),
    )

    expect(getArticlesByEli).toHaveBeenCalledWith(
      "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1",
      { amendedAt: "foo", amendedBy: undefined },
    )
  })
})
