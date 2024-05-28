import { ElementType } from "@/types/element"
import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useAffectedElements", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the elements", async () => {
    const getElementsByEliAndType = vi
      .fn()
      .mockReturnValue([
        { eid: "article-1", title: "Heading", type: "article" },
      ])
    vi.doMock("@/services/elementService", () => ({ getElementsByEliAndType }))

    const { useAffectedElements } = await import("./useAffectedElements")

    const elements = useAffectedElements(
      ref("eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1"),
      ["article"],
    )

    await vi.waitUntil(() => elements.value)

    expect(elements.value).toHaveLength(1)
    expect(elements.value?.[0].eid).toBe("article-1")
    expect(getElementsByEliAndType).toHaveBeenCalled()
  })

  test("should load the elements when the identifiers change", async () => {
    const getElementsByEliAndType = vi.fn()
    vi.doMock("@/services/elementService", () => ({ getElementsByEliAndType }))

    const { useAffectedElements } = await import("./useAffectedElements")

    const eli = ref(
      "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1",
    )
    const types = ref<ElementType[]>(["article"])
    const amendingLawEli = ref(
      "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1",
    )
    useAffectedElements(eli, types, { amendingLawEli })

    eli.value = "eli/bund/bgbl-1/1964/s593/2020-03-15/1/deu/regelungstext-1"
    await nextTick()

    types.value = ["article", "conclusions"]
    await nextTick()

    types.value = ["article"]
    await nextTick()

    amendingLawEli.value =
      "eli/bund/bgbl-1/1964/s593/2020-03-15/1/deu/regelungstext-1"
    await nextTick()

    expect(getElementsByEliAndType).toBeCalledTimes(5)
  })

  test("should do nothing if ELI is an empty string", async () => {
    const getElementsByEliAndType = vi.fn()
    vi.doMock("@/services/elementService", () => ({ getElementsByEliAndType }))

    const { useAffectedElements } = await import("./useAffectedElements")

    useAffectedElements(ref(""), ref(["article"]))

    expect(getElementsByEliAndType).not.toHaveBeenCalled()
  })

  test("should do nothing if types are empty", async () => {
    const getElementsByEliAndType = vi.fn()
    vi.doMock("@/services/elementService", () => ({ getElementsByEliAndType }))

    const { useAffectedElements } = await import("./useAffectedElements")

    useAffectedElements(
      ref("eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1"),
      ref([]),
    )

    expect(getElementsByEliAndType).not.toHaveBeenCalled()
  })

  test("should load the elements if an amending law is specified", async () => {
    const getElementsByEliAndType = vi.fn()
    vi.doMock("@/services/elementService", () => ({ getElementsByEliAndType }))

    const { useAffectedElements } = await import("./useAffectedElements")

    useAffectedElements(
      ref("eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1"),
      ref(["article"]),
      { amendingLawEli: ref("foo") },
    )

    expect(getElementsByEliAndType).toHaveBeenCalledWith(
      "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1",
      ["article"],
      { amendedBy: "foo" },
    )
  })
})
