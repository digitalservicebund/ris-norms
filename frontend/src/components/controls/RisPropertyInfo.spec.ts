import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisPropertyInfo from "./RisPropertyInfo.vue"

describe("RisInfoModal", () => {
  test("renders label and value correctly", async () => {
    const label = "Test Label"
    const value = "Test Value"

    render(RisPropertyInfo, {
      props: { label, value },
    })

    const labelElement = screen.getByText(label)
    const valueElement = await screen.getByText(value)

    expect(labelElement).toBeInTheDocument()
    expect(valueElement).toBeInTheDocument()

    expect(
      labelElement.compareDocumentPosition(valueElement) &
        Node.DOCUMENT_POSITION_FOLLOWING,
    ).toBeTruthy()
  })

  test("displays a dash when property value is missing", async () => {
    const propertyInfos = [{ label: "Missing Value", value: undefined }]

    render(RisPropertyInfo, {
      props: { propertyInfos },
    })

    const value = await screen.findByText("-")
    expect(value).toBeInTheDocument()
  })
})
