import { beforeEach, describe, expect, it, vi } from "vitest"
import { reactive } from "vue"
import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

describe("useDokumentExpressionEliPathParameter", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("should provide a valid ELI", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {
            dokumentExpressionEliJurisdiction: "bund",
            dokumentExpressionEliAgent: "bgbl-1",
            dokumentExpressionEliYear: "2017",
            dokumentExpressionEliNaturalIdentifier: "s419",
            dokumentExpressionEliPointInTime: "2017-03-15",
            dokumentExpressionEliVersion: "1",
            dokumentExpressionEliLanguage: "deu",
            dokumentExpressionEliSubtype: "regelungstext-1",
          },
        }),
      ),
    }))

    const { useDokumentExpressionEliPathParameter } = await import(
      "./useDokumentExpressionEliPathParameter"
    )
    const eli = useDokumentExpressionEliPathParameter()

    expect(eli.value).toEqual(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      ),
    )
  })

  it("should provide a valid ELI with named parameters", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {
            testDokumentExpressionEliJurisdiction: "bund",
            testDokumentExpressionEliAgent: "bgbl-1",
            testDokumentExpressionEliYear: "2017",
            testDokumentExpressionEliNaturalIdentifier: "s419",
            testDokumentExpressionEliPointInTime: "2017-03-15",
            testDokumentExpressionEliVersion: "1",
            testDokumentExpressionEliLanguage: "deu",
            testDokumentExpressionEliSubtype: "regelungstext-1",
          },
        }),
      ),
    }))

    const { useDokumentExpressionEliPathParameter } = await import(
      "./useDokumentExpressionEliPathParameter"
    )
    const eli = useDokumentExpressionEliPathParameter("test")

    expect(eli.value).toEqual(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      ),
    )
  })

  it("should react to route param changes", async () => {
    const route = reactive({
      params: {
        dokumentExpressionEliJurisdiction: "bund",
        dokumentExpressionEliAgent: "bgbl-1",
        dokumentExpressionEliYear: "2017",
        dokumentExpressionEliNaturalIdentifier: "s419",
        dokumentExpressionEliPointInTime: "2017-03-15",
        dokumentExpressionEliVersion: "1",
        dokumentExpressionEliLanguage: "deu",
        dokumentExpressionEliSubtype: "regelungstext-1",
      },
    })

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(route),
    }))

    const { useDokumentExpressionEliPathParameter } = await import(
      "./useDokumentExpressionEliPathParameter"
    )
    const eli = useDokumentExpressionEliPathParameter()

    expect(eli.value).toEqual(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      ),
    )

    route.params.dokumentExpressionEliYear = "2023"
    route.params.dokumentExpressionEliNaturalIdentifier = "413"
    route.params.dokumentExpressionEliPointInTime = "2023-12-29"

    expect(eli.value).toEqual(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      ),
    )
  })

  it("should react to route param changes with named parameters", async () => {
    const route = reactive({
      params: {
        testDokumentExpressionEliJurisdiction: "bund",
        testDokumentExpressionEliAgent: "bgbl-1",
        testDokumentExpressionEliYear: "2017",
        testDokumentExpressionEliNaturalIdentifier: "s419",
        testDokumentExpressionEliPointInTime: "2017-03-15",
        testDokumentExpressionEliVersion: "1",
        testDokumentExpressionEliLanguage: "deu",
        testDokumentExpressionEliSubtype: "regelungstext-1",
      },
    })

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(route),
    }))

    const { useDokumentExpressionEliPathParameter } = await import(
      "./useDokumentExpressionEliPathParameter"
    )
    const eli = useDokumentExpressionEliPathParameter("test")

    expect(eli.value).toEqual(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      ),
    )

    route.params.testDokumentExpressionEliYear = "2023"
    route.params.testDokumentExpressionEliNaturalIdentifier = "413"
    route.params.testDokumentExpressionEliPointInTime = "2023-12-29"

    expect(eli.value).toEqual(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      ),
    )
  })

  it("should throw if ELI parameters are missing", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {},
        }),
      ),
    }))

    const { useDokumentExpressionEliPathParameter } = await import(
      "./useDokumentExpressionEliPathParameter"
    )
    const eli = useDokumentExpressionEliPathParameter()

    expect(() => eli.value).toThrowError(
      "useDokumentExpressionEliPathParameter: You can only use this composable on pages which have a DokumentExpressionELI in their route",
    )
  })

  it("should throw if named ELI parameters are missing", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {},
        }),
      ),
    }))

    const { useDokumentExpressionEliPathParameter } = await import(
      "./useDokumentExpressionEliPathParameter"
    )
    const eli = useDokumentExpressionEliPathParameter("test")

    expect(() => eli.value).toThrowError(
      "useDokumentExpressionEliPathParameter: You can only use this composable on pages which have a DokumentExpressionELI prefixed with test in their route",
    )
  })
})

describe("createEliPathParameter", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("creates a valid path", async () => {
    const getPath = vi
      .fn()
      .mockResolvedValue(
        "eli/:dokumentExpressionEliJurisdiction(bund)" +
          "/:dokumentExpressionEliAgent(bgbl-1|bgbl-2|banz-at)" +
          "/:dokumentExpressionEliYear([12][0-9]{3})" +
          "/:dokumentExpressionEliNaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+)" +
          "/:dokumentExpressionEliPointInTime([12][0-9]{3}-[0-9]{2}-[0-9]{2})" +
          "/:dokumentExpressionEliVersion([0-9]+)" +
          "/:dokumentExpressionEliLanguage(deu)" +
          "/:dokumentExpressionEliSubtype(regelungstext-[0-9]+|offenestruktur-[0-9]+|vereinbarung-[0-9]+|bekanntmachungstext-[0-9]+|externesdokument-[0-9]+|rechtsetzungsdokument-[0-9]+)",
      )

    vi.doMock("@/services/normService", () => ({
      getPath,
    }))

    const { createDokumentExpressionEliPathParameter } = await import(
      "./useDokumentExpressionEliPathParameter"
    )
    const path = createDokumentExpressionEliPathParameter()

    const expectedPath =
      "eli/:dokumentExpressionEliJurisdiction(bund)" +
      "/:dokumentExpressionEliAgent(bgbl-1|bgbl-2|banz-at)" +
      "/:dokumentExpressionEliYear([12][0-9]{3})" +
      "/:dokumentExpressionEliNaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+)" +
      "/:dokumentExpressionEliPointInTime([12][0-9]{3}-[0-9]{2}-[0-9]{2})" +
      "/:dokumentExpressionEliVersion([0-9]+)" +
      "/:dokumentExpressionEliLanguage(deu)" +
      "/:dokumentExpressionEliSubtype(regelungstext-[0-9]+|offenestruktur-[0-9]+|vereinbarung-[0-9]+|bekanntmachungstext-[0-9]+|externesdokument-[0-9]+|rechtsetzungsdokument-[0-9]+)"

    expect(path).toBe(expectedPath)
  })

  it("creates a path with a prefix", async () => {
    const getPath = vi.fn((prefix) =>
      Promise.resolve(
        `eli/:${prefix}DokumentExpressionEliJurisdiction(bund)` +
          `/:${prefix}DokumentExpressionEliAgent(bgbl-1|bgbl-2|banz-at)` +
          `/:${prefix}DokumentExpressionEliYear([12][0-9]{3})` +
          `/:${prefix}DokumentExpressionEliNaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+)` +
          `/:${prefix}DokumentExpressionEliPointInTime([12][0-9]{3}-[0-9]{2}-[0-9]{2})` +
          `/:${prefix}DokumentExpressionEliVersion([0-9]+)` +
          `/:${prefix}DokumentExpressionEliLanguage(deu)` +
          `/:${prefix}DokumentExpressionEliSubtype(regelungstext-[0-9]+|offenestruktur-[0-9]+|vereinbarung-[0-9]+|bekanntmachungstext-[0-9]+|externesdokument-[0-9]+|rechtsetzungsdokument-[0-9]+)`,
      ),
    )

    vi.doMock("@/services/normService", () => ({
      getPath,
    }))

    const { createDokumentExpressionEliPathParameter } = await import(
      "./useDokumentExpressionEliPathParameter"
    )
    const path = createDokumentExpressionEliPathParameter("test")

    const expectedPath =
      "eli/:testDokumentExpressionEliJurisdiction(bund)" +
      "/:testDokumentExpressionEliAgent(bgbl-1|bgbl-2|banz-at)" +
      "/:testDokumentExpressionEliYear([12][0-9]{3})" +
      "/:testDokumentExpressionEliNaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+)" +
      "/:testDokumentExpressionEliPointInTime([12][0-9]{3}-[0-9]{2}-[0-9]{2})" +
      "/:testDokumentExpressionEliVersion([0-9]+)" +
      "/:testDokumentExpressionEliLanguage(deu)" +
      "/:testDokumentExpressionEliSubtype(regelungstext-[0-9]+|offenestruktur-[0-9]+|vereinbarung-[0-9]+|bekanntmachungstext-[0-9]+|externesdokument-[0-9]+|rechtsetzungsdokument-[0-9]+)"

    expect(path).toEqual(expectedPath)
  })
})
