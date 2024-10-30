import {
  useGetReleases,
  usePostRelease,
} from "@/services/announcementReleaseService"
import { AmendingLawRelease } from "@/types/amendingLawRelease"
import { Ref, ref, watch } from "vue"
import { UseFetchReturn } from "@vueuse/core"

/**
 * Read and update release information about the law with the specified ELI.
 *
 * @param eli Law from which the information should be taken
 */
export function useAmendingLawReleases(eli: Ref<string>): UseFetchReturn<
  AmendingLawRelease[]
> & {
  release: UseFetchReturn<AmendingLawRelease>
} {
  const data: Ref<AmendingLawRelease[]> = ref([])

  const getReleases = useGetReleases(eli)
  watch(getReleases.data, () => {
    data.value = getReleases.data.value ?? []
  })

  const postRelease = usePostRelease(eli)
  watch(postRelease.data, () => {
    getReleases.execute()
  })

  return {
    ...getReleases,
    data,
    release: postRelease,
  }
}
