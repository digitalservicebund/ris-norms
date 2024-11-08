import { render, screen } from "@testing-library/vue"
import { describe, expect, it } from "vitest"
import { markRaw } from "vue"
import UploadFileOutlineRounded from "~icons/ic/baseline-upload-file"
import RisEmptyState from "./RisEmptyState.vue"

describe("risEmptyState", () => {
  it("should render text content", () => {
    render(RisEmptyState, {
      props: { textContent: "Hello, world!" },
    })

    expect(screen.getByText("Hello, world!")).toBeInTheDocument()
  })

  it("should render the recommended action", () => {
    render(RisEmptyState, {
      props: { textContent: "No announcements available" },
      slots: { "recommended-action": "Add new announcements" },
    })

    expect(screen.getByText("No announcements available")).toBeInTheDocument()
    expect(screen.getByText("Add new announcements")).toBeInTheDocument()
  })

  it("should render the icon", () => {
    render(RisEmptyState, {
      props: {
        textContent: "No announcements available",
        icon: markRaw(UploadFileOutlineRounded),
      },
    })

    expect(screen.getByTestId("empty-state-icon")).toBeInTheDocument()
  })

  it("should detect the simple variant based on the props", () => {
    render(RisEmptyState, {
      props: { textContent: "No announcements available" },
    })

    expect(screen.getByTestId("empty-state")).toHaveAttribute(
      "data-variant",
      "simple",
    )
  })

  it("should detect the extended variant based on the props", () => {
    render(RisEmptyState, {
      props: {
        textContent: "No announcements available",
        icon: markRaw(UploadFileOutlineRounded),
      },
      slots: { "recommended-action": "Add new announcements" },
    })

    expect(screen.getByTestId("empty-state")).toHaveAttribute(
      "data-variant",
      "extended",
    )
  })
})
