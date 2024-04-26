import RisAmendingLawInfoHeaderInfoHeader from "@/components/amendingLaws/RisAmendingLawInfoHeader.vue"
import { Norm } from "@/types/norm"
import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"

describe("RisAmendingLawInfoHeaderInfoHeader", () => {
  test("renders heading and subtitle of printed announced amending law", () => {
    const amendingLaw: Norm = {
      eli: "eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1",
      printAnnouncementGazette: "BGBl. I",
      digitalAnnouncementMedium: undefined,
      frbrDateVerkuendung: "2017-03-15",
      printAnnouncementPage: "419",
      digitalAnnouncementEdition: undefined,
      title: "Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
    }

    render(RisAmendingLawInfoHeaderInfoHeader, {
      props: { amendingLaw },
    })

    expect(screen.getByText("BGBl. I 2017 S. 419")).toBeInTheDocument()

    expect(
      screen.getByText(
        "Entwurf eines Zweiten Gesetzes zur Änderung des Vereinsgesetzes",
      ),
    ).toBeInTheDocument()
  })

  test("renders heading and subtitle of digitally announced amending law", () => {
    const amendingLaw: Norm = {
      eli: "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-1",
      printAnnouncementGazette: undefined,
      digitalAnnouncementMedium: "BGBl. I",
      frbrDateVerkuendung: "2023-12-29",
      printAnnouncementPage: undefined,
      digitalAnnouncementEdition: "413",
      title: "Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts",
    }

    render(RisAmendingLawInfoHeaderInfoHeader, {
      props: { amendingLaw },
    })

    expect(screen.getByText("BGBl. I 2023 Nr. 413")).toBeInTheDocument()

    expect(
      screen.getByText(
        "Gesetz zum ersten Teil der Reform des Nachrichtendienstrechts",
      ),
    ).toBeInTheDocument()
  })
})
