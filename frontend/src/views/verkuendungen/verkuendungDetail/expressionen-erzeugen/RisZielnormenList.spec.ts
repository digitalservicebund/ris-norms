import { describe, expect, it } from "vitest"
import { render, screen } from "@testing-library/vue"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import RisZielnormenList from "@/views/verkuendungen/verkuendungDetail/expressionen-erzeugen/RisZielnormenList.vue"

describe("risZielnormenList", () => {
  it("renders all entries with infos", () => {
    render(RisZielnormenList, {
      props: {
        items: [
          {
            title: "Luftverkehrsteuergesetz",
            shortTitle: "LuftVStG",
            expressions: [
              {
                normExpressionEli: NormExpressionEli.fromString(
                  "eli/bund/bgbl-1/2010/s1885/2023-01-01/1/deu",
                ),
                isGegenstandslos: false,
                isCreated: true,
                erzeugtDurch: "diese Verkündung",
              },
            ],
          },
          {
            title: "Berufsbildungsgesetzes",
            shortTitle: "BBiG",
            expressions: [],
          },
        ],
      },
    })

    expect(
      screen.getByRole("button", {
        name: "Luftverkehrsteuergesetz (LuftVStG)",
      }),
    ).toBeInTheDocument()
    expect(
      screen.getByRole("button", {
        name: "Berufsbildungsgesetzes (BBiG)",
      }),
    ).toBeInTheDocument()
  })

  it("clicking on entry reveals table and button for creating new expressions", async () => {
    render(RisZielnormenList, {
      props: {
        items: [
          {
            title: "Luftverkehrsteuergesetz",
            shortTitle: "LuftVStG",
            expressions: [
              {
                normExpressionEli: NormExpressionEli.fromString(
                  "eli/bund/bgbl-1/2010/s1885/2023-01-01/1/deu",
                ),
                isGegenstandslos: false,
                isCreated: true,
                erzeugtDurch: "diese Verkündung",
              },
            ],
          },
        ],
      },
    })

    await screen
      .getByRole("button", {
        name: "Luftverkehrsteuergesetz (LuftVStG)",
      })
      .click()

    expect(screen.getByRole("table")).toBeInTheDocument()
    expect(
      screen.getByRole("button", { name: "Expressionen erzeugen" }),
    ).toBeInTheDocument()
  })
})
