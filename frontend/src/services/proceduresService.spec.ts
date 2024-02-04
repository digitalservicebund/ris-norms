import { describe, it, expect } from "vitest"
import { getProcedures } from "./proceduresService"

describe("getProcedures", () => {
  it("returns an array of procedures with the correct structure", () => {
    const procedures = getProcedures()

    const expectedStructure = {
      eli: "string",
      printAnnouncementGazette: "string",
      printAnnouncementYear: "number",
      printAnnouncementNumber: "number",
      printAnnouncementPage: "number",
      publicationDate: "object",
      fna: "string",
    }

    expect(procedures).toBeInstanceOf(Array)

    procedures.forEach((procedure) => {
      Object.entries(expectedStructure).forEach(([key, type]) => {
        expect(procedure).toHaveProperty(key)
        expect(typeof procedure[key as keyof typeof procedure]).toBe(type)
      })
    })
  })
})
