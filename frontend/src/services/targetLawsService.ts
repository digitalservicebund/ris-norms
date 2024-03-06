import { apiFetch } from "@/services/apiService"

export interface TargetLaw {
  eli: string
  title: string
}

/**
 * Load a target law from the api
 *
 * @param eli Eli of the target law
 */
export async function getTargetLawByEli(eli: string): Promise<TargetLaw> {
  return await apiFetch(`/target-laws/${eli}`)
}

/**
 * Load the xml version of a target law from the api
 *
 * @param eli Eli of the target law
 */
export async function getTargetLawXmlByEli(eli: string): Promise<string> {
  return await apiFetch(`/target-laws/${eli}`, {
    headers: {
      Accept: "application/xml",
    },
  })
}
