import { describe, it, expect, vi, beforeEach } from "vitest"
import { useAmendingLaw } from "./useAmendingLaw"
import { setActivePinia, createPinia } from "pinia"
import { ref } from "vue"
import * as amendingLawsService from "@/services/amendingLawsService"

vi.mock("@/services/amendingLawsService", () => ({
  getAmendingLawByEli: vi.fn(),
}))

describe("useAmendingLaw", () => {
  beforeEach(() => {
    setActivePinia(createPinia())
  })

  it("updates amending law on successful fetch", async () => {
    const eli = "test-eli"
    const mockAmendingLaw = {
      eli: eli,
      printAnnouncementGazette: "example",
      printAnnouncementMedium: undefined,
      publicationDate: "2023-01-01",
      printAnnouncementPage: "1",
      digitalAnnouncementEdition: undefined,
      articles: [
        {
          eli: "article eli 1",
          title: "article eli 1",
          enumeration: "1",
        },
      ],
    }
    const mockGetAmemdingLawByEli = vi.mocked(
      amendingLawsService.getAmendingLawByEli,
    )
    mockGetAmemdingLawByEli.mockResolvedValue(mockAmendingLaw)

    const { amendingLaw } = useAmendingLaw(eli)

    await vi.waitFor(() => expect(amendingLaw.value).toBeDefined())

    expect(amendingLaw.value).toEqual(mockAmendingLaw)
  })

  it("handles error from getAmendingLawByEli", async () => {
    const errorMessage = "Fetch error"
    vi.mocked(amendingLawsService.getAmendingLawByEli).mockRejectedValue(
      new Error(errorMessage),
    )

    const eli = "test-eli"
    const { amendingLaw } = useAmendingLaw(ref(eli))

    await vi.waitFor(() => expect(amendingLaw.value).toBeUndefined())
  })
})
