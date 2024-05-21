import { LawElementIdentifier } from "@/types/lawElementIdentifier"
import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useElementHtml", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the element", async () => {
    const getElementHtmlByEliAndEid = vi.fn().mockResolvedValue("<div></div>")

    vi.doMock("@/services/elementsService", () => ({
      getElementHtmlByEliAndEid,
    }))

    const { useElementHtml } = await import("./useElementHtml")

    const element = useElementHtml({
      eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      eid: "hauptteil-1_art-1",
    })

    await vi.waitUntil(() => element.value)

    expect(element.value).toBe("<div></div>")
    expect(getElementHtmlByEliAndEid).toHaveBeenCalled()
  })

  test("should load the element when the identifier changes", async () => {
    const getElementHtmlByEliAndEid = vi.fn()

    vi.doMock("@/services/elementsService", () => ({
      getElementHtmlByEliAndEid,
    }))

    const { useElementHtml } = await import("./useElementHtml")

    const identifier = ref<LawElementIdentifier>({
      eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
      eid: "hauptteil-1_art-1",
    })

    useElementHtml(identifier)

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

    expect(getElementHtmlByEliAndEid).toBeCalledTimes(3)
  })
})
