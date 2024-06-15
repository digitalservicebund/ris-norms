import { Article } from "@/types/article"
import { beforeEach, describe, expect, test, vi } from "vitest"
import { MaybeRefOrGetter, nextTick, ref, toValue, watch } from "vue"

describe("useAffectedDocuments", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the affected documents", async () => {
    const useArticles = vi.fn().mockReturnValue({
      data: ref([
        { affectedDocumentEli: "example/eli1" },
        { affectedDocumentEli: "example/eli2" },
        { affectedDocumentEli: "example/eli3" },
        { affectedDocumentEli: "example/eli4" },
      ]),
    })

    vi.doMock("@/services/articleService", () => ({
      useArticles,
    }))

    const getNormByEli = vi
      .fn()
      .mockResolvedValue({ title: "affected document", eli: "example/eli" })

    vi.doMock("@/services/normService", () => ({
      getNormByEli,
    }))

    const { useAffectedDocuments } = await import("./useAffectedDocuments")

    const documents = useAffectedDocuments(() => "example")

    await vi.waitUntil(() => documents.value.length)

    expect(documents.value).toHaveLength(4)
    expect(getNormByEli).toHaveBeenCalled()
  })

  test("should load the affected documents when the ELI changes", async () => {
    const useArticles = vi
      .fn()
      .mockImplementation((eli: MaybeRefOrGetter<string>) => {
        const articles = ref<Partial<Article>[]>([
          { affectedDocumentEli: "example/eli1" },
        ])

        watch(
          () => toValue(eli),
          () => {
            articles.value = [
              { affectedDocumentEli: "example/eli2" },
              { affectedDocumentEli: "example/eli3" },
            ]
          },
        )

        return { data: articles }
      })

    vi.doMock("@/services/articleService", () => ({
      useArticles,
    }))

    const getNormByEli = vi
      .fn()
      .mockResolvedValue({ title: "affected document", eli: "example/eli" })

    vi.doMock("@/services/normService", () => ({
      getNormByEli,
    }))

    const { useAffectedDocuments } = await import("./useAffectedDocuments")

    const eli = ref("0")
    useAffectedDocuments(eli)

    eli.value = "1"
    await nextTick()

    expect(getNormByEli).toHaveBeenCalledTimes(3)
  })
})
