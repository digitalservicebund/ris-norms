import { userEvent } from "@testing-library/user-event"
import { render, screen } from "@testing-library/vue"
import InputText from "primevue/inputtext"
import { describe, expect, it } from "vitest"
import RisZeitgrenzenListItem from "./RisZeitgrenzenListItem.vue"

describe("risZeitgrenzenListItem", () => {
  it("shows the unbestimmt value", () => {
    render(RisZeitgrenzenListItem, {
      props: {
        index: 0,
        modelValue: { id: "1", date: "2025-04-08", art: "INKRAFT" },
      },
      global: { stubs: { InputMask: InputText } },
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
        modelValue: { id: "1", date: "2025-04-08", art: "INKRAFT" },
      },
      global: { stubs: { InputMask: InputText } },
    })

    // eslint-disable-next-line -- Purely visual component, no better selectors available
    expect(container.querySelector(".bg-highlight-2-default")).toBeVisible()
  })

  it("shows the Geltungszeit", () => {
    render(RisZeitgrenzenListItem, {
      props: {
        index: 1,
        modelValue: { id: "1", date: "2025-04-08", art: "INKRAFT" },
      },
      global: { stubs: { InputMask: InputText } },
    })

    expect(screen.getByRole("textbox", { name: "Geltungszeit" })).toHaveValue(
      "08.04.2025",
    )
  })

  it("shows the Art", () => {
    render(RisZeitgrenzenListItem, {
      props: {
        index: 0,
        modelValue: { id: "1", date: "2025-04-08", art: "AUSSERKRAFT" },
      },
      global: { stubs: { InputMask: InputText } },
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
        modelValue: { id: "1", date: "2025-04-08", art: "AUSSERKRAFT" },
      },
      global: { stubs: { InputMask: InputText } },
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
        modelValue: { id: "1", date: "2025-04-08", art: "AUSSERKRAFT" },
      },
      global: { stubs: { InputMask: InputText } },
    })

    const textbox = screen.getByRole("textbox", { name: "Geltungszeit" })
    await user.clear(textbox)
    await user.type(textbox, "09.04.2025")

    expect(emitted("update:modelValue")).toContainEqual([
      { id: expect.anything(), date: "2025-04-09", art: "AUSSERKRAFT" },
    ])
  })

  it("sets the date to empty string when clearing the Geltungszeit", async () => {
    const user = userEvent.setup()
    const { emitted } = render(RisZeitgrenzenListItem, {
      props: {
        index: 0,
        modelValue: { id: "1", date: "2025-04-08", art: "AUSSERKRAFT" },
      },
      global: { stubs: { InputMask: InputText } },
    })

    const textbox = screen.getByRole("textbox", { name: "Geltungszeit" })
    await user.clear(textbox)

    expect(emitted("update:modelValue")).toContainEqual([
      { id: expect.anything(), date: "", art: "AUSSERKRAFT" },
    ])
  })

  it("updates the model value with the new Art", async () => {
    const user = userEvent.setup()
    const { emitted } = render(RisZeitgrenzenListItem, {
      props: {
        index: 0,
        modelValue: { id: "1", date: "2025-04-08", art: "AUSSERKRAFT" },
      },
      global: { stubs: { InputMask: InputText } },
    })

    await user.click(screen.getByRole("combobox", { name: "Außerkrafttreten" }))
    await user.click(screen.getByRole("option", { name: "Inkrafttreten" }))

    expect(emitted("update:modelValue")).toEqual([
      [{ id: expect.anything(), date: "2025-04-08", art: "INKRAFT" }],
    ])
  })
})
