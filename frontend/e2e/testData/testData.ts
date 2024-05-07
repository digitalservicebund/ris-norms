import { Article } from "@/types/article"
import { Norm } from "@/types/norm"

export const amendingLaws: (Norm & {
  articles: Article[]
  targetLaws: (Norm & { zf0Eli: string })[]
})[] = [
  {
    eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
    frbrName: "BGBl. I",
    frbrDateVerkuendung: "2017-03-15",
    frbrNumber: "s419",
    title: "Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
    articles: [
      {
        enumeration: "1",
        eid: "hauptteil-1_art-1",
        title: "Änderung des Vereinsgesetzes",
        affectedDocumentEli: "",
        affectedDocumentZf0Eli: "",
      },
    ],
    targetLaws: [
      {
        eli: "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1",
        title: "Gesetz zur Regelung des öffentlichen Vereinsrechts",
        fna: "754-28-1",
        shortTitle: "Vereinsgesetz",
        zf0Eli: "eli/bund/bgbl-1/1964/s593/2017-03-15/1/deu/regelungstext-1",
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
        enumeration: "1",
        eid: "hauptteil-1_art-1",
        title: "Änderung des Bundesverfassungsschutzgesetzes",
        affectedDocumentEli: "",
        affectedDocumentZf0Eli: "",
      },
    ],
    targetLaws: [
      {
        eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        title:
          "Gesetz über die Zusammenarbeit des Bundes und der Länder in Angelegenheiten des Verfassungsschutzes und über das Bundesamt für Verfassungsschutz",
        fna: "210-5",
        shortTitle: "Bundesverfassungsschutzgesetz",
        zf0Eli: "eli/bund/bgbl-1/1990/s2954/2023-12-29/1/deu/regelungstext-1",
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
