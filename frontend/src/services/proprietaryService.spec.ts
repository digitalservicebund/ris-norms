import { Proprietary } from "@/types/proprietary"
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
import { flushPromises } from "@vue/test-utils"

describe("proprietaryService", () => {
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

  describe("useProprietaryService", () => {
    it("provides the data from the API", async () => {
      const fixtures: Proprietary = {
        fna: "foo",
      }

      const useApiFetch = vi.fn().mockReturnValue({
        json: vi.fn().mockReturnValue({
          data: ref(fixtures),
          execute: vi.fn(),
        }),
      })

      vi.doMock("@/services/apiService", () => ({ useApiFetch }))

      const { useProprietaryService } = await import(
        "@/services/proprietaryService"
      )

      const result = useProprietaryService("fake/eli")
      expect(result.data.value).toBeTruthy()

      vi.doUnmock("@/services/apiService")
    })

    it("does not load if the ELI has no value", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import(
        "@/services/proprietaryService"
      )

      const eli = ref("")
      useProprietaryService(eli)
      await flushPromises()
      expect(fetchSpy).not.toHaveBeenCalled()
    })

    it("does not reload if the ELI has no value", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import(
        "@/services/proprietaryService"
      )

      const eli = ref("fake/eli/1")
      useProprietaryService(eli)
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

      eli.value = ""
      await flushPromises()
      expect(fetchSpy).toHaveBeenCalledTimes(1)
    })

    it("reloads with a new ELI value", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import(
        "@/services/proprietaryService"
      )

      const eli = ref("fake/eli/1")
      useProprietaryService(eli, undefined, { immediate: true, refetch: true })
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

      eli.value = "fake/eli/2"
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
    })

    it("loads with a date string", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import(
        "@/services/proprietaryService"
      )

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

      const { useProprietaryService } = await import(
        "@/services/proprietaryService"
      )

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

      const { useProprietaryService } = await import(
        "@/services/proprietaryService"
      )

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

    it("doesn't append a date if there is none", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import(
        "@/services/proprietaryService"
      )

      const eli = ref("fake/eli/1")
      useProprietaryService(eli, { atDate: undefined })

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/fake/eli/1/proprietary",
          expect.any(Object),
        ),
      )
    })

    it("sets the loading state", async () => {
      vi.spyOn(window, "fetch").mockReturnValue(
        new Promise<Response>((resolve) => {
          setTimeout(() => {
            resolve(new Response("{}"))
          }, 1000)
        }),
      )

      const { useProprietaryService } = await import(
        "@/services/proprietaryService"
      )

      const eli = ref("fake/eli")
      const { isFetching } = useProprietaryService(eli)
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

      const { useProprietaryService } = await import(
        "@/services/proprietaryService"
      )

      const eli = ref("fake/eli")
      const { error } = useProprietaryService(eli)
      await vi.waitFor(() => expect(error.value).toBeFalsy())

      vi.advanceTimersToNextTimer()
      await vi.waitFor(() => expect(error.value).toBeTruthy())
    })

    it("runs the request manually", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useProprietaryService } = await import(
        "@/services/proprietaryService"
      )

      const eli = ref("fake/eli/1")
      const date = ref("2024-06-04")
      const { execute } = useProprietaryService(
        eli,
        { atDate: date },
        { immediate: false },
      )
      execute()
      await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))
    })

    it("sends data to the API", async () => {
      const fixtures: Proprietary = {
        fna: "foo",
      }

      const useApiFetch = vi.fn().mockReturnValue({
        json: vi.fn().mockReturnValue({
          put: vi.fn().mockReturnValue({ data: ref(fixtures) }),
        }),
      })

      vi.doMock("@/services/apiService", () => ({ useApiFetch }))

      const { useProprietaryService } = await import(
        "@/services/proprietaryService"
      )

      const result = useProprietaryService("fake/eli").put()
      expect(result.data.value).toBeTruthy()

      vi.doUnmock("@/services/apiService")
    })
  })
})
