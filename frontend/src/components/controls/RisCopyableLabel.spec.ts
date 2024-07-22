import { render, screen } from "@testing-library/vue"
import { describe, expect, test, vi } from "vitest"
import RisCopyableLabel from "./RisCopyableLabel.vue"
import { userEvent } from "@testing-library/user-event"

describe("RisCopyableLabel", () => {
  test("renders", () => {
    render(RisCopyableLabel, { props: { text: "Foo" } })
    expect(screen.getByText("Foo")).toBeInTheDocument()
  })

  test("renders an accessible label with the default value", () => {
    render(RisCopyableLabel, { props: { text: "Foo" } })

    expect(
      screen.getByRole("button", {
        name: "Wert in die Zwischenablage kopieren",
      }),
    ).toBeInTheDocument()
  })

  test("renders an accessible label with a custom value", () => {
    render(RisCopyableLabel, { props: { text: "Foo", name: "Bar" } })

    expect(
      screen.getByRole("button", {
        name: "Bar in die Zwischenablage kopieren",
      }),
    ).toBeInTheDocument()
  })

  test("copies the text if no value is provided", async () => {
    const user = userEvent.setup()
    const spy = vi.spyOn(navigator.clipboard, "writeText")
    render(RisCopyableLabel, { props: { text: "Foo" } })

    await user.click(screen.getByRole("button"))

    expect(spy).toHaveBeenCalledWith("Foo")
  })

  test("copies the value if provided", async () => {
    const user = userEvent.setup()
    const spy = vi.spyOn(navigator.clipboard, "writeText")
    render(RisCopyableLabel, { props: { text: "Foo", value: "Bar" } })

    await user.click(screen.getByRole("button"))

    expect(spy).toHaveBeenCalledWith("Bar")
  })
})
