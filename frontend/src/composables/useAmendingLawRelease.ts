import {
  getAmendingLawReleaseStatus,
  putReleaseAmendingLawXml,
} from "@/services/amendingLawReleaseService"
import { AmendingLawReleaseResponse } from "@/types/amendingLawReleaseResponse"
import { Ref, ref } from "vue"

export function useAmendingLawRelease(eli: Ref<string>) {
  const releasedAt = ref<Date | null>(null)
  const releasedElis = ref<AmendingLawReleaseResponse | null>(null)

  async function fetchReleaseStatus() {
    const response = await getAmendingLawReleaseStatus(eli.value)
    releasedAt.value = response.releaseAt ? new Date(response.releaseAt) : null
    releasedElis.value = response
  }

  async function releaseAmendingLaw() {
    const response = await putReleaseAmendingLawXml(eli.value)
    releasedAt.value = new Date(response.releaseAt)
    releasedElis.value = response
  }

  return { releasedAt, releasedElis, releaseAmendingLaw, fetchReleaseStatus }
}
