import { getRelease, putRelease } from "@/services/announcementReleaseService"
import { AmendingLawRelease } from "@/types/amendingLawRelease"
import { Ref, ref } from "vue"

/**
 * Read and update release information about the law with the specified ELI.
 *
 * @param eli Law from which the information should be taken
 */
export function useAmendingLawRelease(eli: Ref<string>): {
  /** Date of the most recent release. */
  releasedAt: Ref<Date | null>

  /** Detail information on the most recent release. */
  releasedElis: Ref<AmendingLawRelease | null>

  /** Release the current version of the law. */
  releaseAmendingLaw: () => Promise<void>

  /**
   * Reload the current release status of the law. This information will then be
   * found in `releasedAt` and `releasedElis`.
   */
  fetchReleaseStatus: () => Promise<void>
} {
  const releasedAt = ref<Date | null>(null)
  const releasedElis = ref<AmendingLawRelease | null>(null)

  async function fetchReleaseStatus() {
    const response = await getRelease(eli.value)
    releasedAt.value = response.releaseAt ? new Date(response.releaseAt) : null
    releasedElis.value = response
  }

  async function releaseAmendingLaw() {
    const response = await putRelease(eli.value)
    releasedAt.value = new Date(response.releaseAt)
    releasedElis.value = response
  }

  return { releasedAt, releasedElis, releaseAmendingLaw, fetchReleaseStatus }
}
