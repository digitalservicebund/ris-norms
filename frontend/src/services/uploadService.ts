import { UseFetchReturn } from "@vueuse/core"
import { computed } from "vue"
import { useApiFetch } from "@/services/apiService"
import { Norm } from "@/types/norm"

/**
 * Base service to upload a file (normal or force).
 *
 * @param force Whether the upload is forced or not.
 * @param file The file to be uploaded.
 */
function useUploadService(force: boolean, file: File): UseFetchReturn<Norm> {
  const formData = new FormData()
  formData.append("file", file)

  return useApiFetch(
    computed(() => `/announcements?force=${force}`),
    {
      method: "POST",
      body: formData,
    },
  ).json()
}

/**
 * Force upload the file.
 *
 * @param file The file to be force uploaded.
 */
export function useForceUploadFile(file: File): UseFetchReturn<Norm> {
  return useUploadService(true, file)
}
