import { ofetch } from "ofetch"

export interface Procedure {
  eli: string
  printAnnouncementGazette: string
  printAnnouncementYear: number
  printAnnouncementPage: number
}

const apiFetch = ofetch.create({
  baseURL: "/api/v1/norms",
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
})

export async function getProcedures(): Promise<Procedure[]> {
  return await apiFetch("/procedures")
}

export async function getProcedureByEli(eli: string): Promise<Procedure> {
  return await apiFetch(`/procedures/${eli}`)
}
