import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import type { Zeitgrenze } from "@/types/zeitgrenze"
import { flushPromises } from "@vue/test-utils"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { nextTick, ref } from "vue"

vi.mock("@/lib/auth", () => {
  return {
    useAuthentication: () => ({
      addAuthorizationHeader: (init: HeadersInit) => ({ ...init }),
      tryRefresh: vi.fn().mockReturnValue(true),
    }),
  }
})

describe("zeitgrenzenService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("useGeltungszeitenHtml", () => {
    it("fetches the HTML content of a norm's entry into force section", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response("<div></div>"))

      const { useGeltungszeitenHtml } = await import(
        "@/services/zeitgrenzenService"
      )

      const { data, isFinished } = useGeltungszeitenHtml(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        ),
      )

      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toBe("<div></div>")

      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/articles?refersTo=geltungszeitregel",
        expect.objectContaining({
          headers: expect.objectContaining({ Accept: "text/html" }),
        }),
      )
    })

    it("reacts to changes of the ELI", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValueOnce(new Response("<div>1</div>"))
        .mockResolvedValueOnce(new Response("<div>2</div>"))

      const { useGeltungszeitenHtml } = await import(
        "@/services/zeitgrenzenService"
      )

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        ),
      )
      const { data, isFinished } = useGeltungszeitenHtml(eli)
      await vi.waitUntil(() => isFinished.value)
      expect(data.value).toBe("<div>1</div>")

      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1",
      )
      await nextTick()
      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toBe("<div>2</div>")

      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/articles?refersTo=geltungszeitregel",
        expect.anything(),
      )

      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/norms/eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-1/articles?refersTo=geltungszeitregel",
        expect.anything(),
      )
    })
  })

  describe("useGetZeitgrenzen", () => {
    it("loads the Zeitgrenzen of a norm", async () => {
      const response: Zeitgrenze[] = [
        { date: "2023-11-01", art: "inkrafttreten" },
        { date: "2023-12-01", art: "ausserkrafttreten" },
      ]

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify(response)))

      const { useGetZeitgrenzen } = await import(
        "@/services/zeitgrenzenService"
      )

      const { isFinished, data } = useGetZeitgrenzen(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        ),
      )

      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toEqual(response)

      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/zeitgrenzen",
        expect.anything(),
      )
    })

    it("doesn't load Zeitgrenzen when no ELI is specified", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify([])))

      const { useGetZeitgrenzen } = await import(
        "@/services/zeitgrenzenService"
      )

      useGetZeitgrenzen(undefined)
      await flushPromises()
      expect(fetchSpy).not.toHaveBeenCalled()
    })

    it("reloads when the ELI is changed", async () => {
      const response: Zeitgrenze[] = [
        { date: "2023-11-01", art: "inkrafttreten" },
        { date: "2023-12-01", art: "ausserkrafttreten" },
      ]

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify(response)))

      const { useGetZeitgrenzen } = await import(
        "@/services/zeitgrenzenService"
      )

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        ),
      )

      const { isFinished } = useGetZeitgrenzen(eli)
      await vi.waitUntil(() => isFinished.value)

      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/zeitgrenzen",
        expect.anything(),
      )

      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2023/413/2023-12-30/1/deu/regelungstext-1",
      )
      await nextTick()
      await flushPromises()

      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/eli/bund/bgbl-1/2023/413/2023-12-30/1/deu/regelungstext-1/zeitgrenzen",
        expect.anything(),
      )
    })
  })

  describe("usePutZeitgrenzen", () => {
    it("updates the Zeitgrenzen", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify([])))

      const { usePutZeitgrenzen } = await import(
        "@/services/zeitgrenzenService"
      )

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        ),
      )
      const payload = ref<Zeitgrenze[]>([
        { date: "2023-11-01", art: "inkrafttreten" },
        { date: "2023-12-01", art: "ausserkrafttreten" },
      ])
      const { execute } = usePutZeitgrenzen(eli, payload)

      await execute()
      await flushPromises()

      expect(fetchSpy).toHaveBeenCalledWith(
        "/api/v1/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1/zeitgrenzen",
        expect.objectContaining({
          body: JSON.stringify(payload.value),
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
          },
          method: "PUT",
        }),
      )
    })

    it("returns the updated Zeitgrenzen", async () => {
      vi.spyOn(global, "fetch").mockResolvedValue(
        new Response(
          JSON.stringify([{ date: "2025-11-01", art: "inkrafttreten" }]),
        ),
      )

      const { usePutZeitgrenzen } = await import(
        "@/services/zeitgrenzenService"
      )

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        ),
      )
      const payload = ref<Zeitgrenze[]>([
        { date: "2023-11-01", art: "inkrafttreten" },
        { date: "2023-12-01", art: "ausserkrafttreten" },
      ])
      const { execute, data } = usePutZeitgrenzen(eli, payload)

      await execute()
      await flushPromises()

      expect(data.value).toEqual([{ date: "2025-11-01", art: "inkrafttreten" }])
    })

    it("only refetches when executed manually", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify([])))

      const { usePutZeitgrenzen } = await import(
        "@/services/zeitgrenzenService"
      )

      const eli = ref(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        ),
      )
      const payload = ref([])
      const { execute } = usePutZeitgrenzen(eli, payload)

      await flushPromises()
      expect(fetchSpy).not.toHaveBeenCalled()

      eli.value = DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2023/413/2023-12-30/1/deu/regelungstext-1",
      )

      await flushPromises()
      expect(fetchSpy).not.toHaveBeenCalled()

      await execute()
      expect(fetchSpy).toHaveBeenCalled()
    })

    it("does nothing if no ELI is specified", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify([])))

      const { usePutZeitgrenzen } = await import(
        "@/services/zeitgrenzenService"
      )

      const eli = ref(undefined)
      const payload = ref([])
      const { execute } = usePutZeitgrenzen(eli, payload)

      await execute()
      await flushPromises()

      expect(fetchSpy).not.toHaveBeenCalled()
    })
  })
})
