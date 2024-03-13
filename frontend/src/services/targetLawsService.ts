import { apiFetch } from "@/services/apiService"
import { TargetLaw } from "@/types/targetLaw"

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

/**
 * Load a preview of the target law after the provided amending law is applied to it.
 *
 * @param eli Eli of the target law
 * @param amendingLawXml XML of the amending law that should be used for creating the preview
 */
export async function previewTargetLaw(
  eli: string,
  amendingLawXml: string,
): Promise<string> {
  return await apiFetch(`/target-laws/${eli}/preview`, {
    method: "POST",
    headers: {
      Accept: "application/xml",
      "Content-Type": "application/xml",
    },
    body: amendingLawXml,
  })
}
