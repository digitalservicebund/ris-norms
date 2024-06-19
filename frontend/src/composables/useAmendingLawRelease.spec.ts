import { describe, it, expect, vi, beforeEach } from "vitest"
import { nextTick, ref } from "vue"
import * as announcementReleaseService from "@/services/announcementReleaseService"
import { AmendingLawRelease } from "@/types/amendingLawRelease"
import { UseFetchReturn } from "@vueuse/core"

describe("useAmendingLawRelease", () => {
  beforeEach(() => {
    vi.resetAllMocks()
  })

  it("should fetch the release status and update state", async () => {
    const mockReleaseStatus = {
      releaseAt: "2024-03-25T10:37:29.658954Z",
      amendingLawEli:
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      zf0Elis: ["eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1"],
    }
    const dataRef = ref<AmendingLawRelease | null>(null)

    vi.spyOn(announcementReleaseService, "useGetRelease").mockReturnValue({
      data: dataRef,
    } as UseFetchReturn<AmendingLawRelease>)

    vi.spyOn(announcementReleaseService, "usePutRelease").mockReturnValue({
      data: ref(),
    } as UseFetchReturn<AmendingLawRelease>)

    const { useAmendingLawRelease } = await import(
      "@/composables/useAmendingLawRelease"
    )

    const eli = ref("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")
    const { data } = useAmendingLawRelease(eli)
    dataRef.value = mockReleaseStatus
    await nextTick()

    expect(data.value).toEqual(mockReleaseStatus)
  })

  it("should send a release request and update state with the new release info", async () => {
    const newRelease = {
      releaseAt: "2024-03-25T11:00:00.000Z",
      amendingLawEli:
        "eli/bund/bgbl-1/2023/414/2023-12-30/1/deu/regelungstext-1",
      zf0Elis: ["eli/bund/bgbl-1/1991/s2955/2023-12-30/1/deu/regelungstext-2"],
    }
    const dataRef = ref<AmendingLawRelease | null>(null)

    vi.spyOn(announcementReleaseService, "useGetRelease").mockReturnValue({
      data: ref(null),
    } as UseFetchReturn<AmendingLawRelease>)

    vi.spyOn(announcementReleaseService, "usePutRelease").mockReturnValue({
      data: dataRef,
      execute: vi.fn().mockImplementation(() => {
        dataRef.value = newRelease
      }),
    } as unknown as UseFetchReturn<AmendingLawRelease>)

    const { useAmendingLawRelease } = await import(
      "@/composables/useAmendingLawRelease"
    )

    const eli = ref("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")
    const {
      data,
      release: { execute },
    } = useAmendingLawRelease(eli)

    await execute()

    expect(data.value).toEqual(newRelease)
  })
})
