import type { Env } from "@/types/env"
import type { InjectionKey } from "vue"

/**
 * Fetches configuration for the current environment from the API. Unlike the
 * other services, this is not reactive, since it is intended to be fetched
 * once when the application initializes and then stay the same for the rest
 * of the session.
 *
 * @returns Configuration for the current environment
 */
export async function getEnv(): Promise<Env> {
  const env = await fetch("/environment").then((response) => {
    if (!response.ok) {
      throw new Error("Failed to load configuration", { cause: response })
    }

    return response.json()
  })

  return env
}

export const envInjectionKey: InjectionKey<Env> = Symbol()
