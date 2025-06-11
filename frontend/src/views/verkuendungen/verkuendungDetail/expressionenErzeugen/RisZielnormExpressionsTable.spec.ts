import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { render, screen, within } from "@testing-library/vue"
import { describe, expect, it } from "vitest"
import RisZielnormExpressionsTable from "./RisZielnormExpressionsTable.vue"

describe("risZielnormExpressionsTable", () => {
  it("renders table with infos", () => {
    render(RisZielnormExpressionsTable, {
      props: {
        items: [
          {
            normExpressionEli: NormExpressionEli.fromString(
              "eli/bund/bgbl-1/2010/s1885/2023-01-01/1/deu",
            ),
            isGegenstandslos: false,
            isCreated: true,
            createdBy: "diese Verkündung",
            isOrphan: false,
          },
          {
            normExpressionEli: NormExpressionEli.fromString(
              "eli/bund/bgbl-1/2010/s1885/2024-01-01/1/deu",
            ),
            isGegenstandslos: true,
            isCreated: true,
            createdBy: "andere Verkündung",
            isOrphan: false,
          },
          {
            normExpressionEli: NormExpressionEli.fromString(
              "eli/bund/bgbl-1/2010/s1885/2024-01-01/2/deu",
            ),
            isGegenstandslos: false,
            isCreated: false,
            createdBy: "System",
            isOrphan: false,
          },
          {
            normExpressionEli: NormExpressionEli.fromString(
              "eli/bund/bgbl-1/2010/s1885/2024-05-01/1/deu",
            ),
            isGegenstandslos: false,
            isCreated: false,
            createdBy: "diese Verkündung",
            isOrphan: false,
          },
          {
            normExpressionEli: NormExpressionEli.fromString(
              "eli/bund/bgbl-1/2010/s1885/2025-01-01/1/deu",
            ),
            isGegenstandslos: false,
            isCreated: false,
            createdBy: "System",
            isOrphan: true,
          },
        ],
      },
    })

    expect(screen.getByRole("table")).toBeInTheDocument()
    expect(screen.getAllByRole("row")).length(6) // 5 rows + header

    const row1 = screen.getAllByRole("row")[1]
    expect(within(row1).getAllByRole("cell")[0]).toHaveTextContent(
      "eli/bund/bgbl-1/2010/s1885/2023-01-01/1/deu",
    )
    expect(within(row1).getAllByRole("cell")[1]).toHaveTextContent(
      "01.01.2023 - 31.12.2023",
    )
    expect(within(row1).getAllByRole("cell")[2]).toHaveTextContent(
      "diese Verkündung",
    )
    expect(within(row1).getAllByRole("cell")[3]).toHaveTextContent(
      "Expression erzeugt",
    )

    const row2 = screen.getAllByRole("row")[2]
    expect(within(row2).getAllByRole("cell")[0]).toHaveTextContent(
      "eli/bund/bgbl-1/2010/s1885/2024-01-01/1/deu",
    )
    expect(within(row2).getAllByRole("cell")[1]).toHaveTextContent(
      "gegenstandslos",
    )
    expect(within(row2).getAllByRole("cell")[2]).toHaveTextContent(
      "andere Verkündung",
    )
    expect(within(row2).getAllByRole("cell")[3]).toHaveTextContent(
      "Gegenstandslos",
    )

    const row3 = screen.getAllByRole("row")[3]
    expect(within(row3).getAllByRole("cell")[0]).toHaveTextContent(
      "eli/bund/bgbl-1/2010/s1885/2024-01-01/2/deu",
    )
    expect(within(row3).getAllByRole("cell")[1]).toHaveTextContent(
      "01.01.2024 - 30.04.2024",
    )
    expect(within(row3).getAllByRole("cell")[2]).toHaveTextContent("System")
    expect(within(row3).getAllByRole("cell")[3]).toHaveTextContent(
      "Noch nicht erzeugt",
    )

    const row4 = screen.getAllByRole("row")[4]
    expect(within(row4).getAllByRole("cell")[0]).toHaveTextContent(
      "eli/bund/bgbl-1/2010/s1885/2024-05-01/1/deu",
    )
    expect(within(row4).getAllByRole("cell")[1]).toHaveTextContent("01.05.2024")
    expect(within(row4).getAllByRole("cell")[2]).toHaveTextContent(
      "diese Verkündung",
    )
    expect(within(row4).getAllByRole("cell")[3]).toHaveTextContent(
      "Noch nicht erzeugt",
    )

    const row5 = screen.getAllByRole("row")[5]
    expect(within(row5).getAllByRole("cell")[0]).toHaveTextContent(
      "eli/bund/bgbl-1/2010/s1885/2025-01-01/1/deu",
    )
    expect(within(row5).getAllByRole("cell")[1]).toHaveTextContent("01.01.2025")
    expect(within(row5).getAllByRole("cell")[2]).toHaveTextContent("System")
    expect(within(row5).getAllByRole("cell")[3]).toHaveTextContent(
      "Wird gelöscht",
    )
  })
})
