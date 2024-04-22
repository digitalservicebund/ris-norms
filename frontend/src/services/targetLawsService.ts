import { apiFetch } from "@/services/apiService"
import { TargetLaw } from "@/types/targetLaw"
import { FetchOptions } from "ofetch"

/**
 * Load a target law from the api
 *
 * @param eli Eli of the target law
 * @param options Fetch options for the request
 */
export async function getTargetLawByEli(
  eli: string,
  options?: FetchOptions<"json">,
): Promise<TargetLaw> {
  return await apiFetch(`/target-laws/${eli}`, options)
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
 * Load the rendered html version of a target law from the api
 *
 * @param eli Eli of the target law
 * @param showMetadata Whether to include metadata in the rendered HTML

 */
export async function getTargetLawHtmlByEli(
  eli: string,
  showMetadata: boolean = true,
): Promise<string> {
  return await apiFetch(`/target-laws/${eli}?showMetadata=${showMetadata}`, {
    headers: {
      Accept: "text/html",
    },
  })
}

/**
 * Save the XML version of a target law to the API.
 *
 * @param eli ELI of the target law
 * @param xml New XML of the target law
 * @returns The newly saved XML
 */
export async function putTargetLawXml(
  eli: string,
  xml: string,
): Promise<string> {
  return await apiFetch<string>(`/target-laws/${eli}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/xml",
      Accept: "application/xml",
    },
    body: xml,
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

/**
 * Load the rendered HTML preview of the target law after the provided amending law is applied to it.
 *
 * @param eli Eli of the target law
 * @param amendingLawXml XML of the amending law that should be used for creating the preview
 */
export async function previewTargetLawAsHtml(
  eli: string,
  amendingLawXml: string,
): Promise<string> {
  return await apiFetch(`/target-laws/${eli}/preview`, {
    method: "POST",
    headers: {
      Accept: "text/html",
      "Content-Type": "application/xml",
    },
    body: amendingLawXml,
  })
}
