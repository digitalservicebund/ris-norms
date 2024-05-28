import { beforeEach, describe, expect, test, vi } from "vitest"
import { reactive } from "vue"

describe("useEliPathParameter", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("should provide a valid ELI", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {
            eliJurisdiction: "bund",
            eliAgent: "bgbl-1",
            eliYear: "2017",
            eliNaturalIdentifier: "s419",
            eliPointInTime: "2017-03-15",
            eliVersion: "1",
            eliLanguage: "deu",
            eliSubtype: "regelungstext-1",
          },
        }),
      ),
    }))

    const { useEliPathParameter } = await import("./useEliPathParameter")
    const eli = useEliPathParameter()

    expect(eli.value).toBe(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
    )
  })

  test("should provide a valid ELI with named parameters", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {
            testEliJurisdiction: "bund",
            testEliAgent: "bgbl-1",
            testEliYear: "2017",
            testEliNaturalIdentifier: "s419",
            testEliPointInTime: "2017-03-15",
            testEliVersion: "1",
            testEliLanguage: "deu",
            testEliSubtype: "regelungstext-1",
          },
        }),
      ),
    }))

    const { useEliPathParameter } = await import("./useEliPathParameter")
    const eli = useEliPathParameter("test")

    expect(eli.value).toBe(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
    )
  })

  test("should react to route param changes", async () => {
    const route = reactive({
      params: {
        eliJurisdiction: "bund",
        eliAgent: "bgbl-1",
        eliYear: "2017",
        eliNaturalIdentifier: "s419",
        eliPointInTime: "2017-03-15",
        eliVersion: "1",
        eliLanguage: "deu",
        eliSubtype: "regelungstext-1",
      },
    })

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(route),
    }))

    const { useEliPathParameter } = await import("./useEliPathParameter")
    const eli = useEliPathParameter()

    expect(eli.value).toBe(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
    )

    route.params.eliYear = "2023"
    route.params.eliNaturalIdentifier = "413"
    route.params.eliPointInTime = "2023-12-29"

    expect(eli.value).toBe(
      "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
    )
  })

  test("should react to route param changes with named parameters", async () => {
    const route = reactive({
      params: {
        testEliJurisdiction: "bund",
        testEliAgent: "bgbl-1",
        testEliYear: "2017",
        testEliNaturalIdentifier: "s419",
        testEliPointInTime: "2017-03-15",
        testEliVersion: "1",
        testEliLanguage: "deu",
        testEliSubtype: "regelungstext-1",
      },
    })

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(route),
    }))

    const { useEliPathParameter } = await import("./useEliPathParameter")
    const eli = useEliPathParameter("test")

    expect(eli.value).toBe(
      "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
    )

    route.params.testEliYear = "2023"
    route.params.testEliNaturalIdentifier = "413"
    route.params.testEliPointInTime = "2023-12-29"

    expect(eli.value).toBe(
      "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
    )
  })

  test("should throw if ELI parameters are missing", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {},
        }),
      ),
    }))

    const { useEliPathParameter } = await import("./useEliPathParameter")
    const eli = useEliPathParameter()

    expect(() => eli.value).toThrowError(
      "useEliPathParameter: You can only use this composable on pages which have an ELI in their route",
    )
  })

  test("should throw if named ELI parameters are missing", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {},
        }),
      ),
    }))

    const { useEliPathParameter } = await import("./useEliPathParameter")
    const eli = useEliPathParameter("test")

    expect(() => eli.value).toThrowError(
      "useEliPathParameter: You can only use this composable on pages which have an ELI prefixed with test in their route",
    )
  })
})

describe("createEliPathParameter", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("creates a valid path", async () => {
    const getPath = vi
      .fn()
      .mockResolvedValue(
        "eli/:eliJurisdiction(bund)" +
          "/:eliAgent(bgbl-1|bgbl-2|banz-at)" +
          "/:eliYear([12][0-9]{3})" +
          "/:eliNaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+)" +
          "/:eliPointInTime([12][0-9]{3}-[0-9]{2}-[0-9]{2})" +
          "/:eliVersion([0-9]+)" +
          "/:eliLanguage(deu)" +
          "/:eliSubtype(regelungstext-[0-9]+|offenestruktur-[0-9]+|vereinbarung-[0-9]+|bekanntmachungstext-[0-9]+|externesdokument-[0-9]+|rechtsetzungsdokument-[0-9]+)",
      )

    vi.doMock("@/services/normService", () => ({
      getPath,
    }))

    const { createEliPathParameter } = await import("./useEliPathParameter")
    const path = createEliPathParameter()

    const expectedPath =
      "eli/:eliJurisdiction(bund)" +
      "/:eliAgent(bgbl-1|bgbl-2|banz-at)" +
      "/:eliYear([12][0-9]{3})" +
      "/:eliNaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+)" +
      "/:eliPointInTime([12][0-9]{3}-[0-9]{2}-[0-9]{2})" +
      "/:eliVersion([0-9]+)" +
      "/:eliLanguage(deu)" +
      "/:eliSubtype(regelungstext-[0-9]+|offenestruktur-[0-9]+|vereinbarung-[0-9]+|bekanntmachungstext-[0-9]+|externesdokument-[0-9]+|rechtsetzungsdokument-[0-9]+)"

    expect(path).toBe(expectedPath)
  })

  test("creates a path with a prefix", async () => {
    const getPath = vi.fn((prefix) =>
      Promise.resolve(
        `eli/:${prefix}EliJurisdiction(bund)` +
          `/:${prefix}EliAgent(bgbl-1|bgbl-2|banz-at)` +
          `/:${prefix}EliYear([12][0-9]{3})` +
          `/:${prefix}EliNaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+)` +
          `/:${prefix}EliPointInTime([12][0-9]{3}-[0-9]{2}-[0-9]{2})` +
          `/:${prefix}EliVersion([0-9]+)` +
          `/:${prefix}EliLanguage(deu)` +
          `/:${prefix}EliSubtype(regelungstext-[0-9]+|offenestruktur-[0-9]+|vereinbarung-[0-9]+|bekanntmachungstext-[0-9]+|externesdokument-[0-9]+|rechtsetzungsdokument-[0-9]+)`,
      ),
    )

    vi.doMock("@/services/normService", () => ({
      getPath,
    }))

    const { createEliPathParameter } = await import("./useEliPathParameter")
    const path = createEliPathParameter("test")

    const expectedPath =
      "eli/:testEliJurisdiction(bund)" +
      "/:testEliAgent(bgbl-1|bgbl-2|banz-at)" +
      "/:testEliYear([12][0-9]{3})" +
      "/:testEliNaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+)" +
      "/:testEliPointInTime([12][0-9]{3}-[0-9]{2}-[0-9]{2})" +
      "/:testEliVersion([0-9]+)" +
      "/:testEliLanguage(deu)" +
      "/:testEliSubtype(regelungstext-[0-9]+|offenestruktur-[0-9]+|vereinbarung-[0-9]+|bekanntmachungstext-[0-9]+|externesdokument-[0-9]+|rechtsetzungsdokument-[0-9]+)"

    expect(path).toBe(expectedPath)
  })
})
