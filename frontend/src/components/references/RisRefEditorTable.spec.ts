import { describe, expect, it } from "vitest"
import { getByRole, render, screen } from "@testing-library/vue"
import { nextTick } from "vue"
import RisRefEditorTable from "@/components/references/RisRefEditorTable.vue"
import { userEvent } from "@testing-library/user-event"

describe("RisRefEditorTable", () => {
  it("Should render a ref editor for every akn:ref of the xml snippet", async () => {
    render(RisRefEditorTable, {
      props: {
        xmlSnippet:
          "<akn:quotedText xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.6/\" eId='quot-1'>Render of <akn:ref eId='quot-1_ref-1'>a ref</akn:ref> and <akn:ref eId='quot-1_ref-2'>a second ref</akn:ref> and <akn:p eId='quot-1_p-1'>place for a third ref</akn:p></akn:quotedText>",
      },
    })

    await nextTick()

    const refEditor1 = screen.getByRole("region", { name: "a ref" })
    expect(refEditor1).toBeInTheDocument()
    const refEditor2 = screen.getByRole("region", { name: "a second ref" })
    expect(refEditor2).toBeInTheDocument()
  })

  it("Should delete a ref when the delete icon is clicked", async () => {
    const result = render(RisRefEditorTable, {
      props: {
        xmlSnippet:
          "<akn:quotedText xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.6/\" eId='quot-1'>Render of <akn:ref eId='quot-1_ref-1'>a ref</akn:ref> and <akn:ref eId='quot-1_ref-2'>a second ref</akn:ref> and <akn:p eId='quot-1_p-1'>place for a third ref</akn:p></akn:quotedText>",
      },
    })

    const refEditor2 = screen.getByRole("region", { name: "a second ref" })
    const deleteButton = getByRole(refEditor2, "button", { name: "Löschen" })
    await userEvent.click(deleteButton)

    await expect.poll(() => result.emitted("update:xmlSnippet")).toHaveLength(1)
    expect((result.emitted("update:xmlSnippet")[0] as [string])[0]).toContain(
      'Render of <akn:ref eId="quot-1_ref-1">a ref</akn:ref> and a second ref',
    )
  })

  it("Selects a ref when it's href input is clicked", async () => {
    const result = render(RisRefEditorTable, {
      props: {
        xmlSnippet:
          "<akn:quotedText xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.6/\" eId='quot-1'>Render of <akn:ref eId='quot-1_ref-1'>a ref</akn:ref> and <akn:ref eId='quot-1_ref-2'>a second ref</akn:ref> and <akn:p eId='quot-1_p-1'>place for a third ref</akn:p></akn:quotedText>",
      },
    })

    await nextTick()

    const refEditor2 = screen.getByRole("region", { name: "a second ref" })
    const hrefInput = getByRole(refEditor2, "textbox", {
      name: "ELI mit Zielstelle",
    })
    await userEvent.click(hrefInput)

    await expect
      .poll(() => result.emitted("update:selectedRef"))
      .toHaveLength(1)
    expect(result.emitted("update:selectedRef")[0]).toEqual(["quot-1_ref-2"])
  })

  it("Selects a ref when it's is navigated to using keyboard navigation", async () => {
    const result = render(RisRefEditorTable, {
      props: {
        xmlSnippet:
          "<akn:quotedText xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.6/\" eId='quot-1'>Render of <akn:ref eId='quot-1_ref-1'>a ref</akn:ref> and <akn:ref eId='quot-1_ref-2'>a second ref</akn:ref> and <akn:p eId='quot-1_p-1'>place for a third ref</akn:p></akn:quotedText>",
      },
    })

    await userEvent.click(screen.getByText("Typ"))
    await userEvent.tab()
    await userEvent.tab()
    await userEvent.tab()
    await userEvent.tab()

    await expect
      .poll(() => result.emitted("update:selectedRef"))
      .toHaveLength(2)
    expect(result.emitted("update:selectedRef")[0]).toEqual(["quot-1_ref-1"])
    expect(result.emitted("update:selectedRef")[1]).toEqual(["quot-1_ref-2"])
  })
})
