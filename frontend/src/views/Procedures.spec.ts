import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import Procedures from "./Procedures.vue"

describe("Procedures", () => {
  test("should create", () => {
    render(Procedures)
    expect(screen.getByText("Vorgänge")).toBeInTheDocument()
  })
})
