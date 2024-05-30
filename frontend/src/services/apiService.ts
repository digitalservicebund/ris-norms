import { ofetch } from "ofetch"
import { createFetch } from "@vueuse/core"

/**
 * Fetch data from the backend api
 */
export const apiFetch = ofetch.create({
  baseURL: "/api/v1",
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
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
  },
})
