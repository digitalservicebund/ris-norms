import { beforeEach, describe, expect, it, vi } from "vitest"

describe("articlesService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("getArticlesByEli(eli)", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce([
        {
          enumeration: "1",
          eid: "article/eid",
          title: "Example",
          affectedDocumentEli: "article/eli",
        },
      ])

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { getArticlesByEli } = await import("./articlesService")

      const result = await getArticlesByEli("example/eli")

      expect(result).toEqual([
        {
          enumeration: "1",
          eid: "article/eid",
          title: "Example",
          affectedDocumentEli: "article/eli",
        },
      ])

      expect(fetchMock).toHaveBeenCalledWith(
        "/amending-laws/example/eli/articles",
      )
    })
  })

  describe("getArticleByEliAndEid(identifier)", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce({
        enumeration: "1",
        eid: "article/eid",
        title: "Example",
        affectedDocumentEli: "article/eli",
      })

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { getArticleByEliAndEid } = await import("./articlesService")

      const result = await getArticleByEliAndEid({
        eli: "example/eli",
        eid: "example/eid",
      })

      expect(result).toEqual({
        enumeration: "1",
        eid: "article/eid",
        title: "Example",
        affectedDocumentEli: "article/eli",
      })

      expect(fetchMock).toHaveBeenCalledWith(
        "/amending-laws/example/eli/articles/example/eid",
      )
    })
  })

  describe("getArticleXmlByEliAndEid(identifier)", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce('<?xml version="1.0" encoding="UTF-8"?></xml>')

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { getArticleXmlByEliAndEid } = await import("./articlesService")

      const result = await getArticleXmlByEliAndEid({
        eli: "example/eli",
        eid: "example/eid",
      })

      expect(result).toEqual('<?xml version="1.0" encoding="UTF-8"?></xml>')

      expect(fetchMock).toHaveBeenCalledWith(
        "/amending-laws/example/eli/articles/example/eid",
        expect.objectContaining({
          headers: expect.objectContaining({ Accept: "application/xml" }),
        }),
      )
    })
  })
})
