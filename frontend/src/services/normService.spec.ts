import type { Norm } from "@/types/norm"
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

describe("useNormService", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("provides the data from the API", async () => {
    const fixture: Norm = {
      eli: "fake/eli",
      title: "Example norm",
    }

    const useApiFetch = vi.fn().mockReturnValue({
      data: ref(fixture),
      execute: vi.fn(),
    })

    vi.doMock("@/services/apiService", () => ({ useApiFetch }))

    const { useNormService } = await import("./normService")

    const result = useNormService(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
    )
    expect(result.data.value).toBeTruthy()

    vi.doUnmock("@/services/apiService")
  })

  it("does not load if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormService } = await import("./normService")

    const eli = ref(undefined)
    useNormService(eli)
    await flushPromises()
    expect(fetchSpy).not.toHaveBeenCalled()
  })

  it("does not reload if the ELI has no value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormService } = await import("./normService")

    const eli = ref<DokumentExpressionEli | undefined>(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
    )
    useNormService(eli, undefined, { immediate: true, refetch: true })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = undefined
    await flushPromises()
    expect(fetchSpy).toHaveBeenCalledTimes(1)
  })

  it("reloads with a new ELI value", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormService } = await import("./normService")

    const eli = ref(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
    )
    useNormService(eli, undefined, { immediate: true, refetch: true })
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1))

    eli.value = DokumentExpressionEli.fromString(
      "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1",
    )
    await vi.waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(2))
  })

  it("appends the showMetadata parameter", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormService } = await import("./normService")

    const eli = ref(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
    )
    useNormService(eli, { showMetadata: true })

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?showMetadata=true",
        expect.any(Object),
      ),
    )
  })

  it("does not append the showMetadata parameter", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response("{}"))

    const { useNormService } = await import("./normService")

    const eli = ref(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
      ),
    )
    useNormService(eli, { showMetadata: false })

    await vi.waitFor(() =>
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?",
        expect.any(Object),
      ),
    )
  })

  describe("useGetNorm", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("loads the data from the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetNorm } = await import("./normService")

      useGetNorm(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )

      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?",
          expect.objectContaining({
            headers: {
              Accept: "application/json",
              "Content-Type": "application/json",
            },
          }),
        )
      })
    })

    it("reloads when parameters change", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetNorm } = await import("./normService")

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )
      useGetNorm(eli)

      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?",
          expect.objectContaining({
            headers: {
              Accept: "application/json",
              "Content-Type": "application/json",
            },
          }),
        )
      })

      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1",
      )
      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1?",
          expect.any(Object),
        )
      })
    })
  })

  describe("useGetNormHtml", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("loads the data from the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetNormHtml } = await import("./normService")

      useGetNormHtml(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
        {
          showMetadata: true,
        },
      )

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?showMetadata=true",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "text/html",
            }),
          }),
        ),
      )
    })

    it("reloads when parameters change", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetNormHtml } = await import("./normService")

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )
      useGetNormHtml(eli, {
        showMetadata: true,
      })

      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?showMetadata=true",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "text/html",
            }),
          }),
        )
      })

      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1",
      )
      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1?showMetadata=true",
          expect.any(Object),
        )
      })
    })
  })

  describe("useGetNormXml", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("loads the data from the API", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetNormXml } = await import("./normService")

      useGetNormXml(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "application/xml",
            }),
          }),
        ),
      )
    })

    it("reloads when parameters change", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { useGetNormXml } = await import("./normService")

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )
      useGetNormXml(eli)

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "application/xml",
            }),
          }),
        ),
      )

      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1",
      )
      await vi.waitFor(() => {
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/2/deu/regelungstext-verkuendung-1?",
          expect.any(Object),
        )
      })
    })
  })

  describe("usePutNormXml", () => {
    beforeEach(() => {
      vi.resetAllMocks()
      vi.resetModules()
    })

    it("sends the data to the API on changes", async () => {
      const fetchSpy = vi
        .spyOn(window, "fetch")
        .mockResolvedValue(new Response("{}"))

      const { usePutNormXml } = await import("./normService")

      const xml = ref("oldXml")
      const { execute } = usePutNormXml(
        xml,
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1",
        ),
      )

      await flushPromises()
      expect(fetchSpy).not.toHaveBeenCalled()

      xml.value = "newXml"
      execute()

      await vi.waitFor(() =>
        expect(fetchSpy).toHaveBeenCalledWith(
          "/api/v1/norms/eli/bund/bgbl-1/2021/s4/2021-03-01/1/deu/regelungstext-verkuendung-1?",
          expect.objectContaining({
            headers: expect.objectContaining({
              Accept: "application/xml",
              "Content-Type": "application/xml",
            }),
            method: "PUT",
            body: "newXml",
          }),
        ),
      )
    })
  })
})

describe("useGetNorms", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("calls the paginated endpoint with correct params", async () => {
    const fetchSpy = vi.spyOn(window, "fetch").mockResolvedValue(
      new Response(
        JSON.stringify({
          content: [{ eli: "eli/1", title: "Test" }],
          page: { size: 10, number: 0, totalElements: 1, totalPages: 1 },
        }),
      ),
    )

    const { useGetNorms } = await import("./normService")

    const page = ref(0)
    const size = ref(10)
    useGetNorms(page, size)

    await vi.waitFor(() => {
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms?page=0&size=10",
        expect.any(Object),
      )
    })

    page.value = 1
    await vi.waitFor(() => {
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms?page=1&size=10",
        expect.any(Object),
      )
    })
  })
})

describe("useGetNormWork", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("loads the data from the API", async () => {
    const fetchSpy = vi.spyOn(window, "fetch").mockResolvedValue(
      new Response(
        JSON.stringify({
          eli: "eli/bund/bgbl-1/2017/s419",
          title: "Test Norm",
        }),
      ),
    )

    const { useGetNormWork } = await import("./normService")
    const { NormWorkEli } = await import("@/lib/eli/NormWorkEli")

    useGetNormWork(NormWorkEli.fromString("eli/bund/bgbl-1/2017/s419"))

    await vi.waitFor(() => {
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419",
        expect.objectContaining({
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
          },
        }),
      )
    })
  })

  it("reloads when parameters change", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(
        new Response(JSON.stringify({ eli: "test", title: "Test" })),
      )

    const { useGetNormWork } = await import("./normService")
    const { NormWorkEli } = await import("@/lib/eli/NormWorkEli")

    const workEli = ref(NormWorkEli.fromString("eli/bund/bgbl-1/2017/s419"))
    useGetNormWork(workEli)

    await vi.waitFor(() => {
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419",
        expect.any(Object),
      )
    })

    workEli.value = NormWorkEli.fromString("eli/bund/bgbl-1/2017/s420")
    await vi.waitFor(() => {
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s420",
        expect.any(Object),
      )
    })
  })
})

describe("useGetNormExpressions", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()
  })

  it("loads the data from the API", async () => {
    const fetchSpy = vi.spyOn(window, "fetch").mockResolvedValue(
      new Response(
        JSON.stringify([
          {
            eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
            gegenstandslos: false,
          },
        ]),
      ),
    )

    const { useGetNormExpressions } = await import("./normService")
    const { NormWorkEli } = await import("@/lib/eli/NormWorkEli")

    useGetNormExpressions(NormWorkEli.fromString("eli/bund/bgbl-1/2017/s419"))

    await vi.waitFor(() => {
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/expressions",
        expect.objectContaining({
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
          },
        }),
      )
    })
  })

  it("reloads when parameters change", async () => {
    const fetchSpy = vi
      .spyOn(window, "fetch")
      .mockResolvedValue(new Response(JSON.stringify([])))

    const { useGetNormExpressions } = await import("./normService")
    const { NormWorkEli } = await import("@/lib/eli/NormWorkEli")

    const workEli = ref(NormWorkEli.fromString("eli/bund/bgbl-1/2017/s419"))
    useGetNormExpressions(workEli)

    await vi.waitFor(() => {
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s419/expressions",
        expect.any(Object),
      )
    })

    workEli.value = NormWorkEli.fromString("eli/bund/bgbl-1/2017/s420")
    await vi.waitFor(() => {
      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2017/s420/expressions",
        expect.any(Object),
      )
    })
  })
})
