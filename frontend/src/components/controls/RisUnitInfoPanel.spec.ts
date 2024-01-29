import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisUnitInfoPanel from "./RisUnitInfoPanel.vue"

describe("RisInfoModal", () => {
  test("renders heading", () => {
    const heading = "Test Heading"

    render(RisUnitInfoPanel, {
      props: { heading },
    })

    expect(screen.getByText(heading)).toBeInTheDocument()
  })

  test("renders property infos", () => {
    const propertyInfos = [
      { label: "Label 1", value: "Value 1" },
      { label: "Label 2", value: "Value 2" },
    ]

    render(RisUnitInfoPanel, {
      props: { propertyInfos },
    })

    propertyInfos.forEach((info) => {
      expect(screen.getByText(info.label)).toBeInTheDocument()
      expect(screen.getByText(info.value)).toBeInTheDocument()
    })
  })
})
