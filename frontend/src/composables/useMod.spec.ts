import { beforeEach, describe, expect, test, vi } from "vitest"
import { nextTick, ref } from "vue"

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
      getQuotedStructureContent: vi
        .fn()
        .mockReturnValue("<quotedStructure>content</quotedStructure>"), // Added mock
      useUpdateModData: vi.fn(),
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
      quotedStructureContent, // Added expectation
    } = useMod("eli", "eid", `<xml></xml>`)

    expect(destinationHref.value).toBe(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-6_abs-3_inhalt-3_text-1/100-126.xml",
    )
    expect(textualModType.value).toBe("aenderungsbefehl-ersetzen")
    expect(quotedTextFirst.value).toBe("old text")
    expect(quotedTextSecond.value).toBe("new text")
    expect(timeBoundary.value).toBe("2020-01-01")
    expect(quotedStructureContent.value).toBe(
      "<quotedStructure>content</quotedStructure>",
    ) // Added expectation
  })

  test("should provide default values without a mod eid", async () => {
    const { useMod } = await import("./useMod")

    const {
      destinationHref,
      textualModType,
      quotedTextFirst,
      quotedTextSecond,
      timeBoundary,
      quotedStructureContent, // Added expectation
    } = useMod(null, null, `<xml></xml>`)

    expect(destinationHref.value).toBe("")
    expect(textualModType.value).toBe("")
    expect(quotedTextFirst.value).toBe("")
    expect(quotedTextSecond.value).toBe("")
    expect(timeBoundary.value).toBeUndefined()
    expect(quotedStructureContent.value).toBeUndefined() // Added expectation
  })

  test("should support changing the values of the returned refs", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getDestinationHref: vi.fn(),
      getQuotedTextFirst: vi.fn(),
      getQuotedTextSecond: vi.fn().mockReturnValue("new text"),
      getTextualModType: vi.fn(),
      getTimeBoundaryDate: vi.fn(),
      useUpdateModData: vi.fn(),
      getQuotedStructureContent: vi
        .fn()
        .mockReturnValue("<quotedStructure>content</quotedStructure>"), // Added mock
    }))
    vi.doMock("@/services/ldmldeService", () => ({
      getNodeByEid: vi.fn().mockReturnValue({ object: "that is not null" }),
    }))
    const { useMod } = await import("./useMod")

    const { quotedTextSecond } = useMod("eli", "eid", `<xml></xml>`)

    expect(quotedTextSecond.value).toBe("new text")
    quotedTextSecond.value = "newer text"
    expect(quotedTextSecond.value).toBe("newer text")
  })

  test("should overwrite the changed values when the eid changes", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getDestinationHref: vi.fn(),
      getQuotedTextFirst: vi.fn(),
      getQuotedTextSecond: vi.fn().mockReturnValue("new text"),
      getTextualModType: vi.fn(),
      getTimeBoundaryDate: vi.fn(),
      useUpdateModData: vi.fn(),
      getQuotedStructureContent: vi
        .fn()
        .mockReturnValue("<quotedStructure>content</quotedStructure>"), // Added mock
    }))
    vi.doMock("@/services/ldmldeService", () => ({
      getNodeByEid: vi.fn().mockReturnValue({ object: "that is not null" }),
    }))
    const { useMod } = await import("./useMod")

    const eid = ref("eid1")
    const { quotedTextSecond } = useMod("eli", eid, `<xml></xml>`)

    expect(quotedTextSecond.value).toBe("new text")

    quotedTextSecond.value = "newer text"
    expect(quotedTextSecond.value).toBe("newer text")

    eid.value = "eid2"
    await nextTick()
    expect(quotedTextSecond.value).toBe("new text")
  })

  test("should overwrite the changed values when the xml changes", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getDestinationHref: vi.fn(),
      getQuotedTextFirst: vi.fn(),
      getQuotedTextSecond: vi.fn().mockReturnValue("new text"),
      getTextualModType: vi.fn(),
      getTimeBoundaryDate: vi.fn(),
      useUpdateModData: vi.fn(),
      getQuotedStructureContent: vi
        .fn()
        .mockReturnValue("<quotedStructure>content</quotedStructure>"), // Added mock
    }))
    vi.doMock("@/services/ldmldeService", () => ({
      getNodeByEid: vi.fn().mockReturnValue({ object: "that is not null" }),
    }))
    const { useMod } = await import("./useMod")

    const xml = ref("<xml></xml>")
    const { quotedTextSecond } = useMod("eli", "eid1", xml)

    expect(quotedTextSecond.value).toBe("new text")

    quotedTextSecond.value = "newer text"
    expect(quotedTextSecond.value).toBe("newer text")

    xml.value = "<xml>new</xml>"
    await nextTick()
    expect(quotedTextSecond.value).toBe("new text")
  })

  test("should create update and preview using useUpdateModData", async () => {
    const eli = "test-eli"
    const eid = "test-eid"

    const useUpdateModData = vi.fn()

    vi.doMock("@/services/ldmldeModService", () => ({
      getDestinationHref: vi.fn(),
      getQuotedTextFirst: vi.fn(),
      getQuotedTextSecond: vi.fn(),
      getTextualModType: vi.fn(),
      getTimeBoundaryDate: vi.fn(),
      useUpdateModData,
    }))

    const { useMod } = await import("./useMod")
    useMod(eli, eid, `<xml></xml>`)

    expect(useUpdateModData).toHaveBeenCalledWith(
      eli,
      eid,
      expect.anything(),
      false,
    )
    expect(useUpdateModData).toHaveBeenCalledWith(
      eli,
      eid,
      expect.anything(),
      true,
    )
  })
})
