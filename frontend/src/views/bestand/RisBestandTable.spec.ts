import { render, screen, within } from "@testing-library/vue"
import RisBestandTable from "./RisBestandTable.vue"
import { describe, expect, it } from "vitest"

describe("risBestandTable", () => {
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

    render(RisBestandTable, {
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
    render(RisBestandTable, {
      props: {
        items: [],
        total: 200,
        currentPage: 1,
        pageSize: 100,
      },
    })
    expect(screen.getByText("Seite 2 von 2")).toBeInTheDocument()
  })
})
