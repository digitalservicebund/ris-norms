import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisAmendingLawCard from "./RisAmendingLawCard.vue"

describe("RisAmendingLawCard", () => {
  test("should render overview of open print amending law", () => {
    render(RisAmendingLawCard, {
      props: {
        eli: "someEli",
        frbrName: "GazetteName",
        frbrDateVerkuendung: "2021-01-01",
        frbrNumber: "s456",
      },
    })
    expect(screen.getByText("GazetteName 2021 S. 456")).toBeInTheDocument()
  })

  test("should render overview of open digital amending law", () => {
    render(RisAmendingLawCard, {
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
