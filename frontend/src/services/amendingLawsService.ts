import { ofetch } from "ofetch"

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

const apiFetch = ofetch.create({
  baseURL: "/api/v1",
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
})

export async function getAmendingLaws(): Promise<AmendingLaw[]> {
  return await apiFetch("/amending-laws")
}

export async function getAmendingLawByEli(eli: string): Promise<AmendingLaw> {
  return await apiFetch(`/amending-laws/${eli}`)
}
