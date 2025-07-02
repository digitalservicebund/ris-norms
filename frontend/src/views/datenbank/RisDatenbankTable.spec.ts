import { render, screen, within } from "@testing-library/vue"
import RisDatenbankTable from "./RisDatenbankTable.vue"
import { describe, expect, it } from "vitest"
import userEvent from "@testing-library/user-event"

describe("risDatenbankTable", () => {
  it("renders the table with ELI and title columns and paginates", async () => {
    const items = [
      {
        eli: "eli/bund/bgbl-1/2021/s818",
        title:
          "Gesetz zur Einführung eines Lobbyregisters für die Interessenvertretung gegenüber dem Deutschen Bundestag und gegenüber der Bundesregierung",
      },
      {
        eli: "eli/bund/bgbl-1/2024/108",
        title:
          "Gesetz zur Stärkung von Wachstumschancen, Investitionen und Innovation sowie Steuervereinfachung und Steuerfairness",
      },
    ]

    render(RisDatenbankTable, {
      props: {
        items,
        total: 2,
        currentPage: 0,
        pageSize: 100,
      },
    })

    expect(screen.getByRole("table")).toBeInTheDocument()
    expect(screen.getAllByRole("row")).toHaveLength(3) // header + 2 data rows

    const rows = screen.getAllByRole("row")

    expect(within(rows[1]).getByText(items[0].eli)).toBeInTheDocument()
    expect(within(rows[1]).getByText(items[0].title)).toBeInTheDocument()
    expect(within(rows[2]).getByText(items[1].eli)).toBeInTheDocument()
    expect(within(rows[2]).getByText(items[1].title)).toBeInTheDocument()
  })

  it("shows the correct page report", () => {
    render(RisDatenbankTable, {
      props: {
        items: [],
        total: 200,
        currentPage: 1,
        pageSize: 100,
      },
    })
    expect(screen.getByText("Seite 2 von 2")).toBeInTheDocument()
  })

  it("shows 'Weiter' button and emits event on click", async () => {
    const user = userEvent.setup()
    const items = [
      { eli: "eli/bund/bgbl-1/2021/s818", title: "Test" },
      { eli: "eli/bund/bgbl-1/2024/108", title: "Test2" },
    ]
    const { emitted } = render(RisDatenbankTable, {
      props: {
        items,
        total: 4,
        currentPage: 0,
        pageSize: 2,
      },
    })
    const weiter = screen.getByText("Weiter")
    expect(weiter).toBeInTheDocument()
    await user.click(weiter)
    expect(emitted().page[0]).toEqual([1])
  })

  it("shows 'Zurück' button and emits event on click", async () => {
    const user = userEvent.setup()
    const items = [
      { eli: "eli/bund/bgbl-1/2021/s818", title: "Test" },
      { eli: "eli/bund/bgbl-1/2024/108", title: "Test2" },
    ]
    const { emitted } = render(RisDatenbankTable, {
      props: {
        items,
        total: 4,
        currentPage: 1,
        pageSize: 2,
      },
    })
    const zurueck = screen.getByText("Zurück")
    expect(zurueck).toBeInTheDocument()
    await user.click(zurueck)
    expect(emitted().page[0]).toEqual([0])
  })

  it("does not show 'Zurück' on first page or 'Weiter' on last page", () => {
    render(RisDatenbankTable, {
      props: {
        items: [],
        total: 2,
        currentPage: 0,
        pageSize: 2,
      },
    })
    expect(screen.queryByText("Zurück")).not.toBeVisible()
    expect(screen.queryByText("Weiter")).not.toBeVisible()
  })
})
