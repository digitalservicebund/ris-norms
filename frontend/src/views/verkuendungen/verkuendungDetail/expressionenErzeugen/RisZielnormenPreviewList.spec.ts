import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { render, screen } from "@testing-library/vue"
import { describe, expect, it } from "vitest"
import RisZielnormenPreviewList from "./RisZielnormenPreviewList.vue"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"
import userEvent from "@testing-library/user-event"

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
                isOrphan: false,
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
        name: /Luftverkehrsteuergesetz\s*\(LuftVStG\)/,
      }),
    ).toBeInTheDocument()
    expect(
      screen.getByRole("button", {
        name: /Berufsbildungsgesetzes\s*\(BBiG\)/,
      }),
    ).toBeInTheDocument()
  })

  it("clicking on entry reveals table and button for creating new expressions", async () => {
    const user = userEvent.setup()

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
                isOrphan: false,
              },
            ],
          },
        ],
      },
    })

    await user.click(
      screen.getByRole("button", {
        name: /Luftverkehrsteuergesetz\s*\(LuftVStG\)/,
      }),
    )

    expect(await screen.findByRole("table")).toBeInTheDocument()
    expect(
      await screen.findByRole("button", { name: "Expressionen erzeugen" }),
    ).toBeInTheDocument()
  })

  it("sets the loading state", async () => {
    const user = userEvent.setup()

    const { rerender } = render(RisZielnormenPreviewList, {
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
                isOrphan: false,
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
        loading: true,
      },
    })

    await user.click(
      screen.getByRole("button", {
        name: /Luftverkehrsteuergesetz\s*\(LuftVStG\)/,
      }),
    )

    const createBtn = await screen.findByRole("button", {
      name: "Expressionen erzeugen",
    })
    expect(createBtn).toBeDisabled()

    await rerender({ loading: false })
    expect(
      await screen.findByRole("button", { name: "Expressionen erzeugen" }),
    ).not.toBeDisabled()
  })
})
