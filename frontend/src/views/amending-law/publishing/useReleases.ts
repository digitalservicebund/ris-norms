import { useGetReleases, usePostRelease } from "@/services/releaseService"
import { Release } from "@/types/release"
import { Ref, ref, watch } from "vue"
import { UseFetchReturn } from "@vueuse/core"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

/**
 * Read and update release information about the law with the specified ELI.
 *
 * @param eli Law from which the information should be taken
 */
export function useReleases(eli: Ref<DokumentExpressionEli>): UseFetchReturn<
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
