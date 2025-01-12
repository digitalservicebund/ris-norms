import { useApiFetch } from "@/services/apiService"
import { UseFetchReturn } from "@vueuse/core"

/**
 * Fetch the logged-in user's name from the API.
 */
export function useGetUserName(): UseFetchReturn<{
  name: { firstName: string; lastName: string }
}> {
  return useApiFetch("/me").json()
}
