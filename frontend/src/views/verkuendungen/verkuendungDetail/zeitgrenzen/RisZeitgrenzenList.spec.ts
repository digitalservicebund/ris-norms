import userEvent from "@testing-library/user-event"
import { render, screen, within } from "@testing-library/vue"
import InputText from "primevue/inputtext"
import { describe, expect, it } from "vitest"
import RisZeitgrenzenList from "./RisZeitgrenzenList.vue"
import { ButtonStub } from "@/test-utils/ButtonStub"

describe("risZeitgrenzenList", () => {
  it("shows an empty state when the items list is empty", () => {
    render(RisZeitgrenzenList, {
      props: {
        modelValue: [],
      },
      global: { stubs: { InputMask: InputText } },
    })

    expect(
      screen.getByText("Es wurden noch keine Geltungszeiten angelegt."),
    ).toBeVisible()
  })

  it("shows a list of items", () => {
    render(RisZeitgrenzenList, {
      props: {
        modelValue: [
          { id: "1", date: "2025-04-08", art: "INKRAFT" },
          { id: "2", date: "2025-04-10", art: "AUSSERKRAFT" },
        ],
      },
      global: { stubs: { InputMask: InputText } },
    })

    const items = screen.getAllByRole("listitem")
    expect(items).toHaveLength(2)

    expect(
      within(items[0]).getByRole("checkbox", { name: "unbestimmt" }),
    ).not.toBeChecked()
    expect(
      within(items[0]).getByRole("textbox", { name: "Geltungszeit" }),
    ).toHaveValue("08.04.2025")
    expect(
      within(items[0]).getByRole("combobox", { name: "Inkrafttreten" }),
    ).toBeVisible()

    expect(
      within(items[1]).getByRole("checkbox", { name: "unbestimmt" }),
    ).not.toBeChecked()
    expect(
      within(items[1]).getByRole("textbox", { name: "Geltungszeit" }),
    ).toHaveValue("10.04.2025")
    expect(
      within(items[1]).getByRole("combobox", { name: "Außerkrafttreten" }),
    ).toBeVisible()

    expect(
      screen.queryByText("Es wurden noch keine Geltungszeiten angelegt."),
    ).not.toBeInTheDocument()
  })

  it("emits the updated list of items when an item is changed", async () => {
    const user = userEvent.setup()
    const { emitted } = render(RisZeitgrenzenList, {
      props: {
        modelValue: [
          { id: "1", date: "2025-04-08", art: "INKRAFT" },
          { id: "2", date: "2025-04-10", art: "AUSSERKRAFT" },
        ],
      },
      global: { stubs: { InputMask: InputText } },
    })

    const [item] = screen.getAllByRole("listitem")
    const textbox = within(item).getByRole("textbox", { name: "Geltungszeit" })
    await user.clear(textbox)
    await user.type(textbox, "30.05.2025")

    expect(emitted("update:modelValue")).toContainEqual([
      [
        { id: expect.anything(), date: "2025-05-30", art: "INKRAFT" },
        { id: expect.anything(), date: "2025-04-10", art: "AUSSERKRAFT" },
      ],
    ])
  })

  it("removes an item from the list", async () => {
    const user = userEvent.setup()
    const { emitted } = render(RisZeitgrenzenList, {
      props: {
        modelValue: [
          { id: "1", date: "2025-04-08", art: "INKRAFT" },
          { id: "2", date: "2025-04-10", art: "AUSSERKRAFT" },
        ],
      },
      global: { stubs: { InputMask: InputText, Button: ButtonStub } },
    })

    const [, item] = screen.getAllByRole("listitem")
    await user.click(
      within(item).getByRole("button", {
        name: "Zeitgrenze vom 10.04.2025 entfernen",
      }),
    )

    expect(emitted("update:modelValue")).toContainEqual([
      [{ id: expect.anything(), date: "2025-04-08", art: "INKRAFT" }],
    ])
  })

  it("adds an item to the list", async () => {
    const user = userEvent.setup()
    const { emitted } = render(RisZeitgrenzenList, {
      props: {
        modelValue: [
          { id: "1", date: "2025-04-08", art: "INKRAFT" },
          { id: "2", date: "2025-04-10", art: "AUSSERKRAFT" },
        ],
      },
      global: { stubs: { InputMask: InputText, Button: ButtonStub } },
    })

    await user.click(
      screen.getByRole("button", { name: "Geltungszeit hinzufügen" }),
    )

    expect(emitted("update:modelValue")).toContainEqual([
      [
        { id: expect.anything(), date: "2025-04-08", art: "INKRAFT" },
        { id: expect.anything(), date: "2025-04-10", art: "AUSSERKRAFT" },
        { id: expect.anything(), date: "", art: "INKRAFT" },
      ],
    ])
  })

  it("adds an item to an empty list", async () => {
    const user = userEvent.setup()
    const { emitted } = render(RisZeitgrenzenList, {
      props: {
        modelValue: [],
      },
      global: { stubs: { InputMask: InputText } },
    })

    await user.click(
      screen.getByRole("button", { name: "Geltungszeit hinzufügen" }),
    )

    expect(emitted("update:modelValue")).toContainEqual([
      [{ id: expect.anything(), date: "", art: "INKRAFT" }],
    ])
  })

  it("should focus the Geltungszeit textbox when adding a new item", async () => {
    const { rerender } = render(RisZeitgrenzenList, {
      props: {
        modelValue: [],
      },
      global: { stubs: { InputMask: InputText } },
    })

    await rerender({ modelValue: [{ date: "", art: "INKRAFT" }] })

    expect(screen.getByRole("textbox", { name: "Geltungszeit" })).toHaveFocus()
  })

  it("does not allow adding more than 100 entries", () => {
    const id = (function* counter() {
      let i = 0
      while (true) yield ++i
    })()

    render(RisZeitgrenzenList, {
      props: {
        modelValue: Array(100).fill({
          id: id.next().value,
          date: "2025-04-08",
          art: "INKRAFT",
        }),
      },
      global: { stubs: { InputMask: InputText, Button: ButtonStub } },
    })

    expect(
      screen.getByRole("button", { name: "Geltungszeit hinzufügen" }),
    ).toBeDisabled()
  }, 30000)
})
