import { AmendingLaw, AmendingLawArticle } from "@/types/domain"

export const amendingLaws: (AmendingLaw & {
  articles: AmendingLawArticle[]
})[] = [
  {
    eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
    printAnnouncementGazette: "BGBl. I",
    digitalAnnouncementMedium: undefined,
    publicationDate: "2017-03-15",
    printAnnouncementPage: "419",
    digitalAnnouncementEdition: undefined,
    title: "Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
    articles: [
      {
        enumeration: "1",
        eid: "hauptteil-1_art-1",
        title: "Änderung des Vereinsgesetzes",
        affectedDocumentEli: "",
      },
    ],
  },
  {
    eli: "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
    printAnnouncementGazette: undefined,
    digitalAnnouncementMedium: "BGBl. I",
    publicationDate: "2023-12-29",
    printAnnouncementPage: undefined,
    digitalAnnouncementEdition: "413",
    title: "Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts",
    articles: [
      {
        enumeration: "1",
        eid: "hauptteil-1_art-1",
        title: "Änderung des Bundesverfassungsschutzgesetzes",
        affectedDocumentEli: "",
      },
    ],
  },
]

export function getExpectedHeading(amendingLaw: AmendingLaw): string {
  let expectedHeading = ""
  const publicationYear = amendingLaw.publicationDate.substring(0, 4)

  if (amendingLaw?.printAnnouncementGazette) {
    expectedHeading = `${amendingLaw.printAnnouncementGazette} ${publicationYear} S. ${amendingLaw.printAnnouncementPage}`
  } else if (amendingLaw?.digitalAnnouncementEdition) {
    expectedHeading = `${amendingLaw.digitalAnnouncementMedium} ${publicationYear} Nr. ${amendingLaw.digitalAnnouncementEdition}`
  }

  return expectedHeading
}
