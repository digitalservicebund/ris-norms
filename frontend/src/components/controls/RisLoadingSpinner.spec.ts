import { describe, expect, it } from "vitest"
import RisLoadingSpinner from "./RisLoadingSpinner.vue"
import { render, screen } from "@testing-library/vue"

describe("risLoadingSpinner", () => {
  it("renders", () => {
    render(RisLoadingSpinner)
    expect(screen.getByRole("status", { name: "Lädt..." })).toBeInTheDocument()
  })
})
