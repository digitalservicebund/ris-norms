import { describe, it, expect, vi } from "vitest"
import { render, screen } from "@testing-library/vue"
import RisTemporalDataIntervals from "./RisTemporalDataIntervals.vue"
import dayjs from "dayjs"
import { userEvent } from "@testing-library/user-event"

describe("RisTemporalDateIntervals", () => {
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

  it("should remove the corresponding date entry when the delete button is clicked", async () => {
    const user = userEvent.setup()

    const dates = [
      { date: "2023-01-01", eid: "event-1" },
      { date: "2023-02-01", eid: "event-2" },
    ]

    render(RisTemporalDataIntervals, {
      props: { dates },
    })

    expect(screen.getAllByTestId("date-input-field").length).toBe(2)
    await user.click(screen.getByTestId("delete-button-0"))
    expect(screen.getAllByTestId("date-input-field").length).toBe(1)
  })

  it("disables the delete button when there is only one date input", async () => {
    const dates = [{ date: "2023-01-01", eid: "event-1" }]
    render(RisTemporalDataIntervals, {
      props: { dates },
    })

    const deleteButton = screen.getByTestId("delete-button-0")
    expect(deleteButton).toBeDisabled()
  })

  it("emits an update when the list of dates is changed, doesn't mutate the model", async () => {
    const user = userEvent.setup()
    const onUpdate = vi.fn()

    const dates = [
      { date: "2023-01-01", eid: "event-1" },
      { date: "2023-02-01", eid: "event-2" },
    ]

    render(RisTemporalDataIntervals, {
      props: { dates, "onUpdate:dates": onUpdate },
    })

    await user.click(screen.getByTestId("delete-button-0"))

    expect(dates).toStrictEqual([
      { date: "2023-01-01", eid: "event-1" },
      { date: "2023-02-01", eid: "event-2" },
    ])

    expect(onUpdate).toHaveBeenCalledWith([
      { date: "2023-02-01", eid: "event-2" },
    ])
  })
})
