import { ofetch } from "ofetch"
import { createFetch, UseFetchReturn } from "@vueuse/core"
import type { Router } from "vue-router"

/**
 * The same as UseFetchReturn, but without the methods to get more specific useFetch instances.
 */
export type SimpleUseFetchReturn<T> = Omit<
  UseFetchReturn<T>,
  | "get"
  | "post"
  | "put"
  | "delete"
  | "patch"
  | "head"
  | "options"
  | "json"
  | "text"
  | "blob"
  | "arrayBuffer"
  | "formData"
>

let routerInstance: Router | null = null

export const initializeApiService = (router: Router) => {
  routerInstance = router
}

/**
 * Fetch data from the backend api.
 *
 * @deprecated Use `useApiFetch` instead.
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

/* -------------------------------------------------- *
 * Reactive API fetch                                 *
 * -------------------------------------------------- */

/** Fetch data from the backend api using useFetch. */
export const useApiFetch = createFetch({
  baseUrl: "/api/v1",

  options: {
    async beforeFetch({ options, url, cancel }) {
      // useFetch requires the URL to always be there, but in some cases we
      // can't construct a meaningful URL (e.g. because a required param is
      // missing). For those cases we introduce INVALID_URL as a convention
      // to tell useFetch to cancel the request.
      if (url.endsWith(INVALID_URL)) {
        cancel()
        return
      }

      options.headers = {
        Accept: "application/json",
        "Content-Type": "application/json",
        ...options.headers,
      }

      return { options }
    },

    onFetchError(fetchContext) {
      // We'll probably remove this again because we don't want a blanket
      // redirect to 404 if anything goes wrong.
      if (fetchContext.response?.status === 404 && routerInstance) {
        routerInstance.push({ name: "NotFound" }).catch((err) => {
          if (err.name !== "NavigationDuplicated") {
            console.error("Failed to navigate to 404 page:", err)
          }
        })
      }

      // this error is sometimes throws when previous requests are automatically aborted as
      // some of the data changed and refetch is true. It seems to only be throws when the request
      // is aborted before it was actually send.
      // We ignore this error as it (for some odd reason) isn't replaced once the second request finishes
      // successfully
      if (fetchContext.error.name === "AbortError") {
        return {
          ...fetchContext,
          error: null,
        }
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
