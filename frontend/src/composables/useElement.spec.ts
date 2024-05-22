import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useElement", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the element", async () => {
    const getElementByEliAndEid = vi.fn().mockResolvedValue({
      eid: "article-eid",
      title: "Example",
      type: "article",
    })

    vi.doMock("@/services/elementsService", () => ({
      getElementByEliAndEid,
    }))

    const { useElement } = await import("./useElement")

    const element = useElement({
      eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      eid: "hauptteil-1_art-1",
    })

    await vi.waitUntil(() => element.value)

    expect(element.value).toEqual({
      eid: "article-eid",
      title: "Example",
      type: "article",
    })
    expect(getElementByEliAndEid).toHaveBeenCalled()
  })

  test("should load the element when the identifier changes", async () => {
    const getElementByEliAndEid = vi.fn()

    vi.doMock("@/services/elementsService", () => ({
      getElementByEliAndEid,
    }))

    const { useElement } = await import("./useElement")

    const identifier = ref<LawElementIdentifier>({
      eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      eid: "hauptteil-1_art-1",
    })

    useElement(identifier)

    identifier.value = {
      ...identifier.value,
      eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-2",
    }
    await nextTick()

    identifier.value = {
      ...identifier.value,
      eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-2",
    }
    await nextTick()

    identifier.value = {
      ...identifier.value,
      eid: "hauptteil-1_art-2",
    }
    await nextTick()

    expect(getElementByEliAndEid).toBeCalledTimes(3)
  })
})
