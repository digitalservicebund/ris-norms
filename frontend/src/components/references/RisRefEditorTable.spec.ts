import { describe, expect, it } from "vitest"
import { render, screen } from "@testing-library/vue"
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

    const refEditor1 = screen.getByTestId("ris-ref-editor-quot-1_ref-1")
    expect(refEditor1).toBeInTheDocument()
    const refEditor2 = screen.getByTestId("ris-ref-editor-quot-1_ref-2")
    expect(refEditor2).toBeInTheDocument()
  })

  it("Should delete a ref when the delete icon is clicked", async () => {
    const result = render(RisRefEditorTable, {
      props: {
        xmlSnippet:
          "<akn:quotedText xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.6/\" eId='quot-1'>Render of <akn:ref eId='quot-1_ref-1'>a ref</akn:ref> and <akn:ref eId='quot-1_ref-2'>a second ref</akn:ref> and <akn:p eId='quot-1_p-1'>place for a third ref</akn:p></akn:quotedText>",
      },
    })

    await nextTick()

    // click the delete icon of the 2nd ref
    await userEvent.click(screen.getAllByLabelText("Löschen")[1])

    const updateEvents = result.emitted("update:xmlSnippet")
    expect(updateEvents).toHaveLength(1)
    expect((updateEvents[0] as [string])[0]).toContain(
      'Render of <akn:ref eId="quot-1_ref-1">a ref</akn:ref> and a second ref',
    )
  })
})
