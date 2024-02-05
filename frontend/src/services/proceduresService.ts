export interface Procedure {
  eli: string
  printAnnouncementGazette: string
  printAnnouncementYear: number
  printAnnouncementNumber: number
  printAnnouncementPage: number
  publicationDate: Date
  fna: string
}

export async function getProcedures(): Promise<Procedure[]> {
  return [
    {
      eli: "eli/bund/bgbl-1/1964/s593/regelungstext-1",
      printAnnouncementGazette: "BGBl I",
      printAnnouncementYear: 1964,
      printAnnouncementNumber: 7,
      printAnnouncementPage: 593,
      publicationDate: new Date("2023-03-12"),
      fna: "711-1-1",
    },
    {
      eli: "eli/bund/bgbl-1/1982/s22/regelungstext-1",
      printAnnouncementGazette: "BGBl II",
      printAnnouncementYear: 1982,
      printAnnouncementNumber: 3,
      printAnnouncementPage: 22,
      publicationDate: new Date("2024-01-01"),
      fna: "711-0-333",
    },
  ]
}
