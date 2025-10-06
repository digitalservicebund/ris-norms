import { beforeEach, describe, expect, it, vi } from "vitest"
import { nextTick, ref } from "vue"
import { useArticles } from "@/services/articleService"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

vi.mock("@/lib/auth", () => {
  return {
    useAuthentication: () => ({
      addAuthorizationHeader: (init: HeadersInit) => ({ ...init }),
      tryRefresh: vi.fn().mockReturnValue(true),
    }),
  }
})

describe("articleService", () => {
  describe("useArticles", () => {
    beforeEach(() => {
      vi.resetAllMocks()
    })

    it("should provide the articles", async () => {
      const fetchSpy = vi.spyOn(global, "fetch").mockResolvedValueOnce(
        new Response(
          JSON.stringify([
            {
              eid: "article eid 1",
              title: "article eid 1",
              enumeration: "Artikel 1",
            },
          ]),
        ),
      )

      const eli = ref<DokumentExpressionEli>(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1",
        ),
      )
      const { data: articles, isFinished } = useArticles(eli)
      await vi.waitUntil(() => isFinished.value)

      expect(articles.value).toEqual([
        {
          eid: "article eid 1",
          title: "article eid 1",
          enumeration: "Artikel 1",
        },
      ])

      expect(fetchSpy).toHaveBeenCalledExactlyOnceWith(
        `/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1/articles`,
        expect.objectContaining({
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
        }),
      )
    })

    it("should load the articles when the ELI changes", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValueOnce(new Response())

      const eli = ref<DokumentExpressionEli>(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )
      const { isFinished } = useArticles(eli)
      await nextTick()
      await vi.waitFor(() => isFinished.value)

      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1",
      )
      await nextTick()
      await vi.waitFor(() => isFinished.value)

      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1",
      )
      await nextTick()
      await vi.waitFor(() => isFinished.value)

      expect(fetchSpy).toBeCalledTimes(2)
    })
  })
})
