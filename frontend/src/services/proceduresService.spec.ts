import { describe, it, expect, vi } from "vitest"
import { getProcedures } from "./proceduresService"

vi.mock("./proceduresService", () => ({
  getProcedures: vi.fn(),
}))

describe("Service consumer tests", () => {
  it("tests another function or component using getProcedures with mock data", async () => {
    const mockedProceduresArray = [
      {
        eli: "eli/example/2023/1",
        printAnnouncementGazette: "Example Gazette",
        printAnnouncementYear: 2023,
        printAnnouncementNumber: 1,
        printAnnouncementPage: 100,
        publicationDate: new Date("2023-01-01"),
        fna: "123-456",
      },
      {
        eli: "eli/example/2024/2",
        printAnnouncementGazette: "Example Gazette 2",
        printAnnouncementYear: 2024,
        printAnnouncementNumber: 2,
        printAnnouncementPage: 101,
        publicationDate: new Date("2024-02-02"),
        fna: "456-789",
      },
    ]

    vi.mocked(getProcedures).mockResolvedValue(mockedProceduresArray)
    const result = await getProcedures()
    expect(result).toBe(mockedProceduresArray)

    expect(getProcedures).toHaveBeenCalled()
  })
})
