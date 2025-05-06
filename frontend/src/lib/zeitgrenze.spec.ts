import { describe, expect, it } from "vitest"
import { getZeitgrenzeArtLabel } from "./zeitgrenze"

describe("zeitgrenze", () => {
  it('prints the label for the Art of a "inkraft" Zeitgrenze', () => {
    expect(getZeitgrenzeArtLabel("INKRAFT")).toBe("Inkrafttreten")
  })

  it('prints the label for the Art of a "ausserkraft" Zeitgrenze', () => {
    expect(getZeitgrenzeArtLabel("AUSSERKRAFT")).toBe("Außerkrafttreten")
  })
})
