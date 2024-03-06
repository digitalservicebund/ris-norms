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
    const article = useArticleXml(identifier)

    await vi.waitUntil(() => article.value, { timeout: 100, interval: 20 })

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
})
