import { Element, ElementType } from "@/types/element"
import { flushPromises } from "@vue/test-utils"
import {
  afterAll,
  beforeAll,
  beforeEach,
  describe,
  expect,
  it,
  vi,
} from "vitest"
import { ref } from "vue"

describe("elementService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("getElementHtmlByEliAndEid", () => {
    it("provides the data from the API", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce(`<div></div>`)

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { getElementHtmlByEliAndEid } = await import("./elementService")

      const result = await getElementHtmlByEliAndEid("example/eli", "eid-1")

      expect(result).toEqual(`<div></div>`)

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/example/eli/elements/eid-1",
        { headers: { Accept: "text/html" }, query: { atIsoDate: undefined } },
      )
    })

    it("loads the data with an ISO date", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce(`<div></div>`)

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { getElementHtmlByEliAndEid } = await import("./elementService")

      const result = await getElementHtmlByEliAndEid("example/eli", "eid-1", {
        at: new Date(2024, 3, 5),
      })

      expect(result).toEqual(`<div></div>`)

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/example/eli/elements/eid-1",
        {
          headers: { Accept: "text/html" },
          query: { atIsoDate: "2024-04-05T00:00:00.000Z" },
        },
      )
    })
  })

  describe("getElementByEliAndEid", () => {
    it("provides the data from the API", async () => {
      const fetchMock = vi.fn().mockResolvedValueOnce({
        eid: "article-eid",
        title: "Example",
        type: "article",
      })

      vi.doMock("./apiService.ts", () => ({ apiFetch: fetchMock }))

      const { getElementByEliAndEid } = await import("./elementService")

      const result = await getElementByEliAndEid("example/eli", "article-eid")

      expect(result).toEqual({
        eid: "article-eid",
        title: "Example",
        type: "article",
      })

      expect(fetchMock).toHaveBeenCalledWith(
        "/norms/example/eli/elements/article-eid",
      )
    })
  })

  describe("useElementsService", () => {
    beforeAll(() => {
      vi.useFakeTimers()
    })

    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    afterAll(() => {
      vi.useRealTimers()
    })

    it("provides the data from the API", async () => {
      const fixtures: Element[] = [
        { eid: "fake_eid", title: "Test", type: "article" },
      ]

      const useApiFetch = vi.fn().mockReturnValue({
        json: vi.fn().mockReturnValue({
          data: ref(fixtures),
          execute: vi.fn(),
        }),
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

    it("sets the loading state", async () => {
      vi.spyOn(window, "fetch").mockReturnValue(
        new Promise<Response>((resolve) => {
          setTimeout(() => {
            resolve(new Response("{}"))
          }, 1000)
        }),
      )

      const { useElementsService } = await import("./elementService")

      const eli = ref("fake/eli")
      const { isFetching } = useElementsService(eli, ["article"])
      await vi.waitFor(() => expect(isFetching.value).toBeTruthy())

      vi.advanceTimersToNextTimer()
      await vi.waitFor(() => expect(isFetching.value).toBeFalsy())
    })

    it("sets the error state", async () => {
      vi.spyOn(window, "fetch").mockReturnValue(
        new Promise<Response>((_, reject) => {
          setTimeout(() => {
            reject(new Response("{}"))
          }, 1000)
        }),
      )

      const { useElementsService } = await import("./elementService")

      const eli = ref("fake/eli")
      const { error } = useElementsService(eli, ["article"])
      await vi.waitFor(() => expect(error.value).toBeFalsy())

      vi.advanceTimersToNextTimer()
      await vi.waitFor(() => expect(error.value).toBeTruthy())
    })

    it("runs the request manually", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useElementsService } = await import("./elementService")

      const eli = ref("fake/eli/1")
      const { execute } = useElementsService(eli, ["article"], undefined, {
        immediate: false,
      })
      execute()
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))
    })
  })
})
