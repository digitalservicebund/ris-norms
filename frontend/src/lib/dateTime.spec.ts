import { describe, expect, it } from "vitest"
import { formatDate, formatDateTime } from "./dateTime"

describe("formatDate", () => {
  it("formats a date", () => {
    const formatted = formatDate(new Date(2025, 3, 7))
    expect(formatted).toEqual("07.04.2025")
  })

  it("formats a string", () => {
    const formatted = formatDate("2025-04-07")
    expect(formatted).toEqual("07.04.2025")
  })

  it("handles an invalid string input", () => {
    const formatted = formatDate("this will break")
    expect(formatted).toEqual("Ungültiges Datum")
  })
})

describe("formatDateTime", () => {
  it("formats a date", () => {
    const formatted = formatDateTime(new Date(2025, 3, 7, 17, 50))
    expect(formatted).toEqual("07.04.2025, 17:50")
  })

  it("formats a string", () => {
    const formatted = formatDateTime("2025-04-07T17:50:00.000Z")
    expect(formatted).toEqual("07.04.2025, 17:50")
  })

  it("handles an invalid string input", () => {
    const formatted = formatDateTime("this will break")
    expect(formatted).toEqual("Ungültiges Datum")
  })
})
