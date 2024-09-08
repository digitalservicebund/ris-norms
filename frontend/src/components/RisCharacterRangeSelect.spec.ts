import { render, screen } from "@testing-library/vue"
import { describe, expect, it } from "vitest"
import RisCharacterRangeSelect from "@/components/RisCharacterRangeSelect.vue"
import { userEvent } from "@testing-library/user-event"

describe("RisCharacterRangeSelect", () => {
  it("renders the norm html", async () => {
    render(RisCharacterRangeSelect, {
      props: { render: "<span>TEST</span>", xml: "<akn:span>Test</akn:span>" },
    })

    expect(screen.getByText("TEST")).exist
  })

  it("supports selecting text", async () => {
    const user = userEvent.setup()
    const result = render(RisCharacterRangeSelect, {
      props: {
        render: "<span data-eId='span-1'>Test</span>",
        xml: `<akn:akonaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" ><akn:span eId="span-1">Test</akn:span></akn:akonaNtoso>`,
      },
    })

    await user.pointer([
      {
        keys: "[MouseLeft>]",
        target: screen.getByText("Test"),
        offset: 1,
      },
      {
        offset: 3,
      },
      { keys: "[/MouseLeft]" },
    ])

    expect(result.emitted("update:modelValue")).toHaveLength(1)
    expect(result.emitted("update:modelValue")[0]).toContain("span-1/1-3.xml")
  })

  // we can't test the highlight stuff as it is not supported by jsdom
})
