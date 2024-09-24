import { APIRequestContext } from "@playwright/test"
import * as fs from "node:fs/promises"
import * as path from "node:path"
import { fileURLToPath } from "node:url"

const __dirname = path.dirname(fileURLToPath(import.meta.url))

export async function uploadAmendingLaw(
  request: APIRequestContext,
  filename: string,
) {
  const filePath = path.join(
    __dirname,
    "../../../LegalDocML.de/1.6/samples/amending-laws",
    filename,
  )
  const fileContent = await fs.readFile(filePath)

  const response = await request.post("/api/v1/announcements", {
    multipart: {
      file: {
        name: filename,
        mimeType: "text/xml",
        buffer: fileContent,
      },
      force: String(true),
    },
  })

  if (!response.ok()) {
    throw new Error(`Failed to upload test data: ${response.status()}`)
  }
}
