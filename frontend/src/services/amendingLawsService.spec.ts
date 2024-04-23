import { describe, it, expect, vi, beforeEach } from "vitest"

describe("amendingLawsService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("getAmendingLawByEli(eli)", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce({
        eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        printAnnouncementGazette: "bgbl-1",
        printAnnouncementMedium: undefined,
        publicationDate: "2017-03-15",
        printAnnouncementPage: "419",
        digitalAnnouncementEdition: undefined,
      })

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { getAmendingLawByEli } = await import("./amendingLawsService")

      const result = await getAmendingLawByEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )
      expect(result.eli).toBe(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )
      expect(result.publicationDate).toBe("2017-03-15")

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )
    })
  })

  describe("getAmendingLaws()", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce([
        {
          eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
          printAnnouncementGazette: "bgbl-1",
          printAnnouncementMedium: undefined,
          publicationDate: "2017-03-15",
          printAnnouncementPage: "419",
          digitalAnnouncementEdition: undefined,
          articles: [
            {
              eid: "article eid 1",
              title: "article eid 1",
              enumeration: "1",
            },
            {
              eid: "article eid 2",
              title: "article eli 2",
              enumeration: "2",
            },
          ],
        },
      ])

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { getAmendingLaws } = await import("./amendingLawsService")

      const result = await getAmendingLaws()
      expect(result.length).toBe(1)
      expect(result[0].eli).toBe(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )

      expect(fetchMock).toHaveBeenCalledWith("/announcements")
    })
  })

  describe("getAmendingLawXmlByEli(eli)", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce(`<?xml version="1.0" encoding="UTF-8"?></xml>`)

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { getAmendingLawXmlByEli } = await import("./amendingLawsService")

      const result = await getAmendingLawXmlByEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )
      expect(result).toBe('<?xml version="1.0" encoding="UTF-8"?></xml>')

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "application/xml",
          }),
        }),
      )
    })
  })

  describe("getAmendingLawHtmlByEli(eli)", () => {
    it("provides the data from the api", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce(`<div></div>`)

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { getAmendingLawHtmlByEli } = await import("./amendingLawsService")

      const result = await getAmendingLawHtmlByEli(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      )
      expect(result).toBe("<div></div>")

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "text/html",
          }),
        }),
      )
    })
  })

  describe("putAmendingLawXml(eli, xml)", () => {
    it("sends the data to the api", async () => {
      const fetchMock = vi
        .fn()
        .mockResolvedValueOnce('<?xml version="1.0" encoding="UTF-8"?></xml>')

      vi.doMock("./apiService.ts", () => ({
        apiFetch: fetchMock,
      }))

      const { putAmendingLawXml } = await import("./amendingLawsService")

      const result = await putAmendingLawXml(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        '<?xml version="1.0" encoding="UTF-8"?></xml>',
      )
      expect(result).toBe('<?xml version="1.0" encoding="UTF-8"?></xml>')

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
        expect.objectContaining({
          method: "PUT",
          headers: expect.objectContaining({
            "Content-Type": "application/xml",
            Accept: "application/xml",
          }),
          body: '<?xml version="1.0" encoding="UTF-8"?></xml>',
        }),
      )
    })
  })
})
