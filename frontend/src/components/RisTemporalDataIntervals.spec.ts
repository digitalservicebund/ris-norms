import { describe, it, expect } from "vitest"
import { render, screen } from "@testing-library/vue"
import RisTemporalDataIntervals from "./RisTemporalDataIntervals.vue"
import dayjs from "dayjs"

describe("YourComponent", () => {
  it("renders the correct number of date inputs and checks their values", async () => {
    const dates = [
      { date: "2023-01-01", eid: "event-1" },
      { date: "2023-02-01", eid: "event-2" },
    ]
    render(RisTemporalDataIntervals, {
      props: { dates },
    })

    const inputs = screen.getAllByTestId("date-input-field")
    expect(inputs.length).toBe(dates.length)

    inputs.forEach((input, index) => {
      const inputElement = input as HTMLInputElement
      const expectedValue = dayjs(dates[index].date).format("DD.MM.YYYY")
      expect(inputElement.value).toBe(expectedValue)
    })
  })

  it("should contain a sort button", () => {
    render(RisTemporalDataIntervals)
    const sortButton = screen.getByText("Nach Datum sortieren")
    expect(sortButton).toBeInTheDocument()
  })
})
