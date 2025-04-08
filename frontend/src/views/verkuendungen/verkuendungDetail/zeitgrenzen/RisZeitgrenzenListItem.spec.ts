import { userEvent } from "@testing-library/user-event"
import { render, screen } from "@testing-library/vue"
import { describe, expect, it } from "vitest"
import RisZeitgrenzenListItem from "./RisZeitgrenzenListItem.vue"

describe("risZeitgrenzenListItem", () => {
  it("shows the unbestimmt value", () => {
    render(RisZeitgrenzenListItem, {
      props: {
        index: 0,
        modelValue: { date: "2025-04-08", art: "inkrafttreten" },
      },
    })

    // Hardcoded to "false" for now
    expect(
      screen.getByRole("checkbox", { name: "unbestimmt" }),
    ).not.toBeChecked()
  })

  it("shows the color based on the index", () => {
    const { container } = render(RisZeitgrenzenListItem, {
      props: {
        index: 1,
        modelValue: { date: "2025-04-08", art: "inkrafttreten" },
      },
    })

    // eslint-disable-next-line -- Purely visual component, no better selectors available
    expect(container.querySelector(".bg-highlight-2-default")).toBeVisible()
  })

  it("shows the Geltungszeit", () => {
    render(RisZeitgrenzenListItem, {
      props: {
        index: 1,
        modelValue: { date: "2025-04-08", art: "inkrafttreten" },
      },
    })

    expect(screen.getByRole("textbox", { name: "Geltungszeit" })).toHaveValue(
      "2025-04-08",
    )
  })

  it("shows the Art", () => {
    render(RisZeitgrenzenListItem, {
      props: {
        index: 0,
        modelValue: { date: "2025-04-08", art: "ausserkrafttreten" },
      },
    })

    // Hardcoded to "false" for now
    expect(
      screen.getByRole("combobox", { name: "Außerkrafttreten" }),
    ).toBeVisible()
  })

  it("emits an event when deleting", async () => {
    const user = userEvent.setup()
    const { emitted } = render(RisZeitgrenzenListItem, {
      props: {
        index: 0,
        modelValue: { date: "2025-04-08", art: "ausserkrafttreten" },
      },
    })

    await user.click(
      screen.getByRole("button", {
        name: "Zeitgrenze vom 08.04.2025 entfernen",
      }),
    )

    expect(emitted("delete")).toBeTruthy()
  })

  it("updates the model value with the new Geltungszeit", async () => {
    const user = userEvent.setup()
    const { emitted } = render(RisZeitgrenzenListItem, {
      props: {
        index: 0,
        modelValue: { date: "2025-04-08", art: "ausserkrafttreten" },
      },
    })

    const textbox = screen.getByRole("textbox", { name: "Geltungszeit" })
    await user.clear(textbox)
    await user.type(textbox, "2025-04-09")

    expect(emitted("update:modelValue")).toContainEqual([
      { date: "2025-04-09", art: "ausserkrafttreten" },
    ])
  })

  it("updates the model value with the new Art", async () => {
    const user = userEvent.setup()
    const { emitted } = render(RisZeitgrenzenListItem, {
      props: {
        index: 0,
        modelValue: { date: "2025-04-08", art: "ausserkrafttreten" },
      },
    })

    await user.click(screen.getByRole("combobox", { name: "Außerkrafttreten" }))
    await user.click(screen.getByRole("option", { name: "Inkrafttreten" }))

    expect(emitted("update:modelValue")).toEqual([
      [{ date: "2025-04-08", art: "inkrafttreten" }],
    ])
  })
})
