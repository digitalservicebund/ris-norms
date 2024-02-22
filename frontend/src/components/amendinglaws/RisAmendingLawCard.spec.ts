import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisProcedureCard from "./RisAmendingLawCard.vue"

describe("RisAmendingLawCard", () => {
  test("should render overview of open print amending law", () => {
    render(RisProcedureCard, {
      props: {
        eli: "someEli",
        printAnnouncementGazette: "GazetteName",
        publicationDate: "2021-01-01",
        printAnnouncementPage: "456",
      },
    })
    expect(screen.getByText("GAZETTENAME 2021 Nr. 456")).toBeInTheDocument()
  })

  test("should render overview of open digital amending law", () => {
    render(RisProcedureCard, {
      props: {
        eli: "someEli",
        digitalAnnouncementMedium: "DigitalGazette",
        publicationDate: "2019-01-01",
        digitalAnnouncementEdition: "123",
      },
    })
    expect(screen.getByText("DIGITALGAZETTE 2019 Nr. 123")).toBeInTheDocument()
  })
})
