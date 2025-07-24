import userEvent from "@testing-library/user-event"
import { render, screen, within } from "@testing-library/vue"
import { beforeAll, describe, expect, it } from "vitest"
import { defineComponent } from "vue"
import type { Router } from "vue-router"
import { createRouter, createWebHashHistory } from "vue-router"
import RisZielnormenList from "./RisZielnormenList.vue"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

describe("risZielnormenList", () => {
  let global = {}
  let router: Router

  beforeAll(async () => {
    const component = defineComponent({ template: "<div></div>" })

    router = createRouter({
      history: createWebHashHistory(),
      routes: [
        { path: "/", name: "Home", component },
        { path: "/:pathMatch(.*)*", name: "CatchAll", component },
      ],
    })

    await router.push({ name: "Home" })
    await router.isReady()

    global = { plugins: [router] }
  })

  it("renders a Zielnorm's info", () => {
    render(RisZielnormenList, {
      global,
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
        verkuendungEli: NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
        ),
      },
    })

    expect(screen.getByText(/abbrv/)).toBeInTheDocument()
    expect(screen.getByText(/4711-1/)).toBeInTheDocument()
    expect(screen.getByText(/eli\/example/)).toBeInTheDocument()
  })

  it("renders expressions of a Zielnorm", async () => {
    const user = userEvent.setup()

    render(RisZielnormenList, {
      global,
      props: {
        items: [
          {
            title: "Example norm",
            shortTitle: "abbrv",
            eli: "eli/example",
            fna: "4711-1",
            expressions: [
              {
                eli: DokumentExpressionEli.fromString(
                  "eli/bund/bgbl-1/2017/s1/2017-03-15/1/deu/regelungstext-verkuendung-1",
                ),
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
        verkuendungEli: NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
        ),
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
      global,
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
        verkuendungEli: NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu",
        ),
      },
    })

    expect(screen.getByText(/Fallback long title/)).toBeInTheDocument()
    expect(screen.queryByText(/abbrv/)).not.toBeInTheDocument()
  })
})
