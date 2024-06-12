import { describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"
import { useArticles } from "@/services/articleService"

describe("articleService", () => {
  describe("useArticles", () => {
    test("should provide the articles", async () => {
      const fetchSpy = vi.spyOn(global, "fetch").mockResolvedValueOnce(
        new Response(
          JSON.stringify([
            {
              eid: "article eid 1",
              title: "article eid 1",
              enumeration: "1",
              affectedDocumentEli: "example/eli",
            },
          ]),
        ),
      )

      const eli = ref<string>(
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      )
      const { data: articles, isFinished } = useArticles(eli)
      await vi.waitUntil(() => isFinished.value)

      expect(articles.value).toEqual([
        {
          eid: "article eid 1",
          title: "article eid 1",
          enumeration: "1",
          affectedDocumentEli: "example/eli",
        },
      ])

      expect(fetchSpy).toHaveBeenCalledWith(
        `/api/v1/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles`,
        expect.objectContaining({
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
        }),
      )
    })

    test("should load the articles when the ELI changes", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValueOnce(new Response())

      const eli = ref<string>("0")
      const { isFinished } = useArticles(eli)
      await nextTick()
      await vi.waitFor(() => isFinished.value)

      eli.value = "1"
      await nextTick()
      await vi.waitFor(() => isFinished.value)

      eli.value = "1"
      await nextTick()
      await vi.waitFor(() => isFinished.value)

      expect(fetchSpy).toBeCalledTimes(2)
    })
  })
})
