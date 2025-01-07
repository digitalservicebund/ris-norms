import { UseFetchReturn } from "@vueuse/core"
import { useApiFetch } from "@/services/apiService"

/**
 * Service to log out the user.
 *
 * Calls the backend `/logout` endpoint.
 */
export function useLogoutService(): UseFetchReturn<void> {
  return useApiFetch("/logout", {
    method: "POST",
  })
}
