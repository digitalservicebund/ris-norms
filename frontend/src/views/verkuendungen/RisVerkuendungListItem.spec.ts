import { render, screen } from "@testing-library/vue"
import { describe, expect, it } from "vitest"
import RisVerkuendungListItem from "./RisVerkuendungListItem.vue"

describe("risVerkuendungListItem", () => {
  it("should render overview of open print amending law", () => {
    render(RisVerkuendungListItem, {
      props: {
        eli: "someEli",
        frbrName: "GazetteName",
        frbrDateVerkuendung: "2021-01-01",
        frbrNumber: "s456",
      },
    })
    expect(screen.getByText("GazetteName 2021 S. 456")).toBeInTheDocument()
  })

  it("should render overview of open digital amending law", () => {
    render(RisVerkuendungListItem, {
      props: {
        eli: "someEli",
        frbrName: "DigitalGazette",
        frbrDateVerkuendung: "2019-01-01",
        frbrNumber: "123",
      },
    })
    expect(screen.getByText("DigitalGazette 2019 Nr. 123")).toBeInTheDocument()
  })
})
