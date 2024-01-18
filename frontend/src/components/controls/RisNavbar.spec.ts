import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisNavbar from "./RisNavbar.vue"

describe("RisNavbar", () => {
  test("should create", () => {
    render(RisNavbar)
    expect(screen.getByText("Rechtsinformationen")).toBeInTheDocument()
  })
})
