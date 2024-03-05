import { apiFetch } from "@/services/apiService"

export interface AmendingLaw {
  eli: string
  printAnnouncementGazette?: string
  digitalAnnouncementMedium?: string
  publicationDate: string
  printAnnouncementPage?: string
  digitalAnnouncementEdition?: string
  title?: string
  articles?: Article[]
}

interface Article {
  eid: string
  title: string
  enumeration: string
}

/**
 * Load all amending laws from the api
 */
export async function getAmendingLaws(): Promise<AmendingLaw[]> {
  return await apiFetch("/amending-laws")
}

/**
 * Load an amending law from the api
 *
 * @param eli Eli of the amending law
 */
export async function getAmendingLawByEli(eli: string): Promise<AmendingLaw> {
  return await apiFetch(`/amending-laws/${eli}`)
}

/**
 * Load the xml version of an amending law from the api
 *
 * @param eli Eli of the amending law
 */
export async function getAmendingLawXmlByEli(eli: string): Promise<string> {
  return await apiFetch(`/amending-laws/${eli}`, {
    headers: {
      Accept: "application/xml",
    },
  })
}
