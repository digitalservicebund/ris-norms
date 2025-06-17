import type { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { nextTick, ref } from "vue"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

vi.mock("@/lib/auth", () => {
  return {
    useAuthentication: () => ({
      addAuthorizationHeader: (init: HeadersInit) => ({ ...init }),
      tryRefresh: vi.fn().mockReturnValue(true),
    }),
  }
})

describe("useArticle", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("should provide the article", async () => {
    const fetchSpy = vi.spyOn(global, "fetch").mockResolvedValueOnce(
      new Response(
        JSON.stringify({
          eid: "article eid 1",
          title: "article eid 1",
          enumeration: "Artikel 1",
        }),
      ),
    )

    const { useArticle } = await import("./useArticle")

    const identifier = ref<LawElementIdentifier>({
      eli: DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
      ),
      eid: "art_1",
    })
    const { data: article, isFinished } = useArticle(identifier)
    await vi.waitUntil(() => isFinished.value)

    expect(article.value?.eid).toBe("article eid 1")
    expect(fetchSpy).toHaveBeenCalledWith(
      "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1/articles/art_1",
      expect.anything(),
    )
  })

  it("should load the article when the identifier changes", async () => {
    const fetchSpy = vi.spyOn(global, "fetch")

    const { useArticle } = await import("./useArticle")

    const identifier = ref<LawElementIdentifier>({ eli: undefined, eid: "" })
    useArticle(identifier)

    identifier.value = {
      eli: DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
      eid: "1",
    }
    await nextTick()

    identifier.value = {
      eli: DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
      eid: "1",
    }
    await nextTick()

    expect(fetchSpy).toBeCalledTimes(2)
  })
})
