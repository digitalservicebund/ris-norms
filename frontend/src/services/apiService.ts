import { ofetch } from "ofetch"
import { createFetch } from "@vueuse/core"
import type { Router } from "vue-router"

let routerInstance: Router | null = null

export const initializeApiService = (router: Router) => {
  routerInstance = router
}

/** Fetch data from the backend api. */
export const apiFetch = ofetch.create({
  baseURL: "/api/v1",
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
  async onResponseError({ response }) {
    if (response?.status === 404 && routerInstance) {
      routerInstance.push({ name: "NotFound" }).catch((err) => {
        if (err.name !== "NavigationDuplicated") {
          console.error("Failed to navigate to 404 page:", err)
        }
      })
    }
  },
})

/** Fetch data from the backend api using useFetch. */
export const useApiFetch = createFetch({
  baseUrl: "/api/v1",
  options: {
    async beforeFetch({ options }) {
      options.headers = {
        Accept: "application/json",
        "Content-Type": "application/json",
        ...options.headers,
      }
      return { options }
    },
    onFetchError(fetchContext) {
      if (fetchContext.response?.status === 404 && routerInstance) {
        routerInstance.push({ name: "NotFound" }).catch((err) => {
          if (err.name !== "NavigationDuplicated") {
            console.error("Failed to navigate to 404 page:", err)
          }
        })
      }
      return fetchContext
    },
  },
})

/**
 * Special string that can be used in places where you want to express that
 * a URL should not be fetched or used in any way, but you are still required
 * to provide a string value (e.g. when providing a computed URL to useFetch).
 * Example:
 *
 * ```ts
 * const url = computed(() => someCondition ? 'example.com' : INVALID_URL)
 * useFetch(url, {
 *   beforeFetch(ctx) {
 *     if (url === INVALID_URL) ctx.abort()
 *   }
 * })
 */
export const INVALID_URL = "__invalid_url__"
