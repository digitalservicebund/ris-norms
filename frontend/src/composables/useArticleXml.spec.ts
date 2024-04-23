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

  describe("update", () => {
    test("should request an update and the xml should be update with the response", async () => {
      const getNormXmlByEli = vi.fn().mockResolvedValue("<xml>1</xml>")
      const putNormXml = vi
        .fn()
        .mockResolvedValueOnce("<xml some-backend-created-tag>2</xml>")

      vi.doMock("@/services/normService", () => ({
        getNormXmlByEli: getNormXmlByEli,
        putNormXml: putNormXml,
      }))

      const { useArticleXml } = await import("./useArticleXml")
      const { xml: article, update } = useArticleXml({
        eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        eid: "hauptteil-1_art-1",
      })

      await vi.waitUntil(() => article.value)
      expect(article.value).toBe("<xml>1</xml>")

      await update("<xml>2</xml>")
      expect(article.value).toBe("<xml some-backend-created-tag>2</xml>")

      expect(putNormXml).toHaveBeenCalledWith(
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        "<xml>2</xml>",
      )
    })

    test("should reject if the update fails and the xml should then not be updated", async () => {
      vi.doMock("@/services/normService", () => ({
        getNormXmlByEli: vi.fn().mockResolvedValue("<xml>1</xml>"),
        putNormXml: vi.fn().mockRejectedValue("404 Not Found"),
      }))

      const { useArticleXml } = await import("./useArticleXml")
      const { xml: article, update } = useArticleXml({
        eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        eid: "hauptteil-1_art-1",
      })

      await vi.waitUntil(() => article.value)
      expect(article.value).toBe("<xml>1</xml>")
      await expect(update("<xml>2</xml>")).rejects.toThrow("404 Not Found")
      expect(article.value).toBe("<xml>1</xml>")
    })

    test("should reject if no identifier exists", async () => {
      vi.doMock("@/services/normService", () => ({
        getNormXmlByEli: vi.fn().mockResolvedValue("<xml>1</xml>"),
        putNormXml: vi.fn().mockResolvedValue("<xml>2</xml>"),
      }))

      const { useArticleXml } = await import("./useArticleXml")
      const { update } = useArticleXml(ref(undefined))

      await expect(update("<xml>2</xml>")).rejects.toThrow(
        "Expected an identifier to exist when calling update.",
      )
    })
  })
})
