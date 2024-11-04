import { render, screen, within } from "@testing-library/vue"
import { describe, expect, it } from "vitest"
import RisAlert from "@/components/controls/RisAlert.vue"

describe("risAlert", () => {
  it("renders slot content", () => {
    render(RisAlert, {
      props: {
        variant: "success",
      },
      slots: {
        default: "This was a success",
      },
    })

    const alert = screen.getByRole("alert")
    expect(within(alert).getByText("This was a success")).toBeInTheDocument()
  })

  it("emits close event", () => {
    const { emitted } = render(RisAlert, {
      props: {
        variant: "error",
      },
      slots: {
        default: "There was an error",
      },
    })

    const alert = screen.getByRole("alert")
    within(alert).getByRole("button", { name: "Schließen" }).click()

    expect(emitted().close.length).toBe(1)
  })
})
