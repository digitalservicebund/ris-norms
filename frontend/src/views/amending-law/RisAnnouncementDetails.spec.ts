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

  it("should render metadata items correctly", () => {
    render(RisAnnouncementDetails, {
      props: {
        title: "Test Announcement",
        metaData: [
          { label: "Veröffentlichungsdatum", value: "01.01.2025" },
          { label: "FNA", value: "123-456-789" },
        ],
      },
    })

    expect(screen.getByText("Veröffentlichungsdatum")).toBeInTheDocument()
    expect(screen.getByText("01.01.2025")).toBeInTheDocument()
    expect(screen.getByText("FNA")).toBeInTheDocument()
    expect(screen.getByText("123-456-789")).toBeInTheDocument()
  })

  it("should handle missing metadata gracefully", () => {
    render(RisAnnouncementDetails, {
      props: {
        title: "Test Announcement",
        metaData: undefined,
      },
    })

    // Ensure it doesn't break when metadata is missing
    expect(screen.getByText("Test Announcement")).toBeInTheDocument()
  })
})
