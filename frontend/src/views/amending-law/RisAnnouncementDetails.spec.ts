import { render, screen } from "@testing-library/vue"
import { describe, expect, it } from "vitest"
import RisAnnouncementDetails from "./RisAnnouncementDetails.vue"

describe("risAnnouncementDetails", () => {
  it("should render the title", () => {
    render(RisAnnouncementDetails, {
      props: {
        title: "Test Announcement Title",
      },
    })
    expect(screen.getByText("Test Announcement Title")).toBeInTheDocument()
  })

  it("should render all announcement details correctly", () => {
    render(RisAnnouncementDetails, {
      props: {
        title: "Test Announcement",
        veroeffentlichungsdatum: "2025-02-27",
        ausfertigungsdatum: "2025-02-24",
        datenlieferungsdatum: "2025-02-24T08:12:00Z",
        fna: "8052-5, 860-5, 2030-2-30-2, 51-1-23",
      },
    })

    expect(screen.getByText("Veröffentlichungsdatum")).toBeInTheDocument()
    expect(screen.getByText("27.02.2025")).toBeInTheDocument()

    expect(screen.getByText("Ausfertigungsdatum")).toBeInTheDocument()
    expect(screen.getByText("24.02.2025")).toBeInTheDocument()

    expect(screen.getByText("Datenlieferungsdatum")).toBeInTheDocument()
    expect(screen.getByText("24.02.2025, 08:12")).toBeInTheDocument()

    expect(screen.getByText("FNA")).toBeInTheDocument()
    expect(
      screen.getByText("8052-5, 860-5, 2030-2-30-2, 51-1-23"),
    ).toBeInTheDocument()
  })
})
