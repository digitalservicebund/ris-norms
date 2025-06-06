import { beforeEach, describe, expect, it, vi } from "vitest"
import { reactive } from "vue"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"

describe("useNormWorkEliPathParameter", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("should provide a valid NormWorkEli", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {
            eliJurisdiction: "bund",
            eliAgent: "bgbl-1",
            eliYear: "2017",
            eliNaturalIdentifier: "s419",
          },
        }),
      ),
    }))

    const { useNormWorkEliPathParameter } = await import(
      "./useNormWorkEliPathParameter"
    )
    const eli = useNormWorkEliPathParameter()

    expect(eli.value).toEqual(
      NormWorkEli.fromString("eli/bund/bgbl-1/2017/s419"),
    )
  })

  it("should provide a valid NormWorkEli with named parameters", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(
        reactive({
          params: {
            testEliJurisdiction: "bund",
            testEliAgent: "bgbl-1",
            testEliYear: "2017",
            testEliNaturalIdentifier: "s419",
          },
        }),
      ),
    }))

    const { useNormWorkEliPathParameter } = await import(
      "./useNormWorkEliPathParameter"
    )
    const eli = useNormWorkEliPathParameter("test")

    expect(eli.value).toEqual(
      NormWorkEli.fromString("eli/bund/bgbl-1/2017/s419"),
    )
  })

  it("should react to route param changes", async () => {
    const route = reactive({
      params: {
        eliJurisdiction: "bund",
        eliAgent: "bgbl-1",
        eliYear: "2017",
        eliNaturalIdentifier: "s419",
      },
    })

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(route),
    }))

    const { useNormWorkEliPathParameter } = await import(
      "./useNormWorkEliPathParameter"
    )
    const eli = useNormWorkEliPathParameter()

    expect(eli.value).toEqual(
      NormWorkEli.fromString("eli/bund/bgbl-1/2017/s419"),
    )

    route.params.eliYear = "2023"
    route.params.eliNaturalIdentifier = "413"

    expect(eli.value).toEqual(
      NormWorkEli.fromString("eli/bund/bgbl-1/2023/413"),
    )
  })

  it("should react to route param changes with named parameters", async () => {
    const route = reactive({
      params: {
        testEliAgent: "bgbl-1",
        testEliYear: "2017",
        testEliNaturalIdentifier: "s419",
      },
    })

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(route),
    }))

    const { useNormWorkEliPathParameter } = await import(
      "./useNormWorkEliPathParameter"
    )
    const eli = useNormWorkEliPathParameter("test")

    expect(eli.value.toString()).toBe("eli/bund/bgbl-1/2017/s419")

    // Simulate route param change
    route.params.testEliYear = "2022"
    route.params.testEliNaturalIdentifier = "415"

    expect(eli.value.toString()).toBe("eli/bund/bgbl-1/2022/415")
  })

  it("should throw if required params are missing", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(reactive({ params: {} })),
    }))

    const { useNormWorkEliPathParameter } = await import(
      "./useNormWorkEliPathParameter"
    )

    const eli = useNormWorkEliPathParameter()

    expect(() => eli.value).toThrowError(
      "useNormWorkEliPathParameter: Missing required route parameters",
    )
  })
})

describe("createNormWorkEliPathParameter", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("creates a valid path", async () => {
    const getPath = vi
      .fn()
      .mockResolvedValue(
        "eli/:eliJurisdiction(bund)" +
          "/:eliAgent(bgbl-1|bgbl-2|banz-at)" +
          "/:eliYear([12][0-9]{3})" +
          "/:eliNaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+)",
      )

    vi.doMock("@/services/normService", () => ({
      getPath,
    }))

    const { createNormWorkEliPathParameter } = await import(
      "./useNormWorkEliPathParameter"
    )
    const path = createNormWorkEliPathParameter()

    const expectedPath =
      "eli/:eliJurisdiction(bund)" +
      "/:eliAgent(bgbl-1|bgbl-2|banz-at)" +
      "/:eliYear([12][0-9]{3})" +
      "/:eliNaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+)"

    expect(path).toBe(expectedPath)
  })

  it("creates a valid path with prefix", async () => {
    const getPath = vi.fn((prefix) =>
      Promise.resolve(
        `eli/:${prefix}EliJurisdiction(bund)` +
          `/:${prefix}EliAgent(bgbl-1|bgbl-2|banz-at)` +
          `/:${prefix}EliYear([12][0-9]{3})` +
          `/:${prefix}EliNaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+)`,
      ),
    )

    vi.doMock("@/services/normService", () => ({
      getPath,
    }))

    const { createNormWorkEliPathParameter } = await import(
      "./useNormWorkEliPathParameter"
    )
    const path = createNormWorkEliPathParameter("test")

    const expectedPath =
      "eli/:testEliJurisdiction(bund)" +
      "/:testEliAgent(bgbl-1|bgbl-2|banz-at)" +
      "/:testEliYear([12][0-9]{3})" +
      "/:testEliNaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+)"

    expect(path).toEqual(expectedPath)
  })
})
