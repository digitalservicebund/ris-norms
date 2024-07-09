import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("useAffectedDocuments", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the affected documents", async () => {
    const useArticles = vi.fn().mockReturnValue({
      data: ref([
        {
          affectedDocumentEli: "example/eli1",
          affectedDocumentZf0Eli: "example/zf0/eli1",
        },
        {
          affectedDocumentEli: "example/eli2",
          affectedDocumentZf0Eli: "example/zf0/eli2",
        },
        {
          affectedDocumentEli: "example/eli3",
          affectedDocumentZf0Eli: "example/zf0/eli3",
        },
        {
          affectedDocumentEli: "example/eli4",
          affectedDocumentZf0Eli: "example/zf0/eli4",
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
    expect(data.value?.[0]).toStrictEqual({
      eli: "example/eli1",
      zf0Eli: "example/zf0/eli1",
    })
  })
})
