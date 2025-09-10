import { useAuthentication } from "@/lib/auth"
import { getFallbackError } from "@/lib/errorResponseMapper"
import type { UseFetchOptions, UseFetchReturn } from "@vueuse/core"
import { createFetch } from "@vueuse/core"

import type {
  Middleware,
  Client,
  FetchResponse,
  ParseAsResponse,
} from "openapi-fetch"
import createClient from "openapi-fetch"
import type { paths } from "@/lib/api"
import type { ShallowRef, ComputedRef, WatchSource } from "vue"
import { computed, ref, watch } from "vue"
import type { IsAny } from "@vueuse/shared"
import type {
  MediaType,
  ResponseObjectMap,
  SuccessResponse,
} from "openapi-typescript-helpers"
export const client: Client<paths> = createClient<paths>({ baseUrl: "/api/v1" })

const authMiddleware: Middleware = {
  async onRequest({ request }) {
    // Authorize requests
    const { tryRefresh, getHeaderValue } = useAuthentication()

    const hasValidSession = await tryRefresh()
    if (!hasValidSession) throw new Error("Invalid Session")

    request.headers.set("Authorization", getHeaderValue())

    return request
  },
}

client.use(authMiddleware)
client.use({
  async onRequest({ request, schemaPath, params }) {
    console.log(request.url, schemaPath, params)
  },
})

type Callback<T> =
  IsAny<T> extends true
    ? (...param: any) => void
    : [T] extends [void]
      ? (...param: unknown[]) => void
      : [T] extends [any[]]
        ? (...param: T) => void
        : (...param: [T, ...unknown[]]) => void

/**
 * Provides (most) of the api of useFetch while allowing to use an openapi-fetch client for loading the data.
 * @param executeFunction
 * @param refetchOptions
 * @param fetchOptions
 */
export function useOpenApiFetch<
  T extends Record<string | number, any>,
  Options,
  Media extends MediaType,
>(
  executeFunction: (
    abortSignal: AbortSignal,
  ) => Promise<FetchResponse<T, Options, Media>>,
  refetchOptions: WatchSource<unknown>,
  fetchOptions: UseFetchOptions = {},
): SimpleUseFetchReturn<
  ParseAsResponse<SuccessResponse<ResponseObjectMap<T>, Media>, Options>
> {
  const isFinished: ShallowRef<boolean> = ref(false)
  const statusCode: ShallowRef<number | null> = ref(null)
  const response: ShallowRef<Response | null> = ref(null)
  const error: ShallowRef<any> = ref(null)
  const data: ShallowRef<any | null> = ref(null)
  const isFetching: ShallowRef<boolean> = ref(false)

  const abortController = new AbortController()
  const canAbort: ComputedRef<boolean> = computed(() => isFetching.value)
  const aborted: ShallowRef<boolean> = ref(false)
  const abort = abortController.abort
  abortController.signal.addEventListener("abort", () => {
    aborted.value = true
  })

  const onFetchResponseListener = new Set<Callback<Response>>()
  const onFetchResponse: (fn: Callback<Response>) => {
    off: () => void
  } = (fn) => {
    onFetchResponseListener.add(fn)

    return {
      off: () => {
        onFetchResponseListener.delete(fn)
      },
    }
  }
  const onFetchErrorListener = new Set<Callback<void>>()
  const onFetchError: (fn: Callback<void>) => {
    off: () => void
  } = (fn) => {
    onFetchErrorListener.add(fn)
    return {
      off: () => {
        onFetchErrorListener.delete(fn)
      },
    }
  }
  const onFetchFinallyListener = new Set<Callback<void>>()
  const onFetchFinally: (fn: Callback<void>) => {
    off: () => void
  } = (fn) => {
    onFetchFinallyListener.add(fn)
    return {
      off: () => {
        onFetchFinallyListener.delete(fn)
      },
    }
  }

  const execute: (throwOnFailed?: boolean) => Promise<any> = async () => {
    aborted.value = false
    isFetching.value = true
    isFinished.value = false
    response.value = null
    error.value = null

    try {
      const result = await executeFunction(abortController.signal)

      response.value = result.response
      statusCode.value = result.response.status
      error.value = result.error
      data.value = result.data
    } catch (thrownError: unknown) {
      response.value = null
      statusCode.value = null
      error.value = thrownError
      data.value = null
    }

    isFetching.value = false
    isFinished.value = true

    if (data.value) {
      onFetchResponseListener.forEach((onResponse) => {
        onResponse(response.value!)
      })
    }

    if (error.value) {
      onFetchErrorListener.forEach((onError) => {
        onError()
      })
    }

    onFetchFinallyListener.forEach((onFinally) => {
      onFinally()
    })
  }

  if (fetchOptions.immediate !== false) {
    execute()
  }

  if (fetchOptions.refetch !== true) {
    watch(refetchOptions, () => {
      execute()
    })
  }

  return {
    isFetching,
    isFinished,
    response,
    data,
    error,
    statusCode,
    canAbort,
    aborted,
    abort,
    onFetchError,
    onFetchFinally,
    onFetchResponse,
    execute,
  }
}

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

      // Only set the Content-Type if the body is not FormData
      if (!(options.body instanceof FormData)) {
        options.headers = {
          Accept: "application/json",
          "Content-Type": "application/json",
          ...options.headers,
        }
      } else {
        // Let the browser handle the Content-Type for FormData
        options.headers = {
          Accept: "application/json",
          ...options.headers,
        }
      }

      // Authorize requests
      const { addAuthorizationHeader, tryRefresh } = useAuthentication()

      const hasValidSession = await tryRefresh()
      if (!hasValidSession) cancel()

      options.headers = addAuthorizationHeader(options.headers)

      return { options }
    },

    onFetchError(fetchContext) {
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

      // In the useFetch implementation, `fetchContext.error` is the only thing
      // that will be provided to the UI by default. This is only an error
      // object though and access to the response data of an error is not possible.
      //
      // There is an option to replace the normal `data` property returned by
      // useFetch with the error response, but that would mean that `data` can
      // have different contents depending on the success/failure of the request,
      // and would require lots of type checking wherever we need to access
      // the response data.
      //
      // Since we're only ever interested in the data and never the error object,
      // we'll replace the `fetchContext.error` with the response data so we can
      // access it in the UI.
      //
      // For `.text()` calls, the error response comes as a string that needs
      // to be parsed as JSON. We check the type of the data and parse it if
      // it's a string.
      let baseError

      if (typeof fetchContext.data === "string") {
        try {
          baseError = JSON.parse(fetchContext.data)
        } catch {
          baseError = getFallbackError()
        }
      } else {
        baseError = fetchContext.data ?? getFallbackError()
      }

      fetchContext.error = {
        ...baseError,
        status: fetchContext.response?.status ?? null,
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
