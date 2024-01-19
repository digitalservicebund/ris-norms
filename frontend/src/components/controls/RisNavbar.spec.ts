import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisNavbar from "./RisNavbar.vue"

describe("RisNavbar", () => {
  test("should show 'Rechtsinformationen' and 'des Bundes'", () => {
    render(RisNavbar)
    expect(screen.getByText("Rechtsinformationen")).toBeInTheDocument()
    expect(screen.getByText("des Bundes")).toBeInTheDocument()
  })
})
