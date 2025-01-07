import path from "node:path"
import fs from "fs"
import { fileURLToPath } from "node:url"

export const samplesDirectory = path.resolve(
  path.dirname(fileURLToPath(import.meta.url)),
  "../../../LegalDocML.de/1.7.1/samples",
)

async function setup() {
  const files = [
    "bgbl-1_1001_2_mods_01/aenderungsgesetz.xml",
    "bgbl-1_1002_2_mods-subsitution_01/aenderungsgesetz.xml",
    "bgbl-1_2017_s419/aenderungsgesetz.xml",
    "bgbl-1_2023_413/aenderungsgesetz.xml",
  ]

  for (const file of files) {
    const filePath = path.join(samplesDirectory, file)
    const fileContent = fs.readFileSync(filePath)

    const formData = new FormData()
    formData.append("file", new Blob([fileContent], { type: "text/xml" }), file)
    formData.append("force", String(true))

    const response = await fetch(
      `${process.env.E2E_BASE_URL}/api/v1/announcements`,
      {
        method: "POST",
        body: formData,
      },
    )

    if (!response.ok) {
      throw new Error(`Failed to set up test data: ${response.statusText}`)
    }

    console.log(`Imported ${file} successfully.`)
  }
}

export default setup
