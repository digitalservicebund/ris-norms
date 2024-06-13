import { Proprietary } from "@/types/proprietary"
import { flushPromises } from "@vue/test-utils"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { ref } from "vue"

describe("proprietaryService", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  describe("useProprietaryService", () => {
    it("provides the data from the API", async () => {
      const fixtures: Proprietary = {
        fna: "foo",
      }

      const useApiFetch = vi.fn().mockReturnValue({
        data: ref(fixtures),
        execute: vi.fn(),
      })

      vi.doMock("@/services/apiService", () => ({ useApiFetch }))

      const { useProprietaryService } = await import("./proprietaryService")

      const result = useProprietaryService("fake/eli", { atDate: "2024-06-10" })
      expect(result.data.value).toBeTruthy()

      vi.doUnmock("@/services/apiService")
    })

    it("does not load if the ELI has no value", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      const eli = ref("")
      useProprietaryService(eli, { atDate: "2024-06-10" })
      await flushPromises()
      expect(fetchSpy).not.toHaveBeenCalled()
    })

    it("does not reload if the ELI has no value", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      const eli = ref("fake/eli/1")
      useProprietaryService(
        eli,
        { atDate: "2024-06-10" },
        { immediate: true, refetch: true },
      )
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

      eli.value = ""
      await flushPromises()
      expect(fetchSpy).toHaveBeenCalledTimes(1)
    })

    it("reloads with a new ELI value", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      const eli = ref("fake/eli/1")
      useProprietaryService(
        eli,
        { atDate: "2024-06-10" },
        { immediate: true, refetch: true },
      )
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

      eli.value = "fake/eli/2"
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
    })

    it("loads with a date string", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      const eli = ref("fake/eli/1")
      useProprietaryService(eli, { atDate: "2024-04-06" })

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/proprietary/2024-04-06",
          expect.any(Object),
        ),
      )
    })

    it("loads with a date object", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      const eli = ref("fake/eli/1")
      useProprietaryService(eli, { atDate: new Date(2024, 6, 4) })

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/proprietary/2024-07-04",
          expect.any(Object),
        ),
      )
    })

    it("reloads when the date changes", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      const eli = ref("fake/eli/1")
      const date = ref("2024-06-04")
      useProprietaryService(
        eli,
        { atDate: date },
        { immediate: true, refetch: true },
      )
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

      date.value = "2024-06-10"
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
    })

    it("does not reload if the date is mising", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      const eli = ref("fake/eli/1")
      useProprietaryService(
        eli,
        { atDate: undefined },
        { immediate: true, refetch: true },
      )
      await flushPromises()

      expect(fetchSpy).not.toHaveBeenCalled()
    })
  })

  describe("useGetProprietary", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("loads the data from the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetProprietary } = await import("./proprietaryService")

      useGetProprietary("fake/eli/1", {
        atDate: new Date("2024-05-13"),
      })

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/proprietary/2024-05-13",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "application/json",
            }),
          }),
        ),
      )
    })
  })

  describe("usePutProprietary", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("sends the data to the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { usePutProprietary } = await import("./proprietaryService")

      usePutProprietary(
        { fna: "4711" },
        "fake/eli/1",
        { atDate: new Date("2024-05-13") },
        { immediate: true },
      )

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/proprietary/2024-05-13",
          expect.objectContaining({
            headers: expect.objectContaining({ Accept: "application/json" }),
            method: "PUT",
            body: '{"fna":"4711"}',
          }),
        ),
      )
    })
  })
})
