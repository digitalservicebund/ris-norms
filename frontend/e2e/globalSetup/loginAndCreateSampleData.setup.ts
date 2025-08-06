import { samplesDirectory } from "@e2e/utils/dataDirectories"
import { test as setup } from "@e2e/utils/testWithAuth"
import fs from "fs"
import path from "node:path"
import * as child_process from "node:child_process"

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

function createTemporaryZipFromFolder(folderPath: string): Buffer {
  const temporaryFileName = "norms-e2e-test-upload.tmp.zip"
  console.log(`Creating new temporary zip file from ${folderPath}`)
  child_process.execSync(`zip ./${temporaryFileName} *`, {
    cwd: folderPath,
  })
  const buffer = fs.readFileSync(`${folderPath}/${temporaryFileName}`)
  fs.rmSync(`${folderPath}/${temporaryFileName}`)
  return buffer
}

setup("create sample data", async ({ authenticatedRequest: request }) => {
  const folders = [
    "bgbl-1_1001_2_mods_01/aenderungsgesetz",
    "bgbl-1_1002_2_mods-subsitution_01/aenderungsgesetz",
    "bgbl-1_2017_s419/aenderungsgesetz",
    "bgbl-1_2023_413/aenderungsgesetz",
    "bgbl-1_2024_17",
  ]

  for (const folder of folders) {
    const folderPath = path.join(samplesDirectory, folder)

    const zipContent = createTemporaryZipFromFolder(folderPath)

    const formData = new FormData()
    formData.append(
      "file",
      new Blob([new Uint8Array(zipContent)], { type: "application/zip" }),
      folder,
    )
    formData.append("force", String(true))

    const response = await request.post(`/api/v1/verkuendungen`, {
      multipart: formData,
    })

    if (!response.ok()) {
      throw new Error(`Failed to set up test data: ${response.statusText()}`)
    }

    console.log(`Imported ${folder} successfully.`)
  }
})
