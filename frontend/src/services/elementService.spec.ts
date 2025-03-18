import type { Element } from "@/types/element"
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

describe("useElementService", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("provides the data from the API", async () => {
    const fixture: Element = {
      eid: "fake_eid",
      title: "Test",
      type: "article",
    }

    const useApiFetch = vi.fn().mockReturnValue({
      data: ref(fixture),
      execute: vi.fn(),
    })

    vi.doMock("@/services/apiService", () => ({ useApiFetch }))

    const { useElementService } = await import("./elementService")

    const result = useElementService(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
      ),
      "fake_eid",
    )
    expect(result.data.value).toBeTruthy()

    vi.doUnmock("@/services/apiService")
  })

  it("does not load if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementService } = await import("./elementService")

    const eli = ref(undefined)
    useElementService(eli, "fake_eid")
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("does not reload if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementService } = await import("./elementService")

    const eli = ref<DokumentExpressionEli | undefined>(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
      ),
    )
    useElementService(eli, "fake_eid", {
      immediate: true,
      refetch: true,
    })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = undefined
    await flushPromises()
    expect(fetchSpy).toHaveBeenCalledTimes(1)
  })

  it("does not load if the eId has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementService } = await import("./elementService")

    const eid = ref("")
    useElementService(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
      ),
      eid,
    )
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("does not reload if the eId has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementService } = await import("./elementService")

    const eid = ref("fake_eid")
    useElementService(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
      ),
      eid,
      {
        immediate: true,
        refetch: true,
      },
    )
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eid.value = ""
    await flushPromises()
    expect(fetchSpy).toHaveBeenCalledTimes(1)
  })

  it("reloads with a new ELI value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementService } = await import("./elementService")

    const eli = ref(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
      ),
    )
    useElementService(eli, "fake_eid", {
      immediate: true,
      refetch: true,
    })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-1",
    )
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })

  describe("useGetElement", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("loads the data from the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetElement } = await import("./elementService")

      useGetElement(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
        ),
        "fake_eid",
      )

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1/elements/fake_eid",
          expect.objectContaining({
            headers: {
              Accept: "application/json",
              "Content-Type": "application/json",
            },
          }),
        ),
      )
    })

    it("reloads when parameters change", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetElement } = await import("./elementService")

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
        ),
      )
      useGetElement(eli, "fake_eid")

      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1/elements/fake_eid",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "application/json",
            }),
          }),
        )
      })

      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-1",
      )
      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-1/elements/fake_eid",
          expect.any(Object),
        )
      })
    })
  })

  describe("useGetElementHtml", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("loads the data from the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetElementHtml } = await import("./elementService")

      useGetElementHtml(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
        ),
        "fake_eid",
      )

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1/elements/fake_eid",
          expect.objectContaining({
            headers: expect.objectContaining({ Accept: "text/html" }),
          }),
        ),
      )
    })

    it("reloads when parameters change", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetElementHtml } = await import("./elementService")

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1",
        ),
      )
      useGetElementHtml(eli, "fake_eid")

      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-1/elements/fake_eid",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "text/html",
            }),
          }),
        )
      })

      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-1",
      )
      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-1/elements/fake_eid",
          expect.any(Object),
        )
      })
    })
  })
})
