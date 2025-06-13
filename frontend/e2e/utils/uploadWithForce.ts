import { samplesDirectory } from "@e2e/utils/dataDirectories"
import type { APIRequestContext } from "@playwright/test"
import * as fs from "node:fs/promises"
import * as path from "node:path"
import child_process from "node:child_process"

export async function createTemporaryZipFromFolder(
  folderPath: string,
): Promise<Buffer> {
  const temporaryFileName = "norms-e2e-test-upload.tmp.zip"
  console.log(`Creating new temporary zip file from ${folderPath}`)
  child_process.execSync(`zip ${temporaryFileName} *`, {
    cwd: folderPath,
  })
  const buffer = await fs.readFile(`${folderPath}/${temporaryFileName}`)
  await fs.rm(`${folderPath}/${temporaryFileName}`)
  return buffer
}

export async function uploadAmendingLaw(
  request: APIRequestContext,
  folderName: string,
  directory: string = samplesDirectory,
) {
  const folderPath = path.join(directory, folderName)
  const fileContent = await createTemporaryZipFromFolder(folderPath)

  const response = await request.post("/api/v1/verkuendungen", {
    multipart: {
      file: {
        name: `${folderName}.zip`,
        mimeType: "application/zip",
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
