import { ofetch } from "ofetch"
import { createFetch } from "@vueuse/core"
import type { Router } from "vue-router"

let routerInstance: Router | null = null

export const initializeApiService = (router: Router) => {
  routerInstance = router
}

/**
 * Fetch data from the backend api
 */
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

/**
 * Fetch data from the backend api using useFetch
//  */
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
