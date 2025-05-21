import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { render, screen } from "@testing-library/vue"
import { describe, expect, it } from "vitest"
import RisZielnormenPreviewList from "./RisZielnormenPreviewList.vue"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"

describe("risZielnormenPreviewList", () => {
  it("renders all entries with infos", () => {
    render(RisZielnormenPreviewList, {
      props: {
        items: [
          {
            normWorkEli: NormWorkEli.fromString("eli/bund/bgbl-1/2020/s1"),
            title: "Luftverkehrsteuergesetz",
            shortTitle: "LuftVStG",
            expressions: [
              {
                normExpressionEli: NormExpressionEli.fromString(
                  "eli/bund/bgbl-1/2010/s1885/2023-01-01/1/deu",
                ),
                isGegenstandslos: false,
                isCreated: true,
                createdBy: "diese Verkündung",
              },
            ],
          },
          {
            normWorkEli: NormWorkEli.fromString("eli/bund/bgbl-1/2023/s1"),
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
    render(RisZielnormenPreviewList, {
      props: {
        items: [
          {
            normWorkEli: NormWorkEli.fromString("eli/bund/bgbl-1/2020/s1"),
            title: "Luftverkehrsteuergesetz",
            shortTitle: "LuftVStG",
            expressions: [
              {
                normExpressionEli: NormExpressionEli.fromString(
                  "eli/bund/bgbl-1/2010/s1885/2023-01-01/1/deu",
                ),
                isGegenstandslos: false,
                isCreated: true,
                createdBy: "diese Verkündung",
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
