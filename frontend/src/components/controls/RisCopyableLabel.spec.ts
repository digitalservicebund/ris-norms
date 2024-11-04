import { render, screen } from "@testing-library/vue"
import { describe, expect, it, vi } from "vitest"
import RisCopyableLabel from "./RisCopyableLabel.vue"
import { userEvent } from "@testing-library/user-event"

describe("risCopyableLabel", () => {
  it("renders", () => {
    render(RisCopyableLabel, { props: { text: "Foo" } })
    expect(screen.getByText("Foo")).toBeInTheDocument()
  })

  it("renders an accessible label with the default value", () => {
    render(RisCopyableLabel, { props: { text: "Foo" } })

    expect(
      screen.getByRole("button", {
        name: "Wert in die Zwischenablage kopieren",
      }),
    ).toBeInTheDocument()
  })

  it("renders an accessible label with a custom value", () => {
    render(RisCopyableLabel, { props: { text: "Foo", name: "Bar" } })

    expect(
      screen.getByRole("button", {
        name: "Bar in die Zwischenablage kopieren",
      }),
    ).toBeInTheDocument()
  })

  it("copies the text if no value is provided", async () => {
    const user = userEvent.setup()
    const spy = vi.spyOn(navigator.clipboard, "writeText")
    render(RisCopyableLabel, { props: { text: "Foo" } })

    await user.click(screen.getByRole("button"))

    expect(spy).toHaveBeenCalledWith("Foo")
  })

  it("copies the value if provided", async () => {
    const user = userEvent.setup()
    const spy = vi.spyOn(navigator.clipboard, "writeText")
    render(RisCopyableLabel, { props: { text: "Foo", value: "Bar" } })

    await user.click(screen.getByRole("button"))

    expect(spy).toHaveBeenCalledWith("Bar")
  })
})
