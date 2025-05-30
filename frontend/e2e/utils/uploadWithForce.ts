import { samplesDirectory } from "@e2e/utils/dataDirectories"
import type { APIRequestContext } from "@playwright/test"
import * as fs from "node:fs/promises"
import * as path from "node:path"

export async function uploadAmendingLaw(
  request: APIRequestContext,
  filename: string,
  directory: string = samplesDirectory,
) {
  const filePath = path.join(directory, filename)
  const fileContent = await fs.readFile(filePath)

  const response = await request.post("/api/v1/verkuendungen", {
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
    console.log(await response.json())
    throw new Error(`Failed to upload test data: ${response.status()}`)
  }
}
