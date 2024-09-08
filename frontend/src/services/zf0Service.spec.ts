import { ref } from "vue"
import { flushPromises } from "@vue/test-utils"
import { beforeEach, describe, expect, it, vi } from "vitest"

describe("useZf0Service", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("provides the data from the API", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("<xml></xml>"))

    const { useZf0Service } = await import("./zf0Service")

    const eli = ref("fake/eli/1")
    const amendingNormEli = ref("fake/eli/2")
    const result = useZf0Service(eli, amendingNormEli)
    await flushPromises()
    expect(result.data.value).to.eq("<xml></xml>")
    expect(fetchSpy).toHaveBeenCalledWith(
      "/api/v1/norms/fake/eli/1/zf0?amendingNormEli=fake%2Feli%2F2",
      expect.anything(),
    )
  })

  it("does not load if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("<xml></xml>"))

    const { useZf0Service } = await import("./zf0Service")

    const eli = ref("")
    const amendingNormEli = ref("")
    useZf0Service(eli, amendingNormEli)
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("reloads with a new ELI value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("<xml></xml>"))

    const { useZf0Service } = await import("./zf0Service")

    const eli = ref("fake/eli/1")
    const amendingNormEli = ref("fake/eli/2")
    useZf0Service(eli, amendingNormEli)
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = "fake/eli/3"
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))

    amendingNormEli.value = "fake/eli/4"
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(3))
  })
})
