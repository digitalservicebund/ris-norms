import { describe, expect, test } from "vitest"
import RisLoadingSpinner from "./RisLoadingSpinner.vue"
import { render, screen } from "@testing-library/vue"

describe("RisLoadingSpinner", () => {
  test("renders", () => {
    render(RisLoadingSpinner)
    expect(screen.getByRole("status", { name: "Lädt..." })).toBeInTheDocument()
  })
})
