import { ofetch } from "ofetch"

export interface AmendingLaw {
  eli: string
  printAnnouncementGazette?: string
  digitalAnnouncementMedium?: string
  publicationDate: string
  printAnnouncementPage?: string
  digitalAnnouncementEdition?: string
  articles?: Article[]
}

interface Article {
  eli: string
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
  return await apiFetch("/amendinglaw")
}

export async function getAmendingLawByEli(eli: string): Promise<AmendingLaw> {
  return await apiFetch(`/amendinglaw/${eli}`)
}
