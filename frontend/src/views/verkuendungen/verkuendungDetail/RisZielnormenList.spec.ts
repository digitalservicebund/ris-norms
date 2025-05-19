import { describe, expect, it } from "vitest"
import RisZielnormenList from "./RisZielnormenList.vue"
import { render, screen, within } from "@testing-library/vue"
import userEvent from "@testing-library/user-event"

describe("risZielnormenList", () => {
  it("renders a Zielnorm's info", () => {
    render(RisZielnormenList, {
      props: {
        items: [
          {
            title: "Example norm",
            eli: "eli/example",
            shortTitle: "abbrv",
            fna: "4711-1",
            expressions: [],
          },
        ],
        verkuendungEli: "eli/verkuendung",
      },
    })

    expect(screen.getByText(/abbrv/)).toBeInTheDocument()
    expect(screen.getByText(/4711-1/)).toBeInTheDocument()
    expect(screen.getByText(/eli\/example/)).toBeInTheDocument()
  })

  it("renders expressions of a Zielnorm", async () => {
    const user = userEvent.setup()

    render(RisZielnormenList, {
      props: {
        items: [
          {
            title: "Example norm",
            shortTitle: "abbrv",
            eli: "eli/example",
            fna: "4711-1",
            expressions: [
              {
                eli: "eli/bund/bgbl-1/2017/s1/2017-03-15/1/deu/regelungstext-1",
                fna: "4711-1",
                frbrDateVerkuendung: "2017-03-15",
                frbrName: "BGBl. I",
                frbrNumber: "s1",
                shortTitle: "Example norm",
                status: "new",
                title: "Long title of an example norm",
              },
            ],
          },
        ],
        verkuendungEli: "eli/verkuendung",
      },
    })

    await user.click(screen.getByRole("button", { name: /abbrv/ }))
    await user.click(screen.getByRole("button", { name: "Textkonsolidierung" }))
    const textko = screen.getByRole("region", { name: "Textkonsolidierung" })

    expect(
      within(textko).getByRole("cell", {
        name: "eli/bund/bgbl-1/2017/s1/2017-03-15/1/deu",
      }),
    ).toBeInTheDocument()

    expect(
      within(textko).getByRole("cell", {
        name: "15.03.2017",
      }),
    ).toBeInTheDocument()

    expect(
      within(textko).getByRole("cell", {
        name: "neu",
      }),
    ).toBeInTheDocument()
  })

  it("falls back to long title if short title is missing", () => {
    render(RisZielnormenList, {
      props: {
        items: [
          {
            title: "Fallback long title",
            eli: "eli/fallback",
            shortTitle: "",
            fna: "1234-5",
            expressions: [],
          },
        ],
        verkuendungEli: "eli/verkuendung",
      },
    })

    expect(screen.getByText(/Fallback long title/)).toBeInTheDocument()
    expect(screen.queryByText(/abbrv/)).not.toBeInTheDocument()
  })
})
