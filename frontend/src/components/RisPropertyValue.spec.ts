import { render, screen } from "@testing-library/vue"
import { describe, expect, it } from "vitest"
import RisPropertyValue from "./RisPropertyValue.vue"

describe("risPropertyValue", () => {
  it("shows the property name", () => {
    render(RisPropertyValue, { props: { property: "Foo" } })
    expect(screen.getByText("Foo")).toBeInTheDocument()
  })

  it("shows the property value", () => {
    render(RisPropertyValue, { props: { property: "Foo", value: "Bar" } })
    expect(screen.getByText("Bar")).toBeInTheDocument()
  })

  it("shows the default value for an empty value", () => {
    render(RisPropertyValue, { props: { property: "Foo" } })
    expect(screen.getByText("-")).toBeInTheDocument()
  })
})
