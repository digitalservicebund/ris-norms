import { Norm } from "@/types/norm"
import { describe, expect, test } from "vitest"
import { getFrbrDisplayText } from "./frbr"

describe("getFrbrDisplayText", () => {
  test("formats the name of a printed announced law", () => {
    const amendingLaw: Norm = {
      eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      frbrName: "BGBl. I",
      frbrDateVerkuendung: "2017-03-15",
      frbrNumber: "s419",
      title: "Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
    }

    expect(getFrbrDisplayText(amendingLaw)).toBe("BGBl. I 2017 S. 419")
  })

  test("formats the name of a digitally announced law", () => {
    const amendingLaw: Norm = {
      eli: "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      frbrName: "BGBl. I",
      frbrDateVerkuendung: "2023-12-29",
      frbrNumber: "413",
      title: "Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts",
    }

    expect(getFrbrDisplayText(amendingLaw)).toBe("BGBl. I 2023 Nr. 413")
  })
})
