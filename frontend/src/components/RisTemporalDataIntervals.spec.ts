import { describe, it, expect } from "vitest"
import { render, screen } from "@testing-library/vue"
import RisTemporalDataIntervals from "./RisTemporalDataIntervals.vue"
import dayjs from "dayjs"

describe("YourComponent", () => {
  it("renders the correct number of date inputs and checks their values", async () => {
    const dates = ["2023-01-01", "2023-02-01"]
    render(RisTemporalDataIntervals, {
      props: { dates },
    })

    const inputs = screen.getAllByTestId("date-input-field")
    expect(inputs.length).toBe(dates.length)

    inputs.forEach((input, index) => {
      const inputElement = input as HTMLInputElement
      const expectedValue = dayjs(dates[index]).format("DD.MM.YYYY")
      expect(inputElement.value).toBe(expectedValue)
    })
  })

  it("should contain a sort button", () => {
    render(RisTemporalDataIntervals)
    const sortButton = screen.getByText("Nach Datum sortieren")
    expect(sortButton).toBeInTheDocument()
  })
})
