import { ofetch } from "ofetch"

/**
 * Fetch data from the backend api
 */
export const apiFetch = ofetch.create({
  baseURL: "/api/v1",
  headers: {
    Accept: "application/json",
    "Content-Type": "application/json",
  },
})
