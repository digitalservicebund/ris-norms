import { describe, expect, it } from "vitest"
import { getFrbrDisplayText } from "./frbr"

describe("getFrbrDisplayText", () => {
  it("formats the name of a printed announced law", () => {
    expect(
      getFrbrDisplayText({
        frbrName: "BGBl. I",
        frbrDateVerkuendung: "2017-03-15",
        frbrNumber: "s419",
      }),
    ).toBe("BGBl. I 2017 S. 419")
  })

  it("formats the name of a digitally announced law", () => {
    expect(
      getFrbrDisplayText({
        frbrName: "BGBl. I",
        frbrDateVerkuendung: "2023-12-29",
        frbrNumber: "413",
      }),
    ).toBe("BGBl. I 2023 Nr. 413")
  })

  it("returns undefined if the input is empty", () => {
    expect(getFrbrDisplayText(undefined)).toBeUndefined()
  })

  it("returns undefined if some necessary properties are missing", () => {
    expect(
      getFrbrDisplayText({
        frbrName: undefined,
        frbrDateVerkuendung: "2023-12-29",
        frbrNumber: "413",
      }),
    ).toBeUndefined()
    expect(
      getFrbrDisplayText({
        frbrName: "BGBl. I",
        frbrDateVerkuendung: "2023-12-29",
        frbrNumber: undefined,
      }),
    ).toBeUndefined()
  })
})
