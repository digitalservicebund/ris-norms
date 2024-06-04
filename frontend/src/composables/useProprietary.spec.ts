import { Proprietary } from "@/types/proprietary"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { ref } from "vue"

describe("useProprietary", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

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

    const { useProprietary } = await import("@/composables/useProprietary")

    const result = useProprietary("fake/eli")
    expect(result.data.value).toBeTruthy()

    vi.doUnmock("@/services/apiService")
  })

  it("does not load if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useProprietary } = await import("@/composables/useProprietary")

    const eli = ref("")
    useProprietary(eli)
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(0))
  })

  it("does not reload if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useProprietary } = await import("@/composables/useProprietary")

    const eli = ref("fake/eli/1")
    useProprietary(eli)
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = ""
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))
  })

  it("reloads with a new ELI value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useProprietary } = await import("@/composables/useProprietary")

    const eli = ref("fake/eli/1")
    useProprietary(eli)
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = "fake/eli/2"
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })

  it("loads with a date string", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useProprietary } = await import("@/composables/useProprietary")

    const eli = ref("fake/eli/1")
    useProprietary(eli, { atDate: "2024-04-06" })

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

    const { useProprietary } = await import("@/composables/useProprietary")

    const eli = ref("fake/eli/1")
    useProprietary(eli, { atDate: new Date(2024, 6, 4) })

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

    const { useProprietary } = await import("@/composables/useProprietary")

    const eli = ref("fake/eli/1")
    const date = ref("2024-06-04")
    useProprietary(eli, { atDate: date })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    date.value = "2024-06-10"
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })

  it("doesn't append a date if there is none", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useProprietary } = await import("@/composables/useProprietary")

    const eli = ref("fake/eli/1")
    useProprietary(eli, { atDate: undefined })

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/fake/eli/1/proprietary",
        expect.any(Object),
      ),
    )
  })
})
