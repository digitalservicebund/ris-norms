import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useArticle", () => {
  beforeEach(() => {
    vi.resetModules()
  })

  it("should provide the article", async () => {
    const fetchSpy = vi.spyOn(global, "fetch").mockResolvedValueOnce(
      new Response(
        JSON.stringify({
          eid: "article eid 1",
          title: "article eid 1",
          enumeration: "Artikel 1",
          affectedDocumentEli: "example/eli",
        }),
      ),
    )

    const { useArticle } = await import("./useArticle")

    const identifier = ref<LawElementIdentifier>({
      eli: "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      eid: "art_1",
    })
    const { data: article, isFinished } = useArticle(identifier)
    await vi.waitUntil(() => isFinished.value)

    expect(article.value?.eid).toBe("article eid 1")
    expect(fetchSpy).toHaveBeenCalledWith(
      "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/articles/art_1",
      expect.anything(),
    )
  })

  it("should load the article when the identifier changes", async () => {
    const fetchSpy = vi.spyOn(global, "fetch")

    const { useArticle } = await import("./useArticle")

    const identifier = ref<LawElementIdentifier>({ eli: "", eid: "" })
    useArticle(identifier)

    identifier.value = { eli: "1", eid: "1" }
    await nextTick()

    identifier.value = { eli: "1", eid: "1" }
    await nextTick()

    expect(fetchSpy).toBeCalledTimes(2)
  })
})
