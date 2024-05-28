import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useArticle", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the article", async () => {
    const getArticleByEliAndEid = vi.fn().mockResolvedValue({
      eid: "article eid 1",
      title: "article eid 1",
      enumeration: "1",
      affectedDocumentEli: "example/eli",
    })

    vi.doMock("@/services/articleService", () => ({
      getArticleByEliAndEid,
    }))

    const { useArticle } = await import("./useArticle")

    const identifier = ref<LawElementIdentifier>({ eli: "", eid: "" })
    const article = useArticle(identifier)

    await vi.waitUntil(() => article.value)

    expect(article.value?.eid).toBe("article eid 1")
    expect(getArticleByEliAndEid).toHaveBeenCalled()
  })

  test("should load the article when the identifier changes", async () => {
    const getArticleByEliAndEid = vi.fn()

    vi.doMock("@/services/articleService", () => ({
      getArticleByEliAndEid,
    }))

    const { useArticle } = await import("./useArticle")

    const identifier = ref<LawElementIdentifier>({ eli: "", eid: "" })
    useArticle(identifier)

    identifier.value = { eli: "1", eid: "1" }
    await nextTick()

    identifier.value = { eli: "1", eid: "1" }
    await nextTick()

    expect(getArticleByEliAndEid).toBeCalledTimes(2)
  })
})
