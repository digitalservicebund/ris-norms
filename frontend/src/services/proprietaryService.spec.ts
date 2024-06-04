import { Proprietary } from "@/types/proprietary"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { nextTick, ref } from "vue"

describe("proprietaryService", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  describe("useProprietary(eli)", () => {
    it("provides the data from the API", async () => {
      const fixtures: Proprietary = {
        fna: { value: "foo" },
      }

      const useApiFetch = vi.fn().mockReturnValue({
        json: vi.fn().mockReturnValue({
          data: ref(fixtures),
          execute: vi.fn(),
        }),
      })

      vi.doMock("@/services/apiService", () => ({ useApiFetch }))

      const { useGetProprietary } = await import(
        "@/services/proprietaryService"
      )

      const result = useGetProprietary("fake/eli")
      expect(result.data.value).toBeTruthy()
    })

    it("does not load until the ELI has a value", async () => {
      const execute = vi.fn()

      const useApiFetch = vi.fn().mockReturnValue({
        json: vi.fn().mockReturnValue({ data: ref({}), execute }),
      })

      vi.doMock("@/services/apiService", () => ({ useApiFetch }))

      const { useGetProprietary } = await import(
        "@/services/proprietaryService"
      )

      const eli = ref("")
      useGetProprietary(eli)
      expect(execute).not.toHaveBeenCalled()

      eli.value = "fake/eli"
      await nextTick()
      expect(execute).toHaveBeenCalled()
    })

    it("does not reload unless the ELI has a value", async () => {
      const execute = vi.fn()

      const useApiFetch = vi.fn().mockReturnValue({
        json: vi.fn().mockReturnValue({ data: ref({}), execute }),
      })

      vi.doMock("@/services/apiService", () => ({ useApiFetch }))

      const { useGetProprietary } = await import(
        "@/services/proprietaryService"
      )

      const eli = ref("fake/eli")
      useGetProprietary(eli)
      expect(execute).toHaveBeenCalledOnce()

      eli.value = ""
      await nextTick()
      expect(execute).toHaveBeenCalledOnce()
    })
  })
})
