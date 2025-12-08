import { describe, it, expect, vi, beforeEach } from "vitest"
import { ref } from "vue"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

describe("verkuendungService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("useVerkuendungen()", () => {
    it("provides the data from the api", async () => {
      const mockData = [
        {
          eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1",
          frbrName: "bgbl-1",
          frbrDateVerkuendung: "2017-03-15",
          frbrNumber: "s419",
          articles: [
            {
              eid: "article eid 1",
              title: "article eid 1",
              enumeration: "Artikel 1",
            },
            {
              eid: "article eid 2",
              title: "article eli 2",
              enumeration: "Artikel 2",
            },
          ],
        },
      ]

      const useFetchMock = {
        data: { value: mockData },
        error: { value: null },
        isFetching: { value: false },
      }

      vi.doMock("@/services/apiService", () => ({
        useApiFetch: vi.fn().mockReturnValue({
          json: vi.fn().mockReturnValue(useFetchMock),
        }),
      }))

      const { useVerkuendungenService } =
        await import("@/services/verkuendungService")

      const result = useVerkuendungenService()

      const data = result.data.value!

      expect(data.length).toBe(1)
      expect(data[0].eli.toString()).toBe(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-verkuendung-1",
      )

      const { useApiFetch } = await import("@/services/apiService")
      expect(useApiFetch).toHaveBeenCalledExactlyOnceWith("/verkuendungen")
    })
  })

  describe("useGetVerkuendungService()", () => {
    it("fetches a specific verkuendung by its ELI", async () => {
      const mockVerkuendung = {
        eli: "eli/bund/bgbl-1/2025/s65/2025-02-27/1/deu/regelungstext-verkuendung-1",
        title: "Gesetz zur Anpassung des Mutterschutzgesetzes",
        frbrDateVerkuendung: "2025-02-27",
        dateAusfertigung: "2025-02-24",
        importedAt: "2025-02-24T08:12:00Z",
        fna: "8052-5, 860-5, 2030-2-30-2, 51-1-23",
      }

      const useApiFetch = vi.fn().mockReturnValue({
        data: ref(mockVerkuendung),
        json: vi.fn().mockReturnValue({
          data: ref(mockVerkuendung),
          error: ref(null),
          isFetching: ref(false),
          isFinished: ref(true),
        }),
      })

      vi.doMock("@/services/apiService", () => ({ useApiFetch }))

      const { useGetVerkuendungService } =
        await import("@/services/verkuendungService")

      const eliString =
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-verkuendung-1"
      const eli = ref<NormExpressionEli>(
        NormExpressionEli.fromString(eliString),
      )

      const { data } = useGetVerkuendungService(eli)

      expect(data.value?.eli).toEqual(
        DokumentExpressionEli.fromString(
          "eli/bund/bgbl-1/2025/s65/2025-02-27/1/deu/regelungstext-verkuendung-1",
        ),
      )
      expect(data.value?.title).toBe(
        "Gesetz zur Anpassung des Mutterschutzgesetzes",
      )
      expect(data.value?.frbrDateVerkuendung).toBe("2025-02-27")
      expect(data.value?.dateAusfertigung).toBe("2025-02-24")
      expect(data.value?.importedAt).toBe("2025-02-24T08:12:00Z")
      expect(data.value?.fna).toBe("8052-5, 860-5, 2030-2-30-2, 51-1-23")
      expect(useApiFetch).toHaveBeenCalledTimes(1)
    })
  })

  describe("useZielnormen()", () => {
    it("fetches zielnormen for a specific verkuendung ELI", async () => {
      const mockZielnormen = [
        {
          eli: "eli/bund/bgbl-1/2002/123/2002-05-15/1/deu/regelungstext-verkuendung-1",
          status: "inForce",
          frbrDateVerkuendung: "2002-05-15",
        },
        {
          eli: "eli/bund/bgbl-1/2004/789/2004-07-21/1/deu/regelungstext-verkuendung-1",
          status: "outOfForce",
          frbrDateVerkuendung: "2004-07-21",
        },
      ]

      const useApiFetch = vi.fn().mockReturnValue({
        data: ref(mockZielnormen),
        json: vi.fn().mockReturnValue({
          data: ref(mockZielnormen),
          error: ref(null),
          isFetching: ref(false),
          isFinished: ref(true),
        }),
      })

      vi.doMock("@/services/apiService", () => ({ useApiFetch }))

      const { useGetZielnormReferences } =
        await import("@/services/verkuendungService")

      const eli = ref(
        NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2002/123/2002-05-15/1/deu/regelungstext-verkuendung-1",
        ),
      )

      const { data } = useGetZielnormReferences(eli)

      expect(data.value?.length).toBe(2)
      expect(data.value?.[0].eli.toString()).toBe(
        "eli/bund/bgbl-1/2002/123/2002-05-15/1/deu/regelungstext-verkuendung-1",
      )
      expect(data.value?.[0].status).toBe("inForce")
      expect(data.value?.[0].frbrDateVerkuendung).toBe("2002-05-15")
      expect(data.value?.[1].eli.toString()).toBe(
        "eli/bund/bgbl-1/2004/789/2004-07-21/1/deu/regelungstext-verkuendung-1",
      )
      expect(data.value?.[1].status).toBe("outOfForce")
      expect(data.value?.[1].frbrDateVerkuendung).toBe("2004-07-21")
      expect(useApiFetch).toHaveBeenCalledTimes(1)
    })
  })
})
