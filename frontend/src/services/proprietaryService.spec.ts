import { Proprietary } from "@/types/proprietary"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { ref } from "vue"

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

      vi.doUnmock("@/services/apiService")
    })

    it("does not load if the ELI has no value", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetProprietary } = await import(
        "@/services/proprietaryService"
      )

      const eli = ref("")
      useGetProprietary(eli)
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(0))
    })

    it("does not reload if the ELI has no value", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetProprietary } = await import(
        "@/services/proprietaryService"
      )

      const eli = ref("fake/eli/1")
      useGetProprietary(eli)
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

      eli.value = ""
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))
    })

    it("reloads with a new ELI value", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetProprietary } = await import(
        "@/services/proprietaryService"
      )

      const eli = ref("fake/eli/1")
      useGetProprietary(eli)
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

      eli.value = "fake/eli/2"
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
    })
  })
})
