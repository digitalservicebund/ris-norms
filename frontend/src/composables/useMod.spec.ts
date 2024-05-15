import { beforeEach, describe, expect, test, vi } from "vitest"

describe("useMod", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide the data about a mod", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getDestinationHref: vi
        .fn()
        .mockReturnValue(
          "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1/100-126.xml",
        ),
      getQuotedTextFirst: vi.fn().mockReturnValue("old text"),
      getQuotedTextSecond: vi.fn().mockReturnValue("new text"),
      getTextualModType: vi.fn().mockReturnValue("aenderungsbefehl-ersetzen"),
      getTimeBoundaryDate: vi.fn().mockReturnValue("2020-01-01"),
    }))
    vi.doMock("@/services/ldmldeService", () => ({
      getNodeByEid: vi.fn().mockReturnValue({ object: "that is not null" }),
    }))
    const { useMod } = await import("./useMod")

    const {
      destinationHref,
      textualModType,
      quotedTextFirst,
      quotedTextSecond,
      timeBoundary,
    } = useMod("eid", `<xml></xml>`)

    expect(destinationHref.value).toBe(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1/100-126.xml",
    )
    expect(textualModType.value).toBe("aenderungsbefehl-ersetzen")
    expect(quotedTextFirst.value).toBe("old text")
    expect(quotedTextSecond.value).toBe("new text")
    expect(timeBoundary.value).toBe("2020-01-01")
  })

  test("should provide default values without a mod eid", async () => {
    const { useMod } = await import("./useMod")

    const {
      destinationHref,
      textualModType,
      quotedTextFirst,
      quotedTextSecond,
      timeBoundary,
    } = useMod(null, `<xml></xml>`)

    expect(destinationHref.value).toBe("")
    expect(textualModType.value).toBe("")
    expect(quotedTextFirst.value).toBe("")
    expect(quotedTextSecond.value).toBe("")
    expect(timeBoundary.value).toBe("no_choice")
  })
})
