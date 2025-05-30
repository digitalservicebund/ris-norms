import { samplesDirectory } from "@e2e/utils/dataDirectories"
import { test as setup } from "@e2e/utils/testWithAuth"
import fs from "fs"
import path from "node:path"

setup("login", async ({ page, appCredentials }) => {
  await page.goto("./")
  await page.waitForURL(/localhost:8443/)

  await page
    .getByRole("textbox", { name: "Username or email" })
    .fill(appCredentials.username)

  await page
    .getByRole("textbox", { name: "Password" })
    .fill(appCredentials.password)

  await page.getByRole("button", { name: "Sign In" }).click()

  await page.context().storageState({ path: `e2e/storage/state.json` })

  await page.waitForURL(/\/verkuendungen/)
})

setup("create sample data", async ({ authenticatedRequest: request }) => {
  const files = [
    "bgbl-1_1001_2_mods_01/aenderungsgesetz.xml",
    "bgbl-1_1002_2_mods-subsitution_01/aenderungsgesetz.xml",
    "bgbl-1_2017_s419/aenderungsgesetz.xml",
    "bgbl-1_2017_s593/regelungstext-1.xml",
    "bgbl-1_2023_413/aenderungsgesetz.xml",
  ]

  for (const file of files) {
    const filePath = path.join(samplesDirectory, file)
    const fileContent = fs.readFileSync(filePath) // Read the file content

    const formData = new FormData()
    formData.append("file", new Blob([fileContent], { type: "text/xml" }), file)
    formData.append("force", String(true))

    const response = await request.post(`/api/v1/verkuendungen`, {
      multipart: formData,
    })

    if (!response.ok()) {
      throw new Error(`Failed to set up test data: ${response.statusText()}`)
    }

    console.log(`Imported ${file} successfully.`)
  }
})
