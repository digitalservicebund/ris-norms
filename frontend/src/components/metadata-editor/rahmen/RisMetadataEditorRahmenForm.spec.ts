import { describe, expect, it, vi } from "vitest"
import RisMetadataEditorRahmenForm from "./RisMetadataEditorRahmenForm.vue"
import { render, screen } from "@testing-library/vue"
import type { RahmenProprietary } from "@/types/proprietary"
import { userEvent } from "@testing-library/user-event"

const testData: RahmenProprietary = {
  fna: "210-5",
  art: "regelungstext",
  typ: "gesetz",
  subtyp: "Rechtsverordnung",
  bezeichnungInVorlage: "Testbezeichnung nach meiner Vorlage",
  artDerNorm: "SN,ÜN",
  staat: "BEO - Berlin (Ost)",
  beschliessendesOrgan: "BMinJ - Bundesministerium der Justiz",
  qualifizierteMehrheit: true,
  ressort: "BMVg - Bundesministerium der Verteidigung",
  organisationsEinheit: "Einheit 1",
}

describe("risMetadataEditorRahmenForm", () => {
  it("should render", () => {
    render(RisMetadataEditorRahmenForm, {
      props: {
        modelValue: testData,
      },
    })

    expect(
      screen.getByRole("textbox", { name: "Sachgebiet" }),
    ).toHaveDisplayValue("210-5")

    expect(
      screen.getByRole("checkbox", { name: "SN - Stammnorm" }),
    ).toBeChecked()
    expect(
      screen.getByRole("checkbox", { name: "ÄN - Änderungsnorm" }),
    ).not.toBeChecked()
    expect(
      screen.getByRole("checkbox", { name: "ÜN - Übergangsnorm" }),
    ).toBeChecked()

    expect(
      screen.getByRole("textbox", { name: "Bezeichnung gemäß Vorlage" }),
    ).toHaveDisplayValue("Testbezeichnung nach meiner Vorlage")

    expect(
      screen.getByRole("combobox", {
        name: "Staat",
      }),
    ).toHaveTextContent("BEO - Berlin (Ost)")

    expect(
      screen.getByRole("combobox", { name: "beschließendes Organ" }),
    ).toHaveTextContent("BMinJ - Bundesministerium der Justiz")

    expect(
      screen.getByRole("checkbox", { name: "Beschlussf. qual. Mehrheit" }),
    ).toBeChecked()

    expect(screen.getByRole("combobox", { name: "Ressort" })).toHaveTextContent(
      "BMVg - Bundesministerium der Verteidigung",
    )

    expect(
      screen.getByRole("textbox", { name: "Organisationseinheit" }),
    ).toHaveDisplayValue("Einheit 1")
  })

  describe("editing data", () => {
    it("should update Sachgebiet", async () => {
      const user = userEvent.setup()
      const onModelUpdate = vi.fn()

      render(RisMetadataEditorRahmenForm, {
        props: {
          modelValue: testData,
          "onUpdate:modelValue": onModelUpdate,
        },
      })

      await user.clear(screen.getByRole("textbox", { name: "Sachgebiet" }))
      await user.type(
        screen.getByRole("textbox", { name: "Sachgebiet" }),
        "510-2",
      )
      expect(onModelUpdate).toHaveBeenLastCalledWith(
        expect.objectContaining({
          fna: "510-2",
        }),
      )
    })

    it("should update Art der Norm", async () => {
      const user = userEvent.setup()
      const onModelUpdate = vi.fn()

      render(RisMetadataEditorRahmenForm, {
        props: {
          modelValue: testData,
          "onUpdate:modelValue": onModelUpdate,
        },
      })

      await user.click(screen.getByRole("checkbox", { name: "SN - Stammnorm" }))
      expect(onModelUpdate).toHaveBeenLastCalledWith(
        expect.objectContaining({
          artDerNorm: "ÜN",
        }),
      )

      // It does not combine the inputs as we would need to update the data send to the component for the last change
      // to also be reflected (the way v-model works)
      await user.click(
        screen.getByRole("checkbox", { name: "ÄN - Änderungsnorm" }),
      )
      expect(onModelUpdate).toHaveBeenLastCalledWith(
        expect.objectContaining({
          artDerNorm: "SN,ÜN,ÄN",
        }),
      )

      await user.click(
        screen.getByRole("checkbox", { name: "ÜN - Übergangsnorm" }),
      )
      expect(onModelUpdate).toHaveBeenLastCalledWith(
        expect.objectContaining({
          artDerNorm: "SN",
        }),
      )
    })

    it("should update Bezeichnung gemäß Vorlage", async () => {
      const user = userEvent.setup()
      const onModelUpdate = vi.fn()

      render(RisMetadataEditorRahmenForm, {
        props: {
          modelValue: testData,
          "onUpdate:modelValue": onModelUpdate,
        },
      })

      await user.type(
        screen.getByRole("textbox", { name: "Bezeichnung gemäß Vorlage" }),
        " und noch mehr text",
      )
      expect(onModelUpdate).toHaveBeenLastCalledWith(
        expect.objectContaining({
          bezeichnungInVorlage:
            "Testbezeichnung nach meiner Vorlage und noch mehr text",
        }),
      )
    })

    it("should update Staat", async () => {
      const user = userEvent.setup()
      const onModelUpdate = vi.fn()

      render(RisMetadataEditorRahmenForm, {
        props: {
          modelValue: testData,
          "onUpdate:modelValue": onModelUpdate,
        },
      })

      await user.click(screen.getByRole("combobox", { name: "Staat" }))
      await user.keyboard("{ArrowDown}{ArrowDown}{Enter}")
      expect(onModelUpdate).toHaveBeenLastCalledWith(
        expect.objectContaining({
          staat: "WB - Württemberg-Baden",
        }),
      )
    })

    it("should update beschließendes Organ", async () => {
      const user = userEvent.setup()
      const onModelUpdate = vi.fn()

      render(RisMetadataEditorRahmenForm, {
        props: {
          modelValue: testData,
          "onUpdate:modelValue": onModelUpdate,
        },
      })

      await user.click(
        screen.getByRole("combobox", { name: "beschließendes Organ" }),
      )
      await user.keyboard("{ArrowUp}{Enter}")
      expect(onModelUpdate).toHaveBeenLastCalledWith(
        expect.objectContaining({
          beschliessendesOrgan: "BMinI - Bundesministerium des Innern",
        }),
      )
    })

    it("should update Beschlussf. qual. Mehrheit", async () => {
      const user = userEvent.setup()
      const onModelUpdate = vi.fn()

      render(RisMetadataEditorRahmenForm, {
        props: {
          modelValue: testData,
          "onUpdate:modelValue": onModelUpdate,
        },
      })

      await user.click(
        screen.getByRole("checkbox", { name: "Beschlussf. qual. Mehrheit" }),
      )
      expect(onModelUpdate).toHaveBeenLastCalledWith(
        expect.objectContaining({
          qualifizierteMehrheit: false,
        }),
      )
    })

    it("should update Ressort", async () => {
      const user = userEvent.setup()
      const onModelUpdate = vi.fn()

      render(RisMetadataEditorRahmenForm, {
        props: {
          modelValue: testData,
          "onUpdate:modelValue": onModelUpdate,
        },
      })

      await user.click(screen.getByRole("combobox", { name: "Ressort" }))
      await user.keyboard("{ArrowDown}{ArrowDown}{Enter}")
      expect(onModelUpdate).toHaveBeenLastCalledWith(
        expect.objectContaining({
          ressort:
            "BMWSB - Bundesministerium für Wohnen, Stadtentwicklung und Bauwesen",
        }),
      )
    })

    it("should update Organisationseinheit", async () => {
      const user = userEvent.setup()
      const onModelUpdate = vi.fn()

      render(RisMetadataEditorRahmenForm, {
        props: {
          modelValue: testData,
          "onUpdate:modelValue": onModelUpdate,
        },
      })

      await user.clear(
        screen.getByRole("textbox", { name: "Organisationseinheit" }),
      )
      await user.type(
        screen.getByRole("textbox", { name: "Organisationseinheit" }),
        "V II 1",
      )
      expect(onModelUpdate).toHaveBeenLastCalledWith(
        expect.objectContaining({
          organisationsEinheit: "V II 1",
        }),
      )
    })
  })
})
