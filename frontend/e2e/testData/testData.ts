import { AmendingLaw } from "@/services/amendingLawsService"

export const amendingLaws: AmendingLaw[] = [
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
        eli: "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1",
        title: "Änderung des Vereinsgesetzes",
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
        eli: "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
        title: "Änderung des Bundesverfassungsschutzgesetzes",
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
