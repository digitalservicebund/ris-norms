import type { ZeitgrenzeArt } from "@/types/zeitgrenze"

/**
 * Returns a human-readable label for the Art of a Zeitgrenze.
 *
 * @param art Art of the Zeitgrenze
 * @returns Human-readable label based on the Art
 */
export function getZeitgrenzeArtLabel(art: ZeitgrenzeArt) {
  switch (art) {
    case "AUSSERKRAFT":
      return "Außerkrafttreten"
    case "INKRAFT":
      return "Inkrafttreten"
  }
}
