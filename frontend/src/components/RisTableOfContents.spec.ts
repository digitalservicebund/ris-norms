import { describe, expect, it, vi } from "vitest"
import { render, screen } from "@testing-library/vue"
import { userEvent } from "@testing-library/user-event"
import RisTableOfContents from "@/components/RisTableOfContents.vue"
import type { TocItem } from "@/types/toc"

const toc: TocItem[] = [
  {
    id: "hauptteil-n1_buch-n2",
    marker: "2. Buch",
    heading: "Beispiele für Strukturen",
    type: "book",
    hasEingebundeneStammform: false,
    children: [
      {
        id: "art-z1",
        marker: "Art 1",
        heading: "",
        type: "article",
        hasEingebundeneStammform: false,
        children: [],
      },
      {
        id: "art-z2",
        marker: "Art 2",
        heading: "",
        type: "article",
        hasEingebundeneStammform: false,
        children: [],
      },
    ],
  },
]

describe("risTableOfContents", () => {
  it("should render", () => {
    render(RisTableOfContents, {
      props: {
        toc: toc,
        isFetching: false,
        fetchError: null,
        selectedEId: null,
      },
    })

    expect(
      screen.getByRole("button", { name: "2. Buch Beispiele für Strukturen" }),
    ).toBeVisible()
  })

  it("should select and expand items", async () => {
    const user = userEvent.setup()

    const selectSpy = vi.fn()

    render(RisTableOfContents, {
      props: {
        toc: toc,
        isFetching: false,
        fetchError: null,
        onSelect: selectSpy,
        selectedEId: null,
      },
    })

    await user.click(
      screen.getByRole("button", { name: "2. Buch Beispiele für Strukturen" }),
    )

    expect(selectSpy).toHaveBeenLastCalledWith(
      expect.objectContaining({
        eId: "hauptteil-n1_buch-n2",
      }),
    )

    expect(screen.getByRole("button", { name: "Art 1" })).toBeVisible()
    expect(screen.getByRole("button", { name: "Art 2" })).toBeVisible()

    await user.click(screen.getByRole("button", { name: "Art 2" }))

    expect(selectSpy).toHaveBeenLastCalledWith(
      expect.objectContaining({
        eId: "art-z2",
      }),
    )
  })

  it("shows loading state", () => {
    render(RisTableOfContents, {
      props: {
        toc: toc,
        isFetching: true,
        fetchError: null,
        selectedEId: null,
      },
    })

    expect(screen.getByRole("status", { name: "Lädt..." })).toBeVisible()
  })

  it("shows error state", () => {
    render(RisTableOfContents, {
      props: {
        toc: toc,
        isFetching: false,
        fetchError: "error",
        selectedEId: null,
      },
    })

    expect(screen.getByRole("alert")).toHaveTextContent(
      "Ein unbekannter Fehler ist aufgetreten.",
    )
  })
})
