import { beforeEach, describe, expect, it, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useAffectedDocuments", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("should provide the affected documents", async () => {
    const useArticles = vi.fn().mockReturnValue({
      data: ref([
        {
          affectedDocumentEli: "example/eli1",
        },
        {
          affectedDocumentEli: "example/eli1",
        },
        {
          affectedDocumentEli: "example/eli2",
        },
        {
          affectedDocumentEli: "example/eli3",
        },
        {
          affectedDocumentEli: "example/eli4",
        },
      ]),
    })

    vi.doMock("@/services/articleService", () => ({
      useArticles,
    }))

    const { useAffectedDocuments } = await import("./useAffectedDocuments")

    const { data } = useAffectedDocuments(() => "example")
    await nextTick()

    expect(data.value).toHaveLength(4)
    expect(data.value?.[0]).toStrictEqual("example/eli1")
  })
})
