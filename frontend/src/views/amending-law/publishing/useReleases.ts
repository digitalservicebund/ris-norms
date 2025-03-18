import { useGetReleases, usePostRelease } from "@/services/releaseService"
import type { Release } from "@/types/release"
import type { Ref } from "vue"
import { ref, watch } from "vue"
import type { UseFetchReturn } from "@vueuse/core"
import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"

/**
 * Read and update release information about the law with the specified ELI.
 *
 * @param eli Law from which the information should be taken
 */
export function useReleases(eli: Ref<NormExpressionEli>): UseFetchReturn<
  Release[]
> & {
  release: UseFetchReturn<Release>
} {
  const data: Ref<Release[]> = ref([])

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
