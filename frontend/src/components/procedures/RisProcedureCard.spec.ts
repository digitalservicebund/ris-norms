import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisProcedureCard from "./RisProcedureCard.vue"

describe("YourComponent", () => {
  test("should render overview of open procedures", () => {
    render(RisProcedureCard, {
      props: {
        eli: "someEli",
        printAnnouncementGazette: "GazetteName",
        printAnnouncementYear: 2021,
        printAnnouncementPage: 456,
      },
    })
    expect(screen.getByText("GAZETTENAME 2021 Nr. 456")).toBeInTheDocument()
  })
})
