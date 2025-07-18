import type { ElementProprietary, RahmenProprietary } from "@/types/proprietary"
import { flushPromises } from "@vue/test-utils"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { ref } from "vue"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

vi.mock("@/lib/auth", () => {
  return {
    useAuthentication: () => ({
      addAuthorizationHeader: (init: HeadersInit) => ({ ...init }),
      tryRefresh: vi.fn().mockReturnValue(true),
    }),
  }
})

describe("proprietaryService", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  describe("useProprietaryService", () => {
    it("provides the data from the API", async () => {
      const fixtures: RahmenProprietary = {
        fna: "foo",
      }

      const useApiFetch = vi.fn().mockReturnValue({
        data: ref(fixtures),
        execute: vi.fn(),
      })

      vi.doMock("@/services/apiService", () => ({ useApiFetch }))

      const { useProprietaryService } = await import("./proprietaryService")

      const result = useProprietaryService(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
        {
          eid: null,
        },
      )
      expect(result.data.value).toBeTruthy()

      vi.doUnmock("@/services/apiService")
    })

    it("does not load if the ELI has no value", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      const eli = ref(undefined)
      useProprietaryService(eli, { eid: null })
      await flushPromises()
      expect(fetchSpy).not.toHaveBeenCalled()
    })

    it("does not reload if the ELI has no value", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      const eli = ref<DokumentExpressionEli | undefined>(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )
      useProprietaryService(
        eli,
        { eid: null },
        { immediate: true, refetch: true },
      )
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

      eli.value = undefined
      await flushPromises()
      expect(fetchSpy).toHaveBeenCalledTimes(1)
    })

    it("reloads with a new ELI value", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )
      useProprietaryService(
        eli,
        { eid: null },
        { immediate: true, refetch: true },
      )
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1",
      )
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
    })

    it("loads with an eId", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import("./proprietaryService")

      const eid = ref("fake_eid")
      useProprietaryService(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
        { eid },
      )

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1/proprietary/fake_eid",
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
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
        { eid },
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
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
        { eid },
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
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
        { eid: null },
        { immediate: true, refetch: true },
      )
      await flushPromises()

      expect(fetchSpy).toHaveBeenCalled()
    })
  })

  describe("useGetRahmenProprietary", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("loads the data from the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetRahmenProprietary } = await import("./proprietaryService")

      useGetRahmenProprietary(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1/proprietary",
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

      const { useGetRahmenProprietary } = await import("./proprietaryService")

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )
      useGetRahmenProprietary(eli)

      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1/proprietary",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "application/json",
            }),
          }),
        )
      })

      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1",
      )
      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1/proprietary",
          expect.any(Object),
        )
      })
    })
  })

  describe("usePutRahmenProprietary", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("sends the data to the API on changes", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { usePutRahmenProprietary } = await import("./proprietaryService")

      const data = ref<RahmenProprietary>({ fna: "4711" })
      const { execute } = usePutRahmenProprietary(
        data,
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )

      await flushPromises()
      expect(fetchSpy).not.toHaveBeenCalled()

      data.value = { fna: "4712" }
      execute()

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1/proprietary",
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

      useGetElementProprietary(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
        "fake_eid",
      )

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1/proprietary/fake_eid",
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
      useGetElementProprietary(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
        eid,
      )

      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1/proprietary/fake_eid_1",
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
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1/proprietary/fake_eid_2",
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

      const data = ref<ElementProprietary>({ artDerNorm: "SN" })
      const { execute } = usePutElementProprietary(
        data,
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
        "fake_eid",
      )

      await flushPromises()
      expect(fetchSpy).not.toHaveBeenCalled()

      data.value = { artDerNorm: "UN" }
      execute()

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1/proprietary/fake_eid",
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
