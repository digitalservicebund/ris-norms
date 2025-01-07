import { test as setup } from "@playwright/test"
import path from "node:path"
import fs from "fs"
import { fileURLToPath } from "node:url"

const __dirname = path.dirname(fileURLToPath(import.meta.url))

setup("global setup", async ({ page }) => {
  // Login
  await page.goto("/login")
  await page.getByRole("link").click()

  await page
    .getByRole("textbox", { name: "Username or email" })
    .fill("jane.doe")

  await page.getByRole("textbox", { name: "Password" }).fill("test")

  await page.getByRole("button", { name: "Sign In" }).click()

  await page.context().storageState({ path: `e2e/setup/.auth/user.json` })

  // Upload test data
  const directoryPath = path.resolve(
    __dirname,
    "../../../LegalDocML.de/1.7.1/samples/amending-laws",
  )

  const files = fs.readdirSync(directoryPath)

  for (const file of files) {
    const filePath = path.join(directoryPath, file)
    const fileContent = fs.readFileSync(filePath) // Read the file content
    const formData = new FormData()
    formData.append("file", new Blob([fileContent], { type: "text/xml" }), file)
    formData.append("force", String(true))

    const response = await page.request.post(
      `${process.env.E2E_BASE_URL}/api/v1/announcements`,
      { multipart: formData },
    )

    if (!response.ok()) {
      throw new Error(`Failed to set up test data: ${response.statusText()}`)
    }

    console.log(`Imported ${file} successfully.`)
  }
})
