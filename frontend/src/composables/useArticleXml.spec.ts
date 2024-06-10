import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useArticleXml", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the article", async () => {
    const getNormXmlByEli = vi.fn().mockResolvedValue("<xml></xml>")

    vi.doMock("@/services/normService", () => ({
      getNormXmlByEli: getNormXmlByEli,
    }))

    const { useArticleXml } = await import("./useArticleXml")

    const identifier = ref<LawElementIdentifier>({ eli: "", eid: "" })
    const { xml: article } = useArticleXml(identifier)

    await vi.waitUntil(() => article.value)

    expect(article.value).toBe("<xml></xml>")
    expect(getNormXmlByEli).toHaveBeenCalled()
  })

  test("should load the article when the identifier changes", async () => {
    const getNormXmlByEli = vi.fn()

    vi.doMock("@/services/normService", () => ({
      getNormXmlByEli: getNormXmlByEli,
    }))

    const { useArticleXml } = await import("./useArticleXml")

    const identifier = ref<LawElementIdentifier>({ eli: "", eid: "" })
    useArticleXml(identifier)

    identifier.value = { eli: "1", eid: "1" }
    await nextTick()

    identifier.value = { eli: "1", eid: "1" }
    await nextTick()

    expect(getNormXmlByEli).toBeCalledTimes(2)
  })
})
