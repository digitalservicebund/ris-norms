import { test as setup } from "@playwright/test"
import path from "node:path"
import fs from "fs"
import { samplesDirectory } from "@e2e/utils/samples-directory"

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

  const files = [
    "bgbl-1_1001_2_mods_01/aenderungsgesetz.xml",
    "bgbl-1_1002_2_mods-subsitution_01/aenderungsgesetz.xml",
    "bgbl-1_2017_s419/aenderungsgesetz.xml",
    "bgbl-1_2023_413/aenderungsgesetz.xml",
  ]

  for (const file of files) {
    const filePath = path.join(samplesDirectory, file)
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
