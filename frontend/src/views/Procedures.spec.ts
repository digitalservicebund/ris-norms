import { render, screen } from "@testing-library/vue"
import { it } from "vitest"
import Procedures from "./Procedures.vue"

it("Shows procedures title", async () => {
  render(Procedures)

  screen.getByText("Procedures page")
})
