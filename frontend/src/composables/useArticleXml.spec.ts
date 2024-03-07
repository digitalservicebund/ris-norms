import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useArticle", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the article", async () => {
    const getArticleXmlByEliAndEid = vi.fn().mockResolvedValue("<xml></xml>")

    vi.doMock("@/services/amendingLawsService", () => ({
      getAmendingLawXmlByEli: getArticleXmlByEliAndEid,
    }))

    const { useArticleXml } = await import("./useArticleXml")

    const identifier = ref<LawElementIdentifier>({ eli: "", eid: "" })
    const { xml: article } = useArticleXml(identifier)

    await vi.waitUntil(() => article.value)

    expect(article.value).toBe("<xml></xml>")
    expect(getArticleXmlByEliAndEid).toHaveBeenCalled()
  })

  test("should load the article when the identifier changes", async () => {
    const getArticleXmlByEliAndEid = vi.fn()

    vi.doMock("@/services/amendingLawsService", () => ({
      getAmendingLawXmlByEli: getArticleXmlByEliAndEid,
    }))

    const { useArticleXml } = await import("./useArticleXml")

    const identifier = ref<LawElementIdentifier>({ eli: "", eid: "" })
    useArticleXml(identifier)

    identifier.value = { eli: "1", eid: "1" }
    await nextTick()

    identifier.value = { eli: "1", eid: "1" }
    await nextTick()

    expect(getArticleXmlByEliAndEid).toBeCalledTimes(2)
  })

  test("should load the article when refresh is called even if the identifier hasn't changed", async () => {
    const getArticleXmlByEliAndEid = vi
      .fn()
      .mockResolvedValueOnce("<xml>1</xml>")
      .mockResolvedValueOnce("<xml>2</xml>")

    vi.doMock("@/services/amendingLawsService", () => ({
      getAmendingLawXmlByEli: getArticleXmlByEliAndEid,
    }))

    const { useArticleXml } = await import("./useArticleXml")
    const { xml: article, refresh } = useArticleXml({
      eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      eid: "hauptteil-1_art-1",
    })

    await vi.waitUntil(() => article.value)
    expect(article.value).toBe("<xml>1</xml>")

    await refresh()
    expect(article.value).toBe("<xml>2</xml>")

    expect(getArticleXmlByEliAndEid).toHaveBeenCalledTimes(2)
  })
})
