import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useArticleHtml", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the article", async () => {
    const getArticleRenderByEliAndEid = vi.fn().mockResolvedValue("<div></div>")

    vi.doMock("@/services/articlesService", () => ({
      getArticleRenderByEliAndEid: getArticleRenderByEliAndEid,
    }))

    const { useArticleHtml } = await import("./useArticleHtml")

    const identifier = ref<LawElementIdentifier>({ eli: "", eid: "" })
    const article = useArticleHtml(identifier)

    await vi.waitUntil(() => article.value)

    expect(article.value).toBe("<div></div>")
    expect(getArticleRenderByEliAndEid).toHaveBeenCalled()
  })

  test("should load the article when the identifier changes", async () => {
    const getArticleRenderByEliAndEid = vi.fn()

    vi.doMock("@/services/articlesService", () => ({
      getArticleRenderByEliAndEid: getArticleRenderByEliAndEid,
    }))

    const { useArticleHtml } = await import("./useArticleHtml")

    const identifier = ref<LawElementIdentifier>({ eli: "", eid: "" })
    useArticleHtml(identifier)

    identifier.value = { eli: "1", eid: "1" }
    await nextTick()

    identifier.value = { eli: "1", eid: "1" }
    await nextTick()

    expect(getArticleRenderByEliAndEid).toBeCalledTimes(2)
  })
})
