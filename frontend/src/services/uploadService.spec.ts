import { describe, it, vi, beforeEach, expect } from "vitest"
import { useForceUploadFile } from "@/services/uploadService"

describe("uploadService", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  describe("useUploadService", () => {
    it("sends a POST request with form data and returns the norm", async () => {
      const mockFile = new File(["<xml></xml>"], "test.xml", {
        type: "text/xml",
      })
      const expectedNorm = {
        eli: "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
        title: "Test Norm",
      }

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(new Response(JSON.stringify(expectedNorm)))

      const { data, isFinished } = useForceUploadFile(mockFile)

      await vi.waitUntil(() => isFinished.value)

      expect(data.value).toEqual(expectedNorm)
      expect(fetchSpy).toHaveBeenCalledWith(
        `/api/v1/announcements?force=true`,
        expect.objectContaining({
          method: "POST",
          body: expect.any(FormData),
        }),
      )
    })

    it("handles errors returned by the server", async () => {
      const mockFile = new File(["<xml></xml>"], "test.xml", {
        type: "text/xml",
      })
      const expectedError = {
        type: "/errors/internal-server-error",
        title: "Internal Server Error",
        status: 500,
        detail: "A server error occurred",
      }

      const fetchSpy = vi
        .spyOn(global, "fetch")
        .mockResolvedValue(
          new Response(JSON.stringify(expectedError), { status: 500 }),
        )

      const { error, isFinished } = useForceUploadFile(mockFile)

      await vi.waitUntil(() => isFinished.value)

      expect(error.value).toEqual(expectedError)
      expect(fetchSpy).toHaveBeenCalledWith(
        `/api/v1/announcements?force=true`,
        expect.objectContaining({
          method: "POST",
          body: expect.any(FormData),
        }),
      )
    })
  })
})
