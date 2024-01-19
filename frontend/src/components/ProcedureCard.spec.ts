import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import ProcedureCard from "./ProcedureCard.vue"

describe("YourComponent", () => {
  test("should render overview of open procedures", () => {
    render(ProcedureCard, {
      props: {
        eli: "someEli",
        printAnnouncementGazette: "GazetteName",
        printAnnouncementNumber: "123",
        printAnnouncementYear: "2021",
        printAnnouncementPage: "456",
        // publicationDate: "2021-01-01",
        publicationDate: new Date("2021-01-01"),
      },
    })
    expect(screen.getByText("GazetteName 2021 Nr. 123")).toBeInTheDocument()
    expect(screen.getByText("01.01.2021")).toBeInTheDocument()
    expect(screen.queryByText("2021.01.01")).not.toBeInTheDocument()
  })
})
