import type { Zeitgrenze } from "@/types/zeitgrenze"
import { flushPromises } from "@vue/test-utils"
import { beforeEach, describe, expect, it, vi } from "vitest"
import { nextTick, ref } from "vue"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"

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

      const { useGeltungszeitenHtml } =
        await import("@/services/zeitgrenzenService")

      const { data, isFinished } = useGeltungszeitenHtml(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu",
        ),
      )

      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toBe("<div></div>")

      expect(fetchSpy).toHaveBeenCalledExactlyOnceWith(
        "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1/articles?refersTo=geltungszeitregel&refersTo=geltungszeitregel-inkrafttreten&refersTo=geltungszeitregel-ausserkrafttreten",
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

      const { useGeltungszeitenHtml } =
        await import("@/services/zeitgrenzenService")

      const eli = ref(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu",
        ),
      )
      const { data, isFinished } = useGeltungszeitenHtml(eli)
      await vi.waitUntil(() => isFinished.value)
      expect(data.value).toBe("<div>1</div>")

      eli.value = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu",
      )
      await nextTick()
      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toBe("<div>2</div>")

      expect(fetchSpy).toHaveBeenCalledTimes(2)
      expect(fetchSpy).toHaveBeenNthCalledWith(
        1,
        "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1/articles?refersTo=geltungszeitregel&refersTo=geltungszeitregel-inkrafttreten&refersTo=geltungszeitregel-ausserkrafttreten",
        expect.anything(),
      )
      expect(fetchSpy).toHaveBeenNthCalledWith(
        2,
        "/api/v1/norms/eli/bund/bgbl-1/2022/s12/2022-01-23/1/deu/regelungstext-verkuendung-1/articles?refersTo=geltungszeitregel&refersTo=geltungszeitregel-inkrafttreten&refersTo=geltungszeitregel-ausserkrafttreten",
        expect.anything(),
      )
    })
  })

  describe("useGetZeitgrenzen", () => {
    it("loads the Zeitgrenzen of a norm", async () => {
      const response: Zeitgrenze[] = [
        { id: "1", date: "2023-11-01", art: "INKRAFT" },
        { id: "2", date: "2023-12-01", art: "AUSSERKRAFT" },
      ]

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify(response)))

      const { useGetZeitgrenzen } =
        await import("@/services/zeitgrenzenService")

      const { isFinished, data } = useGetZeitgrenzen(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu",
        ),
      )

      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toEqual(response)

      expect(fetchSpy).toHaveBeenCalledExactlyOnceWith(
        "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/zeitgrenzen",
        expect.anything(),
      )
    })

    it("doesn't load Zeitgrenzen when no ELI is specified", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify([])))

      const { useGetZeitgrenzen } =
        await import("@/services/zeitgrenzenService")

      useGetZeitgrenzen(undefined)
      await flushPromises()
      expect(fetchSpy).not.toHaveBeenCalled()
    })

    it("reloads when the ELI is changed", async () => {
      const response: Zeitgrenze[] = [
        { id: "1", date: "2023-11-01", art: "INKRAFT" },
        { id: "2", date: "2023-12-01", art: "AUSSERKRAFT" },
      ]

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify(response)))

      const { useGetZeitgrenzen } =
        await import("@/services/zeitgrenzenService")

      const eli = ref(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu",
        ),
      )

      const { isFinished } = useGetZeitgrenzen(eli)
      await vi.waitUntil(() => isFinished.value)

      eli.value = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/2023/413/2023-12-30/1/deu",
      )
      await nextTick()
      await flushPromises()

      expect(fetchSpy).toHaveBeenCalledTimes(2)

      expect(fetchSpy).toHaveBeenNthCalledWith(
        1,
        "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/zeitgrenzen",
        expect.anything(),
      )

      expect(fetchSpy).toHaveBeenNthCalledWith(
        2,
        "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-30/1/deu/zeitgrenzen",
        expect.anything(),
      )
    })
  })

  describe("usePutZeitgrenzen", () => {
    it("updates the Zeitgrenzen", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify([])))

      const { usePutZeitgrenzen } =
        await import("@/services/zeitgrenzenService")

      const eli = ref(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu",
        ),
      )
      const payload = ref<Zeitgrenze[]>([
        { id: "1", date: "2023-11-01", art: "INKRAFT" },
        { id: "2", date: "2023-12-01", art: "AUSSERKRAFT" },
      ])
      const { execute } = usePutZeitgrenzen(eli, payload)

      await execute()
      await flushPromises()

      expect(fetchSpy).toHaveBeenCalledExactlyOnceWith(
        "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/zeitgrenzen",
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

    it("updates the Zeitgrenzen if data is null", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify([])))

      const { usePutZeitgrenzen } =
        await import("@/services/zeitgrenzenService")

      const eli = ref(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu",
        ),
      )
      const payload = ref(null)
      const { execute } = usePutZeitgrenzen(eli, payload)

      await execute()
      await flushPromises()

      expect(fetchSpy).toHaveBeenCalledExactlyOnceWith(
        "/api/v1/norms/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/zeitgrenzen",
        expect.objectContaining({
          body: JSON.stringify([]),
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
          JSON.stringify([{ id: "id", date: "2025-11-01", art: "INKRAFT" }]),
        ),
      )

      const { usePutZeitgrenzen } =
        await import("@/services/zeitgrenzenService")

      const eli = ref(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu",
        ),
      )
      const payload = ref<Zeitgrenze[]>([
        { id: "1", date: "2023-11-01", art: "INKRAFT" },
        { id: "2", date: "2023-12-01", art: "AUSSERKRAFT" },
      ])
      const { execute, data } = usePutZeitgrenzen(eli, payload)

      await execute()
      await flushPromises()

      expect(data.value).toEqual([
        { id: "id", date: "2025-11-01", art: "INKRAFT" },
      ])
    })

    it("only refetches when executed manually", async () => {
      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify([])))

      const { usePutZeitgrenzen } =
        await import("@/services/zeitgrenzenService")

      const eli = ref(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu",
        ),
      )
      const payload = ref([])
      const { execute } = usePutZeitgrenzen(eli, payload)

      await flushPromises()
      expect(fetchSpy).not.toHaveBeenCalled()

      eli.value = NormExpressionEli.fromString(
        "eli/bund/bgbl-1/2023/413/2023-12-30/1/deu",
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

      const { usePutZeitgrenzen } =
        await import("@/services/zeitgrenzenService")

      const eli = ref(undefined)
      const payload = ref([])
      const { execute } = usePutZeitgrenzen(eli, payload)

      await execute()
      await flushPromises()

      expect(fetchSpy).not.toHaveBeenCalled()
    })
  })
})
