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
    expect(timeBoundary.value).toBeUndefined()
  })

  test("should support changing the values of the returned refs", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getDestinationHref: vi.fn(),
      getQuotedTextFirst: vi.fn(),
      getQuotedTextSecond: vi.fn().mockReturnValue("new text"),
      getTextualModType: vi.fn(),
      getTimeBoundaryDate: vi.fn(),
    }))
    vi.doMock("@/services/ldmldeService", () => ({
      getNodeByEid: vi.fn().mockReturnValue({ object: "that is not null" }),
    }))
    const { useMod } = await import("./useMod")

    const { quotedTextSecond } = useMod("eid", `<xml></xml>`)

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
    }))
    vi.doMock("@/services/ldmldeService", () => ({
      getNodeByEid: vi.fn().mockReturnValue({ object: "that is not null" }),
    }))
    const { useMod } = await import("./useMod")

    const eid = ref("eid1")
    const { quotedTextSecond } = useMod(eid, `<xml></xml>`)

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
    }))
    vi.doMock("@/services/ldmldeService", () => ({
      getNodeByEid: vi.fn().mockReturnValue({ object: "that is not null" }),
    }))
    const { useMod } = await import("./useMod")

    const xml = ref("<xml></xml>")
    const { quotedTextSecond } = useMod("eid1", xml)

    expect(quotedTextSecond.value).toBe("new text")

    quotedTextSecond.value = "newer text"
    expect(quotedTextSecond.value).toBe("newer text")

    xml.value = "<xml>new</xml>"
    await nextTick()
    expect(quotedTextSecond.value).toBe("new text")
  })

  test("should update mod data and return the response", async () => {
    const eli = "test-eli"
    const eid = "test-eid"
    const updatedMods = {
      refersTo: "test-refersTo",
      timeBoundaryEid: "test-timeBoundaryEid",
      destinationHref: "test-destinationHref",
      newText: "test-newText",
    }

    vi.doMock("@/services/ldmldeModService", () => ({
      getDestinationHref: vi.fn(),
      getQuotedTextFirst: vi.fn(),
      getQuotedTextSecond: vi.fn(),
      getTextualModType: vi.fn(),
      getTimeBoundaryDate: vi.fn(),
      updateModData: vi.fn().mockResolvedValue({
        targetNormZf0Xml: "<xml>target-norm-zf0-xml</xml>",
        amendingNormXml: "<xml>amending-norm-xml</xml>",
      }),
    }))
    const { useMod } = await import("./useMod")
    const { updateMod } = useMod(eid, `<xml></xml>`)

    const result = await updateMod(eli, eid, updatedMods)

    expect(result.amendingNormXml).toBe("<xml>amending-norm-xml</xml>")
    expect(result.targetNormZf0Xml).toBe("<xml>target-norm-zf0-xml</xml>")

    const { updateModData } = await import("@/services/ldmldeModService")
    expect(updateModData).toHaveBeenCalledWith(eli, eid, updatedMods, false)
  })

  test("should preview update mod data and return the response", async () => {
    const eli = "test-eli"
    const eid = "test-eid"
    const updatedMods = {
      refersTo: "test-refersTo",
      timeBoundaryEid: "test-timeBoundaryEid",
      destinationHref: "test-destinationHref",
      newText: "test-newText",
    }

    vi.doMock("@/services/ldmldeModService", () => ({
      getDestinationHref: vi.fn(),
      getQuotedTextFirst: vi.fn(),
      getQuotedTextSecond: vi.fn(),
      getTextualModType: vi.fn(),
      getTimeBoundaryDate: vi.fn(),
      updateModData: vi.fn().mockResolvedValue({
        targetNormZf0Xml: "<xml>target-norm-zf0-xml</xml>",
        amendingNormXml: "<xml>amending-norm-xml</xml>",
      }),
    }))
    const { useMod } = await import("./useMod")
    const { previewUpdateMod } = useMod(eid, `<xml></xml>`)

    const result = await previewUpdateMod(eli, eid, updatedMods)

    expect(result.amendingNormXml).toBe("<xml>amending-norm-xml</xml>")
    expect(result.targetNormZf0Xml).toBe("<xml>target-norm-zf0-xml</xml>")

    const { updateModData } = await import("@/services/ldmldeModService")
    expect(updateModData).toHaveBeenCalledWith(eli, eid, updatedMods, true)
  })
})
