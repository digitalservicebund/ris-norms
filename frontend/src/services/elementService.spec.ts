import { Element, ElementType } from "@/types/element"
import { flushPromises } from "@vue/test-utils"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { ref } from "vue"

describe("useElementsService", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("provides the data from the API", async () => {
    const fixtures: Element[] = [
      { eid: "fake_eid", title: "Test", type: "article" },
    ]

    const useApiFetch = vi.fn().mockReturnValue({
      data: ref(fixtures),
      execute: vi.fn(),
    })

    vi.doMock("@/services/apiService", () => ({ useApiFetch }))

    const { useElementsService } = await import("./elementService")

    const result = useElementsService("fake/eli", ["article"])
    expect(result.data.value).toBeTruthy()

    vi.doUnmock("@/services/apiService")
  })

  it("does not load if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementsService } = await import("./elementService")

    const eli = ref("")
    useElementsService(eli, ["article"])
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("does not reload if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementsService } = await import("./elementService")

    const eli = ref("fake/eli/1")
    useElementsService(eli, ["article"])
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = ""
    await flushPromises()
    expect(fetchSpy).toHaveBeenCalledTimes(1)
  })

  it("reloads with a new ELI value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementsService } = await import("./elementService")

    const eli = ref("fake/eli/1")
    useElementsService(eli, ["article"], undefined, {
      immediate: true,
      refetch: true,
    })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = "fake/eli/2"
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })

  it("loads with the amendedBy filter", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementsService } = await import("./elementService")

    const eli = ref("fake/eli/1")
    useElementsService(eli, ["article"], { amendedBy: "amendedby" })

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/fake/eli/1/elements?type=article&amendedBy=amendedby",
        expect.any(Object),
      ),
    )
  })

  it("reloads when the amendedBy filter changes", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementsService } = await import("./elementService")

    const eli = ref("fake/eli/1")
    const amendedBy = ref("amendedby")
    useElementsService(
      eli,
      ["article"],
      { amendedBy },
      { immediate: true, refetch: true },
    )
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    amendedBy.value = "amendedby2"
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })

  it("loads with the specified types", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementsService } = await import("./elementService")

    const eli = ref("fake/eli/1")
    useElementsService(eli, ["article", "conclusions"])

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/fake/eli/1/elements?type=article&type=conclusions",
        expect.any(Object),
      ),
    )
  })

  it("reloads when the types change", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementsService } = await import("./elementService")

    const eli = ref("fake/eli/1")
    const types = ref<ElementType[]>(["article"])
    useElementsService(eli, types, undefined, {
      immediate: true,
      refetch: true,
    })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    types.value = ["conclusions"]
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })

  describe("useGetElements", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("loads the data from the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetElements } = await import("./elementService")

      useGetElements("fake/eli/1", ["article"])

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/elements?type=article",
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

      const { useGetElements } = await import("./elementService")

      const eli = ref("fake/eli/1")
      useGetElements(eli, ["article"])

      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/elements?type=article",
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
          "/api/v1/norms/fake/eli/2/elements?type=article",
          expect.any(Object),
        )
      })
    })
  })
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

    const result = useElementService("fake/eli", "fake_eid")
    expect(result.data.value).toBeTruthy()

    vi.doUnmock("@/services/apiService")
  })

  it("does not load if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementService } = await import("./elementService")

    const eli = ref("")
    useElementService(eli, "fake_eid")
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("does not reload if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementService } = await import("./elementService")

    const eli = ref("fake/eli/1")
    useElementService(eli, "fake_eid", undefined, {
      immediate: true,
      refetch: true,
    })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = ""
    await flushPromises()
    expect(fetchSpy).toHaveBeenCalledTimes(1)
  })

  it("does not load if the eId has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementService } = await import("./elementService")

    const eid = ref("")
    useElementService("fake/eli", eid)
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("does not reload if the eId has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementService } = await import("./elementService")

    const eid = ref("fake_eid")
    useElementService("fake/eli/1", eid, undefined, {
      immediate: true,
      refetch: true,
    })
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

    const eli = ref("fake/eli/1")
    useElementService(eli, "fake_eid", undefined, {
      immediate: true,
      refetch: true,
    })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = "fake/eli/2"
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })

  it("loads with the date filter", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementService } = await import("./elementService")

    const eli = ref("fake/eli/1")
    const at = ref(new Date(2024, 5, 13))
    useElementService(eli, "fake_eid", { at })

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/fake/eli/1/elements/fake_eid?atIsoDate=2024-06-13T00%3A00%3A00.000Z",
        expect.any(Object),
      ),
    )
  })

  it("reloads when the date filter changes", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useElementService } = await import("./elementService")

    const eli = ref("fake/eli/1")
    const at = ref(new Date(2024, 5, 13))
    useElementService(
      eli,
      "fake_eid",
      { at },
      { immediate: true, refetch: true },
    )
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    at.value = new Date(2024, 4, 13)
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

      useGetElement("fake/eli/1", "fake_eid")

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/elements/fake_eid?",
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

      const eli = ref("fake/eli/1")
      useGetElement(eli, "fake_eid")

      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/elements/fake_eid?",
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
          "/api/v1/norms/fake/eli/2/elements/fake_eid?",
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

      useGetElementHtml("fake/eli/1", "fake_eid")

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/elements/fake_eid?",
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

      const eli = ref("fake/eli/1")
      useGetElementHtml(eli, "fake_eid")

      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/elements/fake_eid?",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "text/html",
            }),
          }),
        )
      })

      eli.value = "fake/eli/2"
      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/2/elements/fake_eid?",
          expect.any(Object),
        )
      })
    })
  })
})
