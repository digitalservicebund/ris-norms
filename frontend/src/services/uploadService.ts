import { computed } from "vue"
import type { SimpleUseFetchReturn } from "@/services/apiService"
import { useApiFetch } from "@/services/apiService"
import type { Norm } from "@/types/norm"
import { NormSchema } from "@/types/norm"

/**
 * Base service to upload a file (normal or force).
 *
 * @param force Whether the upload is forced or not.
 * @param file The file to be uploaded.
 */
async function useUploadService(
  force: boolean,
  file: File,
): Promise<SimpleUseFetchReturn<Norm>> {
  const formData = new FormData()
  formData.append("file", file)

  const useFetchReturn = await useApiFetch(
    computed(() => `/verkuendungen?force=${force}`),
    {
      method: "POST",
      body: formData,
    },
  ).json<unknown>()

  return {
    ...useFetchReturn,
    data: computed(() =>
      NormSchema.nullable().parse(useFetchReturn.data.value),
    ),
  }
}

/**
 * Force upload the file.
 *
 * @param file The file to be force uploaded.
 */
export function useForceUploadFile(
  file: File,
): Promise<SimpleUseFetchReturn<Norm>> {
  return useUploadService(true, file)
}
