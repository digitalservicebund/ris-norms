import type { Article } from "@/types/article"
import type { Norm } from "@/types/norm"

export const amendingLaws: (Norm & {
  articles: Article[]
})[] = [
  {
    eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
    frbrName: "BGBl. I",
    frbrDateVerkuendung: "2017-03-15",
    frbrNumber: "s419",
    title: "Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
    articles: [
      {
        enumeration: "Artikel 1",
        eid: "hauptteil-1_art-1",
        title: "Änderung des Vereinsgesetzes",
      },
    ],
  },
  {
    eli: "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
    frbrName: "BGBl. I",
    frbrDateVerkuendung: "2023-12-29",
    frbrNumber: "413",
    title: "Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts",
    articles: [
      {
        enumeration: "Artikel 1",
        eid: "hauptteil-1_art-1",
        title: "Änderung des Bundesverfassungsschutzgesetzes",
      },
    ],
  },
]

export function getExpectedHeading(amendingLaw: Norm): string {
  let expectedHeading = ""
  const publicationYear = amendingLaw.frbrDateVerkuendung?.substring(0, 4)

  if (amendingLaw?.frbrNumber?.startsWith("s")) {
    expectedHeading = `${amendingLaw.frbrName} ${publicationYear} S. ${amendingLaw.frbrNumber?.replace("s", "")}`
  } else {
    expectedHeading = `${amendingLaw.frbrName} ${publicationYear} Nr. ${amendingLaw.frbrNumber}`
  }

  return expectedHeading
}
