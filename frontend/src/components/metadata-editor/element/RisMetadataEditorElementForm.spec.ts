import { describe, expect, it, vi } from "vitest"
import RisMetadataEditorElementForm from "./RisMetadataEditorElementForm.vue"
import { render, screen } from "@testing-library/vue"
import type { ElementProprietary } from "@/types/proprietary"
import { userEvent } from "@testing-library/user-event"

const testData: ElementProprietary = {
  artDerNorm: "ÄN",
}

describe("risMetadataEditorElementForm", () => {
  it("should render", () => {
    render(RisMetadataEditorElementForm, {
      props: {
        modelValue: testData,
      },
    })

    expect(
      screen.getByRole("radio", { name: "SN - Stammnorm" }),
    ).not.toBeChecked()
    expect(
      screen.getByRole("radio", { name: "ÄN - Änderungsnorm" }),
    ).toBeChecked()
    expect(
      screen.getByRole("radio", { name: "ÜN - Übergangsnorm" }),
    ).not.toBeChecked()
  })

  describe("editing data", () => {
    it("should update Art der Norm", async () => {
      const user = userEvent.setup()
      const onModelUpdate = vi.fn()

      render(RisMetadataEditorElementForm, {
        props: {
          modelValue: testData,
          "onUpdate:modelValue": onModelUpdate,
        },
      })

      await user.click(screen.getByRole("radio", { name: "SN - Stammnorm" }))
      expect(onModelUpdate).toHaveBeenLastCalledWith(
        expect.objectContaining({
          artDerNorm: "SN",
        }),
      )
    })
  })
})
