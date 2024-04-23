import { describe, it, expect, beforeEach, vi } from "vitest"
import { setActivePinia, createPinia } from "pinia"
import { nextTick } from "vue"

describe("useTargetLawsStore", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  beforeEach(() => {
    setActivePinia(createPinia())
  })

  it("loads amending laws correctly", async () => {
    const getNormByEli = vi.fn().mockResolvedValue({
      eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      title:
        "Gesetz über die Zusammenarbeit des Bundes und der Länder in Angelegenheiten des Verfassungsschutzes und über das Bundesamt für Verfassungsschutz",
    })

    vi.doMock("@/services/normService", () => ({
      getNormByEli,
    }))

    const { useTargetLawStore } = await import("./targetLawStore")

    const store = useTargetLawStore()

    store.loadTargetLawByEli(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )
    await nextTick()

    expect(store.loading).toBe(true)
    expect(getNormByEli).toHaveBeenCalledOnce()

    await vi.waitUntil(() => !store.loading)

    expect(store.loading).toBe(false)
    expect(store.targetLaw?.eli).toEqual(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )
    expect(store.targetLaw?.title).toEqual(
      "Gesetz über die Zusammenarbeit des Bundes und der Länder in Angelegenheiten des Verfassungsschutzes und über das Bundesamt für Verfassungsschutz",
    )
  })
})
