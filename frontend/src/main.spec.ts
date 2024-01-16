import { screen } from "@testing-library/dom"

describe("App", () => {
  it("shows Hello ds", () => {
    document.body.innerHTML = `
      <h1>TypeScript + Vite Application Template<h1>
    `
    expect(
      screen.getByText("TypeScript + Vite Application Template"),
    ).toBeVisible()
  })
})
