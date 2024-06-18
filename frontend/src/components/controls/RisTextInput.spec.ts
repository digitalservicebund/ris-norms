import RisTextInput from "@/components/controls/RisTextInput.vue"
import { userEvent } from "@testing-library/user-event"
import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"

function renderComponent(options?: {
  modelValue?: string
  placeholder?: string
  readOnly?: boolean
  size?: InstanceType<typeof RisTextInput>["$props"]["size"]
  label?: string
}) {
  const user = userEvent.setup()
  const props = {
    id: "identifier",
    modelValue: options?.modelValue,
    placeholder: options?.placeholder,
    readOnly: options?.readOnly,
    size: options?.size,
    label: options?.label,
  }
  const utils = render(RisTextInput, { props })
  return { user, props, ...utils }
}

describe("TextInput", () => {
  test("shows an text input element", () => {
    renderComponent()
    const input = screen.queryByRole("textbox")
    expect(input).toBeInTheDocument()
  })

  test("shows the value", () => {
    renderComponent({ modelValue: "test" })
    const input: HTMLInputElement = screen.getByRole("textbox")
    expect(input).toHaveValue("test")
  })

  test("shows input with a placeholder", () => {
    renderComponent({ placeholder: "Test Placeholder" })
    const input = screen.queryByPlaceholderText("Test Placeholder")
    expect(input).toBeInTheDocument()
  })

  test("emits model update event when user types into input", async () => {
    const { emitted } = renderComponent()
    const input = screen.getByRole("textbox")
    await userEvent.type(input, "a")
    expect(emitted("update:modelValue")).toEqual([["a"]])
  })

  test("emits blur event when input loses focus", async () => {
    const { emitted } = renderComponent()
    const input = screen.getByRole("textbox")
    await userEvent.type(input, "a")
    await userEvent.tab()
    expect(emitted("blur")).toBeTruthy()
  })

  test("renders a read-only input", () => {
    renderComponent({ readOnly: true })
    const input = screen.getByRole("textbox")
    expect(input).toHaveAttribute("readonly")
  })

  test("renders the small variant by default", () => {
    renderComponent()
    const input = screen.getByRole("textbox")
    expect(input).not.toHaveClass("ds-input-medium")
    expect(input).toHaveClass("ds-input-small")
  })

  test("renders the regular variant", () => {
    renderComponent({ size: "regular" })
    const input = screen.getByRole("textbox")
    expect(input).not.toHaveClass("ds-input-medium")
    expect(input).not.toHaveClass("ds-input-small")
  })

  test("renders the medium variant", () => {
    renderComponent({ size: "medium" })
    const input = screen.getByRole("textbox")
    expect(input).toHaveClass("ds-input-medium")
    expect(input).not.toHaveClass("ds-input-small")
  })

  test("renders the small variant", () => {
    renderComponent({ size: "small" })
    const input = screen.getByRole("textbox")
    expect(input).not.toHaveClass("ds-input-medium")
    expect(input).toHaveClass("ds-input-small")
  })

  test("renders a label when provided and associates it with the text input", () => {
    const labelText = "Test Label"
    renderComponent({ label: labelText })

    const labelElement = screen.getByText(labelText) as HTMLLabelElement
    expect(labelElement).toBeInTheDocument()

    const textInput = screen.getByRole("textbox") as HTMLTextAreaElement
    expect(labelElement.htmlFor).toBe(textInput.id)
  })
})
