import { beforeEach, describe, expect, it, vi } from "vitest"

describe("elementService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("getElementsByEliAndType", () => {
    it("provides the data from the API", async () => {
      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce([
          { eid: "article-eid", title: "Example", type: "article" },
        ])

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { getElementsByEliAndType } = await import("./elementService")

      const result = await getElementsByEliAndType("example/eli", ["article"])

      expect(result).toEqual([
        { eid: "article-eid", title: "Example", type: "article" },
      ])

      expect(fetchMock).toHaveBeenCalledWith("/norms/example/eli/elements", {
        query: {
          type: ["article"],
        },
      })
    })

    it("provides the data from the API with filters", async () => {
      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce([
          { eid: "article-eid", title: "Example", type: "article" },
        ])

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { getElementsByEliAndType } = await import("./elementService")

      const result = await getElementsByEliAndType("example/eli", ["article"], {
        amendedBy: "example/eli2",
      })

      expect(result).toEqual([
        { eid: "article-eid", title: "Example", type: "article" },
      ])

      expect(fetchMock).toHaveBeenCalledWith("/norms/example/eli/elements", {
        query: {
          type: ["article"],
          amendedBy: "example/eli2",
        },
      })
    })
  })

  describe("getElementHtmlByEliAndEid", () => {
    it("provides the data from the API", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce(`<div></div>`)

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { getElementHtmlByEliAndEid } = await import("./elementService")

      const result = await getElementHtmlByEliAndEid("example/eli", "eid-1")

      expect(result).toEqual(`<div></div>`)

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/example/eli/elements/eid-1",
        { headers: { Accept: "text/html" }, query: { atIsoDate: undefined } },
      )
    })

    it("loads the data with an ISO date", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce(`<div></div>`)

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { getElementHtmlByEliAndEid } = await import("./elementService")

      const result = await getElementHtmlByEliAndEid("example/eli", "eid-1", {
        at: new Date(2024, 3, 5),
      })

      expect(result).toEqual(`<div></div>`)

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/example/eli/elements/eid-1",
        {
          headers: { Accept: "text/html" },
          query: { atIsoDate: "2024-04-05T00:00:00.000Z" },
        },
      )
    })
  })

  describe("getElementByEliAndEid", () => {
    it("provides the data from the API", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce({
        eid: "article-eid",
        title: "Example",
        type: "article",
      })

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { getElementByEliAndEid } = await import("./elementService")

      const result = await getElementByEliAndEid("example/eli", "article-eid")

      expect(result).toEqual({
        eid: "article-eid",
        title: "Example",
        type: "article",
      })

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/example/eli/elements/article-eid",
      )
    })
  })
})
