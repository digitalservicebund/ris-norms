import { defineStore } from "pinia"
import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"
import { createTestingPinia } from "@pinia/testing"

describe("useAmendingLaw", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the amending law", async () => {
    createTestingPinia({
      stubActions: false,
    })

    vi.doMock("@/store/loadAmendingLawStore", () => ({
      useAmendingLawStore: vi.fn().mockReturnValue(
        defineStore("loaded-amending-law", () => {
          return {
            loadedAmendingLaw: ref({
              eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
              printAnnouncementGazette: "example",
              digitalAnnouncementMedium: undefined,
              publicationDate: "2023-01-01",
              printAnnouncementPage: "1",
              digitalAnnouncementEdition: undefined,
              articles: [
                {
                  eli: "article eli 1",
                  title: "article eli 1",
                  enumeration: "1",
                },
              ],
            }),
            loadAmendingLawByEli: vi.fn(),
          }
        })(),
      ),
    }))

    const { useAmendingLaw } = await import("./useAmendingLaw")

    const eli = ref(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
    )
    const amendingLaw = useAmendingLaw(eli)

    expect(amendingLaw.value?.eli).toBe(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
    )
    expect(amendingLaw.value?.publicationDate).toBe("2023-01-01")
  })

  test("should load the amending law when the eli changes", async () => {
    const loadAmendingLawByEli = vi.fn()

    createTestingPinia({
      stubActions: false,
    })

    vi.doMock("@/store/loadAmendingLawStore", () => ({
      useAmendingLawStore: vi.fn().mockReturnValue(
        defineStore("loaded-amending-law", () => {
          return {
            loadedAmendingLaw: ref(),
            loadAmendingLawByEli,
          }
        })(),
      ),
    }))

    const { useAmendingLaw } = await import("./useAmendingLaw")

    const eli = ref(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
    )
    useAmendingLaw(eli)

    eli.value = "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1"
    await nextTick()

    eli.value = "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1"
    await nextTick()

    expect(loadAmendingLawByEli).toBeCalledTimes(2)
  })
})
