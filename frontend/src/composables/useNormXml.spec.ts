import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"
import { UseFetchReturn } from "@vueuse/core"

describe("useNormXml", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the target law xml", async () => {
    const dataRef = ref<string>()
    vi.doMock("@/services/normService", () => ({
      useGetNormXml: vi.fn().mockReturnValue({
        data: dataRef,
      } as UseFetchReturn<string>),
      usePutNormXml: vi.fn().mockReturnValue({
        data: ref(),
      } as UseFetchReturn<string>),
    }))

    const { useNormXml } = await import("./useNormXml")

    const { data } = useNormXml(
      "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      undefined,
    )
    dataRef.value = "<xml></xml>"
    await nextTick()

    expect(data.value).toEqual("<xml></xml>")
  })

  test("should update the xml", async () => {
    const updateRef = ref<string>()
    vi.doMock("@/services/normService", () => ({
      useGetNormXml: vi.fn().mockReturnValue({
        data: ref("<xml>1</xml>"),
      } as UseFetchReturn<string>),
      usePutNormXml: vi.fn().mockReturnValue({
        data: updateRef,
        execute: vi.fn().mockImplementation(() => {
          updateRef.value = "<xml>2</xml>"
        }) as () => Promise<unknown>,
      } as UseFetchReturn<string>),
    }))

    const { useNormXml } = await import("@/composables/useNormXml")
    const newXml = ref<string>()
    const {
      data,
      update: { execute },
    } = useNormXml(
      "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      newXml,
    )

    newXml.value = "<xml>2</xml>"
    await execute()

    expect(data.value).toEqual("<xml>2</xml>")
  })
})
