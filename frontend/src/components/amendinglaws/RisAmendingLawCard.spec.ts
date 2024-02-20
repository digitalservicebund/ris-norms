import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisProcedureCard from "./RisAmendingLawCard.vue"

describe("YourComponent", () => {
  test("should render overview of open procedures", () => {
    render(RisProcedureCard, {
      props: {
        eli: "someEli",
        printAnnouncementGazette: "GazetteName",
        publicationDate: "2021-01-01",
        printAnnouncementPage: 456,
      },
    })
    expect(screen.getByText("GAZETTENAME 2021 Nr. 456")).toBeInTheDocument()
  })
})
