import { defineStore } from "pinia"
import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"
import { createTestingPinia } from "@pinia/testing"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"

describe("useArticle", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the article", async () => {
    createTestingPinia({
      stubActions: false,
    })

    vi.doMock("@/store/articleStore", () => ({
      useArticleStore: vi.fn().mockReturnValue(
        defineStore("article", () => {
          return {
            loadedArticle: ref({
              eid: "article eid 1",
              title: "article eid 1",
              enumeration: "1",
              affectedDocumentEli: "example/eli",
            }),
            loadArticleByEliAndEid: vi.fn(),
          }
        })(),
      ),
    }))

    const { useArticle } = await import("./useArticle")

    const identifier = ref<LawElementIdentifier>({ eli: "", eid: "" })
    const article = useArticle(identifier)

    expect(article.value?.eid).toBe("article eid 1")
  })

  test("should load the article when the identifier changes", async () => {
    const loadArticleByEliAndEid = vi.fn()

    createTestingPinia({
      stubActions: false,
    })

    vi.doMock("@/store/articleStore", () => ({
      useArticleStore: vi.fn().mockReturnValue(
        defineStore("article", () => {
          return {
            loadedArticle: ref(),
            loadArticleByEliAndEid,
          }
        })(),
      ),
    }))

    const { useArticle } = await import("./useArticle")

    const identifier = ref<LawElementIdentifier>({ eli: "", eid: "" })
    useArticle(identifier)

    identifier.value = { eli: "1", eid: "1" }
    await nextTick()

    identifier.value = { eli: "1", eid: "1" }
    await nextTick()

    expect(loadArticleByEliAndEid).toBeCalledTimes(2)
  })
})
