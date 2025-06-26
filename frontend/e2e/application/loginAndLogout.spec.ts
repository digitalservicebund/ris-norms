import { test } from "@e2e/utils/testWithAuth"
import { expect } from "@playwright/test"

test.use({ storageState: { cookies: [], origins: [] } })

test.describe(
  "login and logout functionality",
  { tag: ["@RISDEV-5654"] },
  () => {
    test("an unauthenticated user gets redirected to the login and then to the page they wanted to visit, after clicking Abmelden they are logged out again", async ({
      page,
    }) => {
      await page.goto(
        "./verkuendungen/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
      )

      await expect(
        page.getByRole("heading", { name: "Sign in to your account" }),
      ).toBeVisible()

      await page
        .getByRole("textbox", { name: "Username or email" })
        .fill("jane.doe")
      await page.getByRole("textbox", { name: "Password" }).fill("test")
      await page.getByRole("button", { name: "Sign In" }).click()

      await page.waitForURL(
        "/app/verkuendungen/eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
      )

      const logoutLinkAfterLogin = page.getByRole("link", { name: "Abmelden" })
      await expect(logoutLinkAfterLogin).toBeVisible()

      const usernameDisplay = page.getByText("Jane Doe")
      await expect(usernameDisplay).toBeVisible()

      await logoutLinkAfterLogin.click()
      await expect(
        page.getByRole("heading", { name: "Sign in to your account" }),
      ).toBeVisible()
    })

    test("API requests without authentication are rejected", async ({
      page,
    }) => {
      const response = await page.request.get("/api/v1/verkuendungen")
      expect(response.status()).toBe(401)
    })

    test("API requests with invalid authentication are rejected", async ({
      page,
    }) => {
      const response = await page.request.get("/api/v1/verkuendungen", {
        headers: {
          Authorization: `Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICItTUNDSVhLSHBhQUlrd3V2dzJYOUlzWlF2N29wa3NUaDJ5dnRzbzUtUmtrIn0.eyJleHAiOjE3MzkyODcyNzMsImlhdCI6MTczOTI4Njk3MywiYXV0aF90aW1lIjoxNzM5Mjg2NDE4LCJqdGkiOiI0NjI3Mjc0MS0xZWQyLTQ0OGUtOTMyOC1jNWFlOWYxNGY0MDEiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0Ojg0NDMvcmVhbG1zL3JpcyIsInN1YiI6ImphbmUuZG9lIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicmlzLW5vcm1zLWxvY2FsIiwic2lkIjoiYmZlZWFiNDgtNDNlMi00YjZlLWI2YzUtMTY1NmZiZTYzMTcxIiwiYWNyIjoiMCIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJodHRwOi8vbG9jYWxob3N0OjUxNzMiXSwic2NvcGUiOiJvcGVuaWQgZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiSmFuZSBEb2UiLCJncm91cHMiOlsiL05vcm1zIl0sInByZWZlcnJlZF91c2VybmFtZSI6ImphbmUuZG9lIiwiZ2l2ZW5fbmFtZSI6IkphbmUiLCJmYW1pbHlfbmFtZSI6IkRvZSIsImVtYWlsIjoiamFuZS5kb2VAZXhhbXBsZS5jb20ifQ.D_VERSumrkPC6C5WNvSC6VW776tt5q5Cud_LZGqBG1eGLr_kPz8ovCXslIwlwpsXfDO162mAkbXHV2pI3hwIBXJzetg6cghOdxzm6uLgBCwCZ1kBSkMAMRUEzP9MCKSexDPMLxeUn-w9RjNtPx9ZU6wYoFjrGG0W0wLLpQ9NfEWJhqRovs7Na97lD7HuaO0R5m2YcRsTuSopJkbSCFea4Gi2Hwb1ovuerXDDlFBLSQWNvK2Ek_heT8Q0FKhCLija1xti4RKdiIIh8nygN4ldaoOIucdBt9iuA5TNz-R2DkN1dYNvRyTk0YbIBI7dA57JkpOIOnbe7YDkOGGbESXAAQ`,
        },
      })
      expect(response.status()).toBe(401)
    })
  },
)
