import type { Norm } from "@/types/norm"
import { describe, expect, it } from "vitest"
import { getFrbrDisplayText } from "./frbr"

describe("getFrbrDisplayText", () => {
  it("formats the name of a printed announced law", () => {
    const norm: Norm = {
      eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      frbrName: "BGBl. I",
      frbrDateVerkuendung: "2017-03-15",
      frbrNumber: "s419",
      title: "Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
    }

    expect(getFrbrDisplayText(norm)).toBe("BGBl. I 2017 S. 419")
  })

  it("formats the name of a digitally announced law", () => {
    const norm: Norm = {
      eli: "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      frbrName: "BGBl. I",
      frbrDateVerkuendung: "2023-12-29",
      frbrNumber: "413",
      title: "Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts",
    }

    expect(getFrbrDisplayText(norm)).toBe("BGBl. I 2023 Nr. 413")
  })

  it("returns undefined if the input is empty", () => {
    expect(getFrbrDisplayText(undefined)).toBeUndefined()
  })

  it("returns undefined if some necessary properties are missing", () => {
    const normWithoutName: Norm = {
      eli: "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      frbrName: undefined,
      frbrDateVerkuendung: "2023-12-29",
      frbrNumber: "413",
      title: "Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts",
    }

    const normWithoutNumber: Norm = {
      eli: "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      frbrName: "BGBl. I",
      frbrDateVerkuendung: "2023-12-29",
      frbrNumber: undefined,
      title: "Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts",
    }

    expect(getFrbrDisplayText(normWithoutName)).toBeUndefined()
    expect(getFrbrDisplayText(normWithoutNumber)).toBeUndefined()
  })
})
