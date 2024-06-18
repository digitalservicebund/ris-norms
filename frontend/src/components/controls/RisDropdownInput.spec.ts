import { userEvent } from "@testing-library/user-event"
import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisDropdowninput, {
  DropdownItem,
} from "@/components/controls/RisDropdownInput.vue"

function renderComponent(options?: {
  items?: DropdownItem[]
  modelValue?: string
  placeholder?: string
  label?: string
}) {
  const user = userEvent.setup()
  const props = {
    id: "identifier",
    modelValue: options?.modelValue,
    items: options?.items ?? [
      { label: "testItem1", value: "t1" },
      { label: "testItem2", value: "t2" },
      { label: "testItem3", value: "t3" },
    ],
    placeholder: options?.placeholder,
    label: options?.label,
  }
  const utils = render(RisDropdowninput, { props })
  return { user, props, ...utils }
}

describe("Dropdown Input", () => {
  test("renders a dropdown", () => {
    renderComponent()
    const input = screen.getByRole("combobox")
    expect(input).toBeInTheDocument()
  })

  test("renders the items", () => {
    renderComponent()
    const items = screen.getAllByRole("option")
    expect(items).toHaveLength(3)
  })

  test("renders the selected item", () => {
    renderComponent({ modelValue: "t2" })
    const input = screen.getByRole("combobox")
    expect(input).toHaveValue("t2")
  })

  test("renders a placeholder", () => {
    renderComponent({ placeholder: "test placeholder", modelValue: "" })
    const input = screen.getByRole("combobox")
    expect(input).toHaveAttribute("data-placeholder", "true")
  })

  test("does not render a placeholder if an item is selected", () => {
    renderComponent({ placeholder: "test placeholder", modelValue: "t2" })
    const input = screen.getByRole("combobox")
    expect(input).not.toHaveAttribute("data-placeholder")
  })

  test("does not render a placeholder if none exists", () => {
    renderComponent({ modelValue: "" })
    const input = screen.getByRole("combobox")
    expect(input).not.toHaveAttribute("data-placeholder")
  })

  test("does not contain a placeholder option if an item is selected", async () => {
    const { rerender } = renderComponent({
      placeholder: "test placeholder",
      modelValue: "",
    })

    const placeholder = screen.getByRole("option", { name: "test placeholder" })
    expect(placeholder).toBeInTheDocument()

    await rerender({ modelValue: "t2", placeholder: "test placeholder" })
    expect(placeholder).not.toBeInTheDocument()
  })

  test("placeholder is disabled", () => {
    renderComponent({ placeholder: "test placeholder" })
    expect(
      screen.getByRole("option", { name: "test placeholder" }),
    ).toBeDisabled()
  })

  test("emits a model update", async () => {
    const user = userEvent.setup()
    const { emitted } = renderComponent({ modelValue: "t1" })

    const input = screen.getByRole("combobox")
    expect(input).toHaveValue("t1")

    await user.selectOptions(input, "t2")
    expect(emitted("update:modelValue")).toEqual([["t2"]])
  })

  test("renders a label when provided and associates it with the dropdown", () => {
    const labelText = "Test Label"
    renderComponent({ label: labelText })

    const labelElement = screen.getByText<HTMLLabelElement>(labelText)
    expect(labelElement).toBeInTheDocument()

    const dropdown = screen.getByRole<HTMLSelectElement>("combobox")
    expect(labelElement.htmlFor).toBe(dropdown.id)
  })

  test("disables an item", () => {
    renderComponent({
      label: "Foo",
      items: [{ label: "Test", value: "test", disabled: true }],
    })

    expect(screen.getByRole("option", { name: "Test" })).toBeDisabled()
  })

  test("does not allow selecting a disabled item", async () => {
    const user = userEvent.setup()
    const { emitted } = renderComponent({
      items: [{ label: "Test", value: "test", disabled: true }],
    })

    await user.selectOptions(screen.getByRole("combobox"), "test")
    expect(emitted("update:modelValue")).toBeUndefined()
  })
})
