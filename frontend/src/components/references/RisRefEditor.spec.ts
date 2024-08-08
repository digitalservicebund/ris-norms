import { describe, expect, it } from "vitest"
import { render, screen, waitFor } from "@testing-library/vue"
import { userEvent } from "@testing-library/user-event"
import RisRefEditor from "@/components/references/RisRefEditor.vue"
import { flushPromises } from "@vue/test-utils"

describe("RisRefEditor", () => {
  it("Should render a a select for the refersTo and an input for the href", async () => {
    render(RisRefEditor, {
      props: {
        xmlSnippet: `<akn:ref xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-4_abs-3.xml" refersTo="zitierung">§4 Abs. 3 StVO</akn:ref>`,
      },
    })

    const refersToSelect = screen.getByRole("combobox", { name: "Typ" })
    expect(refersToSelect).toBeInTheDocument()
    expect(refersToSelect).toHaveTextContent("Zitierung")
    expect(refersToSelect).toHaveValue("zitierung")

    const hrefInput = screen.getByRole("textbox", {
      name: "ELI mit Zielstelle",
    })
    expect(hrefInput).toBeInTheDocument()
    expect(hrefInput).toHaveValue(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-4_abs-3.xml",
    )
  })

  it("Can select a new refersTo value", async () => {
    const user = userEvent.setup()

    const result = render(RisRefEditor, {
      props: {
        xmlSnippet: `<akn:ref xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-4_abs-3.xml">§4 Abs. 3 StVO</akn:ref>`,
      },
    })

    await user.selectOptions(
      screen.getByRole("combobox", { name: "Typ" }),
      screen.getByRole("option", { name: "Zitierung" }),
    )

    await expect.poll(() => result.emitted("update:xmlSnippet")).toHaveLength(1)
    expect(result.emitted("update:xmlSnippet")[0]).toEqual([
      '<akn:ref xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-4_abs-3.xml" refersTo="zitierung">§4 Abs. 3 StVO</akn:ref>',
    ])
  })

  it("Can change href", async () => {
    const user = userEvent.setup()

    const result = render(RisRefEditor, {
      props: {
        xmlSnippet: `<akn:ref xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-4_abs-3.xml" refersTo="zitierung">§4 Abs. 3 StVO</akn:ref>`,
      },
    })

    const hrefInput = screen.getByRole("textbox", {
      name: "ELI mit Zielstelle",
    })

    await user.clear(hrefInput)
    await user.type(
      hrefInput,
      "eli/bund/bgbl-1/2001/s1/2022-12-19/1/deu/regelungstext-1/para-4_abs-3.xml",
    )

    await expect.poll(() => result.emitted("update:xmlSnippet")).toHaveLength(1)
    expect(result.emitted("update:xmlSnippet")[0]).toEqual([
      '<akn:ref xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1_ref-1" href="eli/bund/bgbl-1/2001/s1/2022-12-19/1/deu/regelungstext-1/para-4_abs-3.xml" refersTo="zitierung">§4 Abs. 3 StVO</akn:ref>',
    ])
  })

  it("Can send delete event", async () => {
    const result = render(RisRefEditor, {
      props: {
        xmlSnippet: `<akn:ref xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-4_abs-3.xml" refersTo="zitierung">§4 Abs. 3 StVO</akn:ref>`,
      },
    })

    await userEvent.click(screen.getByRole("button", { name: "Löschen" }))

    expect(result.emitted("delete")).toHaveLength(1)
  })

  it("Can send the focus previous event", async () => {
    const user = userEvent.setup()
    const { emitted } = render(RisRefEditor, {
      props: {
        xmlSnippet: `<akn:ref xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-4_abs-3.xml" refersTo="zitierung">§4 Abs. 3 StVO</akn:ref>`,
      },
    })

    await user.type(screen.getByRole("textbox"), "{ArrowUp}")

    expect(emitted("selectPrevious")).toBeTruthy()
  })

  it("Can send the focus next event", async () => {
    const user = userEvent.setup()
    const { emitted } = render(RisRefEditor, {
      props: {
        xmlSnippet: `<akn:ref xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-4_abs-3.xml" refersTo="zitierung">§4 Abs. 3 StVO</akn:ref>`,
      },
    })

    await user.type(screen.getByRole("textbox"), "{ArrowDown}")

    expect(emitted("selectNext")).toBeTruthy()
  })

  it("Focuses the input", async () => {
    const { rerender } = render(RisRefEditor, {
      props: {
        xmlSnippet: `<akn:ref xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-4_abs-3.xml" refersTo="zitierung">§4 Abs. 3 StVO</akn:ref>`,
        grabFocus: false,
      },
    })

    await flushPromises()
    expect(screen.getByRole("textbox")).not.toHaveFocus()

    await rerender({ grabFocus: true })
    await waitFor(() => expect(screen.getByRole("textbox")).toHaveFocus())
  })
})
