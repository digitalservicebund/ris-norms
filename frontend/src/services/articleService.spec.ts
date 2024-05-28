import { beforeEach, describe, expect, it, vi } from "vitest"

describe("articleService", () => {
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

      const { getArticlesByEli } = await import("./articleService")

      const result = await getArticlesByEli("example/eli")

      expect(result).toEqual([
        {
          enumeration: "1",
          eid: "article/eid",
          title: "Example",
          affectedDocumentEli: "article/eli",
        },
      ])

      expect(fetchMock).toHaveBeenCalledWith("/norms/example/eli/articles", {
        query: {},
      })
    })

    it("provides the data from the API with filters", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce([
        {
          enumeration: "1",
          eid: "article/eid",
          title: "Example",
          affectedDocumentEli: "article/eli",
        },
      ])

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { getArticlesByEli } = await import("./articleService")

      const result = await getArticlesByEli("example/eli", {
        amendedAt: "foo",
        amendedBy: "bar",
      })

      expect(result).toEqual([
        {
          enumeration: "1",
          eid: "article/eid",
          title: "Example",
          affectedDocumentEli: "article/eli",
        },
      ])

      expect(fetchMock).toHaveBeenCalledWith("/norms/example/eli/articles", {
        query: { amendedAt: "foo", amendedBy: "bar" },
      })
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

      const { getArticleByEliAndEid } = await import("./articleService")

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
        "/norms/example/eli/articles/example/eid",
      )
    })
  })

  describe("getArticleXmlByEliAndEid(identifier)", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce('<?xml version="1.0" encoding="UTF-8"?></xml>')

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { getArticleXmlByEliAndEid } = await import("./articleService")

      const result = await getArticleXmlByEliAndEid({
        eli: "example/eli",
        eid: "example/eid",
      })

      expect(result).toEqual('<?xml version="1.0" encoding="UTF-8"?></xml>')

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/example/eli/articles/example/eid",
        expect.objectContaining({
          headers: expect.objectContaining({ Accept: "application/xml" }),
        }),
      )
    })
  })

  describe("getArticleRenderByEliAndEid(identifier, date)", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce("<div></div>")

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { getArticleRenderByEliAndEid } = await import("./articleService")

      const result = await getArticleRenderByEliAndEid({
        eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        eid: "hauptteil-1_art-1",
      })

      expect(result).toEqual("<div></div>")

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles/hauptteil-1_art-1",
        expect.objectContaining({
          query: {},
          headers: expect.objectContaining({ Accept: "text/html" }),
        }),
      )
    })

    it("provides the data from the api with at-date", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce("<div></div>")

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { getArticleRenderByEliAndEid } = await import("./articleService")

      const date = new Date(Date.UTC(2023, 11, 11, 1, 2, 3, 4))
      const result = await getArticleRenderByEliAndEid(
        {
          eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
          eid: "hauptteil-1_art-1",
        },
        date,
      )

      expect(result).toEqual("<div></div>")

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/articles/hauptteil-1_art-1",
        expect.objectContaining({
          query: expect.objectContaining({
            atIsoDate: "2023-12-11T01:02:03.004Z",
          }),
          headers: expect.objectContaining({ Accept: "text/html" }),
        }),
      )
    })
  })
})
