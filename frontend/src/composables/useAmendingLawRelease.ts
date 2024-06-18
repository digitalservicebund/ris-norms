import {
  useGetRelease,
  usePutRelease,
} from "@/services/announcementReleaseService"
import { AmendingLawRelease } from "@/types/amendingLawRelease"
import { Ref, ref, watch } from "vue"
import { UseFetchReturn } from "@vueuse/core"

/**
 * Read and update release information about the law with the specified ELI.
 *
 * @param eli Law from which the information should be taken
 */
export function useAmendingLawRelease(
  eli: Ref<string>,
): UseFetchReturn<AmendingLawRelease> & {
  release: UseFetchReturn<AmendingLawRelease>
} {
  const data: Ref<AmendingLawRelease | null> = ref(null)

  const getRelease = useGetRelease(eli)
  watch(getRelease.data, () => {
    data.value = getRelease.data.value
  })

  const putRelease = usePutRelease(eli)
  watch(putRelease.data, () => {
    data.value = putRelease.data.value
  })

  return {
    ...getRelease,
    data,
    release: putRelease,
  }
}
