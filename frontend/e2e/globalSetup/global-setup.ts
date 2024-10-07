import path from "node:path"
import fs from "fs"
import { fileURLToPath } from "node:url"

const __dirname = path.dirname(fileURLToPath(import.meta.url))

async function setup() {
  const directoryPath = path.resolve(
    __dirname,
    "../../../LegalDocML.de/1.7/samples/amending-laws",
  )

  // Read all files in the directory
  const files = fs.readdirSync(directoryPath)
  for (const file of files) {
    const filePath = path.join(directoryPath, file)
    const fileContent = fs.readFileSync(filePath) // Read the file content

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
