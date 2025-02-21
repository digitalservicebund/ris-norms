import { describe, it, expect, vi, beforeEach } from "vitest"
import { nextTick, ref } from "vue"
import * as announcementReleaseService from "@/services/releaseService"
import { Release } from "@/types/release"
import { UseFetchReturn } from "@vueuse/core"

describe("useReleases", () => {
  beforeEach(() => {
    vi.resetAllMocks()
  })

  it("should fetch the release status and update state", async () => {
    const mockReleaseStatus = {
      releaseAt: "2024-03-25T10:37:29.658954Z",
      norms: [
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/2024-01-01/regelungstext-1.xml",
        "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/2024-01-01/regelungstext-1.xml",
      ],
    }
    const dataRef = ref<Release[]>([])

    vi.spyOn(announcementReleaseService, "useGetReleases").mockReturnValue({
      data: dataRef,
    } as UseFetchReturn<Release[]>)

    vi.spyOn(announcementReleaseService, "usePostRelease").mockReturnValue({
      data: ref(),
    } as UseFetchReturn<Release>)

    const { useReleases } = await import(
      "@/views/amending-law/publishing/useReleases"
    )

    const eli = ref("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")
    const { data } = useReleases(eli)
    dataRef.value = [mockReleaseStatus]
    await nextTick()

    expect(data.value?.[0]).toEqual(mockReleaseStatus)
  })

  it("should send a release request and update state with the new release info", async () => {
    const newRelease = {
      releaseAt: "2024-03-25T11:00:00.000Z",
      norms: [
        "eli/bund/bgbl-1/1991/s2955/2023-12-30/1/deu/2024-01-01/regelungstext-2.xml",
      ],
    }

    const getDataRef = ref<Release[]>([])
    vi.spyOn(announcementReleaseService, "useGetReleases").mockReturnValue({
      data: getDataRef,
      execute: vi.fn().mockImplementation(() => {
        getDataRef.value = [newRelease]
      }),
    } as unknown as UseFetchReturn<Release[]>)

    const dataRef = ref<Release | null>(null)
    vi.spyOn(announcementReleaseService, "usePostRelease").mockReturnValue({
      data: dataRef,
      execute: vi.fn().mockImplementation(() => {
        dataRef.value = newRelease
      }),
    } as unknown as UseFetchReturn<Release>)

    const { useReleases } = await import(
      "@/views/amending-law/publishing/useReleases"
    )

    const eli = ref("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")
    const {
      data,
      release: { execute },
    } = useReleases(eli)

    await execute()
    expect(data.value).toEqual([newRelease])
  })
})
