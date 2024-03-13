import { render, screen, within } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisAlert from "@/components/controls/RisAlert.vue"

describe("RisAlert", () => {
  test("renders slot content", () => {
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

  test("emits close event", () => {
    const renderResult = render(RisAlert, {
      props: {
        variant: "error",
      },
      slots: {
        default: "There was an error",
      },
    })

    const alert = screen.getByRole("alert")
    within(alert).getByRole("button", { name: "Schließen" }).click()

    expect(renderResult.emitted().close.length).toBe(1)
  })
})
