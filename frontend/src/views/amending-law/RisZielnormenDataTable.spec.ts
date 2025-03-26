import { render, screen, fireEvent } from "@testing-library/vue"
import RisZielnormenDataTable from "./RisZielnormenDataTable.vue"
import userEvent from "@testing-library/user-event"
import { describe, it, expect } from "vitest"

const groupedZielnormMock = {
  key: "bgbl-1/2000/s1234",
  title: "Testgesetz",
  fna: "100-1",
  eli: "eli/bund/bgbl-1/2000/s1234",
  expressions: [
    {
      eli: "eli/bund/bgbl-1/2000/s1234/2024-05-01/1/deu/regelungstext-1",
      status: "neu",
      frbrDateVerkuendung: "2024-05-01",
    },
    {
      eli: "eli/bund/bgbl-1/2000/s1234/2024-06-01/1/deu/regelungstext-1",
      status: "neu",
      frbrDateVerkuendung: "2024-06-01",
    },
  ],
}

describe("risZielnormenDataTable", () => {
  it("renders law title and FNA", () => {
    render(RisZielnormenDataTable, {
      props: {
        groupedZielnorm: groupedZielnormMock,
      },
    })

    expect(screen.getByText("Testgesetz")).toBeInTheDocument()
    expect(screen.getByText("FNA")).toBeInTheDocument()
    expect(screen.getByText("100-1")).toBeInTheDocument()
    expect(screen.getByText("eli/bund/bgbl-1/2000/s1234")).toBeInTheDocument()
  })

  it("renders and expands to show 'Textkonsolidierung'", async () => {
    render(RisZielnormenDataTable, {
      props: { groupedZielnorm: groupedZielnormMock },
    })

    // Expand top-level row (Vereinsgesetz)
    const toggleButtons = screen.getAllByRole("button")
    await userEvent.click(toggleButtons[0])

    expect(screen.getByText("Textkonsolidierung")).toBeInTheDocument()
  })

  it.skip("expands the nested row and shows version entries", async () => {
    render(RisZielnormenDataTable, {
      props: {
        groupedZielnorm: groupedZielnormMock,
      },
    })

    const toggleButtons = screen.getAllByRole("button")

    // Click the top-level row to expand
    await fireEvent.click(toggleButtons[0])
    expect(screen.getByText("Textkonsolidierung")).toBeInTheDocument()

    // Click to expand Textkonsolidierung row
    await fireEvent.click(toggleButtons[1])

    expect(screen.getByText("01.05.2024")).toBeInTheDocument()
    expect(screen.getByText("01.06.2024")).toBeInTheDocument()
  })
})
