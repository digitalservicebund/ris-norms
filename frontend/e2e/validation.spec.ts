import { test, expect } from "@playwright/test"

test.describe("Validation errors on check modifications page", () => {
  test("Wrong character range", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_%C3%A4ndbefehl-1",
    )
    await page
      .getByLabel("zu ersetzende Textstelle")
      .fill(
        "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-36.xml",
      )
    await page.getByLabel("zu ersetzende Textstelle").press("Enter")

    const response = await page.waitForResponse(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/mods/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_%C3%A4ndbefehl-1?dryRun=true",
    )
    expect(response.status()).toBe(422)
    expect((await response.json()).message).toBe(
      "The character range 9-36 of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-1 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1 does not resolve to the targeted text to be replaced.",
    )

    await expect(
      page.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()

    await expect(
      page.getByRole("button", {
        name: "Trace-ID in die Zwischenablage kopieren",
      }),
    ).toBeVisible()

    await page.getByRole("button", { name: "Speichern" }).click()

    await expect(page.getByText("Fehler beim Speichern")).toBeVisible()
  })

  test("Wrong eId", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_%C3%A4ndbefehl-1",
    )
    await page
      .getByLabel("zu ersetzende Textstelle")
      .fill(
        "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1wrongEid/9-34.xml",
      )
    await page.getByLabel("zu ersetzende Textstelle").press("Enter")

    const response = await page.waitForResponse(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/mods/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_%C3%A4ndbefehl-1?dryRun=true",
    )
    expect(response.status()).toBe(422)
    expect((await response.json()).message).toBe(
      "Target node with eid hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1wrongEid not present in ZF0 norm with eli eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1.",
    )

    await expect(
      page.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()

    await expect(
      page.getByRole("button", {
        name: "Trace-ID in die Zwischenablage kopieren",
      }),
    ).toBeVisible()

    await page.getByRole("button", { name: "Speichern" }).click()

    await expect(page.getByText("Fehler beim Speichern")).toBeVisible()
  })

  test("Using out of range character range", async ({ page }) => {
    await page.goto(
      "/amending-laws/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/articles/hauptteil-1_art-1/edit/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_%C3%A4ndbefehl-1",
    )
    await page
      .getByLabel("zu ersetzende Textstelle")
      .fill(
        "hauptteil-1_para-20_abs-1_untergl-1_listenelem-2_inhalt-1_text-1/9-3400.xml",
      )
    await page.getByLabel("zu ersetzende Textstelle").press("Enter")

    const response = await page.waitForResponse(
      "/api/v1/norms/eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1/mods/hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_%C3%A4ndbefehl-1?dryRun=true",
    )
    expect(response.status()).toBe(422)
    expect((await response.json()).message).toBe(
      "The character range 9-3400 of passive mod with eId meta-1_analysis-1_pasmod-1_textualmod-1 within ZF0 norm with eli eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1 is not within the target node.",
    )

    await expect(
      page.getByText("Ein unbekannter Fehler ist aufgetreten."),
    ).toBeVisible()

    await expect(
      page.getByRole("button", {
        name: "Trace-ID in die Zwischenablage kopieren",
      }),
    ).toBeVisible()

    await page.getByRole("button", { name: "Speichern" }).click()

    await expect(page.getByText("Fehler beim Speichern")).toBeVisible()
  })
})
