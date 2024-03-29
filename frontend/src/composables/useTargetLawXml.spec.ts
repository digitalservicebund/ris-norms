import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useTargetLawXml", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the target law xml", async () => {
    const getTargetLawXmlByEli = vi.fn().mockResolvedValue("<xml></xml>")

    vi.doMock("@/services/targetLawsService", () => ({
      getTargetLawXmlByEli,
    }))

    const { useTargetLawXml } = await import("./useTargetLawXml")

    const eli = ref(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )
    const { xml } = useTargetLawXml(eli)
    await vi.waitUntil(() => xml.value)

    expect(xml.value).toBe("<xml></xml>")
  })

  test("should load the target law xml when the eli changes", async () => {
    const getTargetLawXmlByEli = vi.fn()

    vi.doMock("@/services/targetLawsService", () => ({
      getTargetLawXmlByEli,
    }))

    const { useTargetLawXml } = await import("./useTargetLawXml")

    const eli = ref(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )
    useTargetLawXml(eli)

    eli.value = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
    await nextTick()

    eli.value = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
    await nextTick()

    expect(getTargetLawXmlByEli).toBeCalledTimes(2)
  })

  describe("update", () => {
    test("should request an update and the XML should be update with the response", async () => {
      const getTargetLawXmlByEli = vi.fn().mockResolvedValue("<xml>1</xml>")
      const putTargetLawXml = vi
        .fn()
        .mockResolvedValueOnce("<xml some-backend-created-tag>2</xml>")

      vi.doMock("@/services/targetLawsService", () => ({
        getTargetLawXmlByEli,
        putTargetLawXml,
      }))

      const { useTargetLawXml } = await import("./useTargetLawXml")
      const { xml, update } = useTargetLawXml(
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      )

      await vi.waitUntil(() => xml.value)
      expect(xml.value).toBe("<xml>1</xml>")

      await update("<xml>2</xml>")
      expect(xml.value).toBe("<xml some-backend-created-tag>2</xml>")

      expect(putTargetLawXml).toHaveBeenCalledWith(
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        "<xml>2</xml>",
      )
    })

    test("should reject if the update fails and the XML should then not be updated", async () => {
      vi.doMock("@/services/targetLawsService", () => ({
        getTargetLawXmlByEli: vi.fn().mockResolvedValue("<xml>1</xml>"),
        putTargetLawXml: vi.fn().mockRejectedValue("404 Not Found"),
      }))

      const { useTargetLawXml } = await import("./useTargetLawXml")
      const { xml, update } = useTargetLawXml(
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      )

      await vi.waitUntil(() => xml.value)
      expect(xml.value).toBe("<xml>1</xml>")
      await expect(update("<xml>2</xml>")).rejects.toThrow("404 Not Found")
      expect(xml.value).toBe("<xml>1</xml>")
    })

    test("should reject if no ELI exists", async () => {
      vi.doMock("@/services/targetLawsService", () => ({
        getTargetLawXmlByEli: vi.fn().mockResolvedValue("<xml>1</xml>"),
        putTargetLawXml: vi.fn().mockResolvedValue("<xml>2</xml>"),
      }))

      const { useTargetLawXml } = await import("./useTargetLawXml")
      const { update } = useTargetLawXml(ref(undefined))

      await expect(update("<xml>2</xml>")).rejects.toThrow(
        "Expected an identifier to exist when calling update.",
      )
    })
  })
})
