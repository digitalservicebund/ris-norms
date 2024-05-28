import { ref } from "vue"
import { describe, it, expect, vi } from "vitest"
import { useEntryIntoForceHtml } from "@/composables/useEntryIntoForceHtml"
import * as temporalDataService from "@/services/temporalDataService"

describe("useEntryIntoForceHtml", () => {
  it("should load the HTML content of the entry into force article", async () => {
    const expectedHtmlContent = `<div><h1>Test Content</h1></div>`
    const eli = ref("eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1")

    vi.spyOn(temporalDataService, "getEntryIntoForceHtml").mockResolvedValue(
      expectedHtmlContent,
    )

    const { htmlContent, loadHtmlContent } = useEntryIntoForceHtml(eli)
    await loadHtmlContent()
    expect(htmlContent.value).toBe(expectedHtmlContent)
  })
})
