import { defineStore } from "pinia"
import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"
import { createTestingPinia } from "@pinia/testing"

describe("useTargetLaw", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the target law", async () => {
    createTestingPinia({
      stubActions: false,
    })

    vi.doMock("@/store/targetLawStore", () => ({
      useTargetLawStore: vi.fn().mockReturnValue(
        defineStore("target-law", () => {
          return {
            targetLaw: ref({
              eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
              title:
                "Gesetz über die Zusammenarbeit des Bundes und der Länder in Angelegenheiten des Verfassungsschutzes und über das Bundesamt für Verfassungsschutz",
            }),
            loadTargetLawByEli: vi.fn(),
          }
        })(),
      ),
    }))

    const { useTargetLaw } = await import("./useTargetLaw")

    const eli = ref(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )
    const targetLaw = useTargetLaw(eli)

    expect(targetLaw.value?.eli).toBe(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )
    expect(targetLaw.value?.title).toBe(
      "Gesetz über die Zusammenarbeit des Bundes und der Länder in Angelegenheiten des Verfassungsschutzes und über das Bundesamt für Verfassungsschutz",
    )
  })

  test("should load the target law when the eli changes", async () => {
    const loadTargetLawByEli = vi.fn()

    createTestingPinia({
      stubActions: false,
    })

    vi.doMock("@/store/targetLawStore", () => ({
      useTargetLawStore: vi.fn().mockReturnValue(
        defineStore("target-law", () => {
          return {
            targetLaw: ref(),
            loadTargetLawByEli,
          }
        })(),
      ),
    }))

    const { useTargetLaw } = await import("./useTargetLaw")

    const eli = ref(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )
    useTargetLaw(eli)

    eli.value = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
    await nextTick()

    eli.value = "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"
    await nextTick()

    expect(loadTargetLawByEli).toBeCalledTimes(2)
  })
})
