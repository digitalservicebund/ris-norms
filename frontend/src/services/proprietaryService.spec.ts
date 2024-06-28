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

      const result = useProprietaryService("fake/eli", {
        atDate: "2024-06-10",
        eid: null,
      })
      expect(result.data.value).toBeTruthy()

      vi.doUnmock("@/services/apiService")
    })

    it("does not load if the ELI has no value", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      const eli = ref("")
      useProprietaryService(eli, { atDate: "2024-06-10", eid: null })
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
        { atDate: "2024-06-10", eid: null },
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
        { atDate: "2024-06-10", eid: null },
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
      useProprietaryService(eli, { atDate: "2024-04-06", eid: null })

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
      useProprietaryService(eli, { atDate: new Date(2024, 6, 4), eid: null })

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
        { atDate: date, eid: null },
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
        { atDate: undefined, eid: null },
        { immediate: true, refetch: true },
      )
      await flushPromises()

      expect(fetchSpy).not.toHaveBeenCalled()
    })

    it("loads with an eId", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      const eid = ref("fake_eid")
      useProprietaryService("fake/eli/1", { atDate: "2024-04-06", eid })

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/proprietary/fake_eid/2024-04-06",
          expect.any(Object),
        ),
      )
    })

    it("reloads when the eId changes", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      const eid = ref("fake_eid_1")
      useProprietaryService(
        "fake/eli/1",
        { atDate: "2024-04-06", eid },
        { immediate: true, refetch: true },
      )
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

      eid.value = "fake_eid_2"
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
    })

    it("does not reload when the eId is provided but empty", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      const eid = ref()
      useProprietaryService(
        "fake/eli/1",
        { atDate: undefined, eid },
        { immediate: true, refetch: true },
      )
      await flushPromises()

      expect(fetchSpy).not.toHaveBeenCalled()
    })

    it("reloads when the eId is null", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      useProprietaryService(
        "fake/eli/1",
        { atDate: "2024-04-06", eid: null },
        { immediate: true, refetch: true },
      )
      await flushPromises()

      expect(fetchSpy).toHaveBeenCalled()
    })
  })

  describe("useGetFrameProprietary", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("loads the data from the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetFrameProprietary } = await import("./proprietaryService")

      useGetFrameProprietary("fake/eli/1", { atDate: new Date("2024-05-13") })

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

    it("reloads when parameters change", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetFrameProprietary } = await import("./proprietaryService")

      const eli = ref("fake/eli/1")
      useGetFrameProprietary(eli, { atDate: new Date("2024-05-13") })

      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/proprietary/2024-05-13",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "application/json",
            }),
          }),
        )
      })

      eli.value = "fake/eli/2"
      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/2/proprietary/2024-05-13",
          expect.any(Object),
        )
      })
    })
  })

  describe("usePutFrameProprietary", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("sends the data to the API on changes", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { usePutFrameProprietary } = await import("./proprietaryService")

      const data = ref<Proprietary>({ fna: "4711" })
      const { execute } = usePutFrameProprietary(data, "fake/eli/1", {
        atDate: new Date("2024-05-13"),
      })

      await flushPromises()
      expect(fetchSpy).not.toHaveBeenCalled()

      data.value = { fna: "4712" }
      execute()

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/proprietary/2024-05-13",
          expect.objectContaining({
            headers: expect.objectContaining({ Accept: "application/json" }),
            method: "PUT",
            body: '{"fna":"4712"}',
          }),
        ),
      )
    })
  })

  describe("useGetElementProprietary", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("loads the data from the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetElementProprietary } = await import("./proprietaryService")

      useGetElementProprietary("fake/eli/1", "fake_eid", {
        atDate: new Date("2024-05-13"),
      })

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/proprietary/fake_eid/2024-05-13",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "application/json",
            }),
          }),
        ),
      )
    })

    it("reloads when parameters change", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetElementProprietary } = await import("./proprietaryService")

      const eid = ref("fake_eid_1")
      useGetElementProprietary("fake/eli/1", eid, {
        atDate: new Date("2024-05-13"),
      })

      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/proprietary/fake_eid_1/2024-05-13",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "application/json",
            }),
          }),
        )
      })

      eid.value = "fake_eid_2"
      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/proprietary/fake_eid_2/2024-05-13",
          expect.any(Object),
        )
      })
    })
  })

  describe("usePutElementProprietary", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("sends the data to the API on changes", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { usePutElementProprietary } = await import("./proprietaryService")

      const data = ref<Proprietary>({ artDerNorm: "SN" })
      const { execute } = usePutElementProprietary(
        data,
        "fake/eli/1",
        "fake_eid",
        { atDate: new Date("2024-05-13") },
      )

      await flushPromises()
      expect(fetchSpy).not.toHaveBeenCalled()

      data.value = { artDerNorm: "UN" }
      execute()

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/proprietary/fake_eid/2024-05-13",
          expect.objectContaining({
            headers: expect.objectContaining({ Accept: "application/json" }),
            method: "PUT",
            body: '{"artDerNorm":"UN"}',
          }),
        ),
      )
    })
  })
})
