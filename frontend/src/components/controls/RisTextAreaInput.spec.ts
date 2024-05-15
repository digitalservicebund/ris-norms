import { userEvent } from "@testing-library/user-event"
import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import TextAreaInput from "@/components/controls/RisTextAreaInput.vue"

type TextAreaInputProps = InstanceType<typeof TextAreaInput>["$props"]

function renderComponent(
  props: Partial<TextAreaInputProps>,
  attrs?: Record<string, unknown>,
) {
  let modelValue = ""

  const defaultProps: TextAreaInputProps = {
    id: "textarea",
    modelValue: modelValue,
    "onUpdate:modelValue": (value) => (modelValue = value),
    ...props,
  }

  return render(TextAreaInput, { props: defaultProps, attrs })
}

describe("TextAreaInput", () => {
  test("shows an textarea element", () => {
    renderComponent({})
    const input: HTMLTextAreaElement | null = screen.queryByRole("textbox")

    expect(input).toBeInTheDocument()
    expect(input?.tagName).toBe("TEXTAREA")
  })

  test("sets the ID of the textarea", () => {
    renderComponent({ id: "test-id" })
    const input: HTMLTextAreaElement | null = screen.queryByRole("textbox")
    expect(input).toHaveAttribute("id", "test-id")
  })

  test("shows textarea with a placeholder", () => {
    renderComponent({ placeholder: "Test Placeholder" })
    const textarea = screen.queryByPlaceholderText("Test Placeholder")
    expect(textarea).toBeInTheDocument()
  })

  test("allows to type text inside textarea", async () => {
    const user = userEvent.setup()
    renderComponent({ modelValue: "one" })
    const textarea: HTMLTextAreaElement = screen.getByRole("textbox")

    expect(textarea).toHaveValue("one")

    await user.type(textarea, " two")
    expect(textarea).toHaveValue("one two")
  })

  test("displays the model value", () => {
    renderComponent({ modelValue: "one" })
    const textarea: HTMLTextAreaElement = screen.getByRole("textbox")

    expect(textarea).toHaveValue("one")
  })

  test("updates the model value", async () => {
    const user = userEvent.setup()
    let testModel = "one"
    renderComponent({
      modelValue: testModel,
      "onUpdate:modelValue": (value) => (testModel = value),
    })
    const textarea: HTMLTextAreaElement = screen.getByRole("textbox")

    await user.type(textarea, " two")
    expect(testModel).toBe("one two")
  })

  test("sets the textarea to readonly", () => {
    renderComponent({ readOnly: true })
    const textarea: HTMLTextAreaElement = screen.getByRole("textbox")
    expect(textarea).toHaveAttribute("readonly")
  })

  test("sets the tabindex to -1 when readonly", () => {
    renderComponent({ readOnly: true })
    const textarea: HTMLTextAreaElement = screen.getByRole("textbox")
    expect(textarea).toHaveAttribute("tabindex", "-1")
  })

  test("doesn't set the textarea to readonly", () => {
    renderComponent({ readOnly: false })
    const textarea: HTMLTextAreaElement = screen.getByRole("textbox")
    expect(textarea).not.toHaveAttribute("readonly")
  })

  test("leaves the tabindex alone when not readonly", () => {
    renderComponent({ readOnly: false })
    const textarea: HTMLTextAreaElement = screen.getByRole("textbox")
    expect(textarea).not.toHaveAttribute("tabindex")
  })

  test("sets the tabindex to the given value", () => {
    renderComponent({}, { tabindex: 815 })
    const textarea: HTMLTextAreaElement = screen.getByRole("textbox")
    expect(textarea).toHaveAttribute("tabindex", "815")
  })

  test("renders the number of rows", () => {
    renderComponent({ rows: 5 })
    const textarea: HTMLTextAreaElement = screen.getByRole("textbox")
    expect(textarea).toHaveAttribute("rows", "5")
  })

  test("renders a label when provided and associates it with the textarea", () => {
    const labelText = "Test Label"
    renderComponent({ label: labelText, id: "test-id" })

    const labelElement = screen.getByText(labelText) as HTMLLabelElement
    expect(labelElement).toBeInTheDocument()

    const textarea = screen.getByRole("textbox") as HTMLTextAreaElement
    expect(labelElement.htmlFor).toBe(textarea.id)
  })
})
