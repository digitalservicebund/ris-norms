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
    const xml = useTargetLawXml(eli)
    // wait for the getTargetLawXmlByEli promise to be resolved
    await vi.waitUntil(() => xml.value, {
      timeout: 100,
      interval: 20,
    })

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
})
