import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import UploadFileOutlineRounded from "~icons/ic/baseline-upload-file"
import RisEmptyState from "./RisEmptyState.vue"
import { markRaw } from "vue"

describe("RisEmptyState", () => {
  test("should render text content", () => {
    render(RisEmptyState, {
      props: { textContent: "Hello, world!" },
    })

    expect(screen.getByText("Hello, world!")).toBeInTheDocument()
  })

  test("should render the recommended action", () => {
    render(RisEmptyState, {
      props: { textContent: "No announcements available" },
      slots: { "recommended-action": "Add new announcements" },
    })

    expect(screen.getByText("No announcements available")).toBeInTheDocument()
    expect(screen.queryByText("Add new announcements")).toBeInTheDocument()
  })

  test("should render the icon", () => {
    const { container } = render(RisEmptyState, {
      props: {
        textContent: "No announcements available",
        icon: markRaw(UploadFileOutlineRounded),
      },
    })

    expect(container.querySelector("svg")).toBeInTheDocument()
  })

  test("should detect the simple variant based on the props", () => {
    render(RisEmptyState, {
      props: { textContent: "No announcements available" },
    })

    expect(screen.getByTestId("empty-state")).toHaveAttribute(
      "data-variant",
      "simple",
    )
  })

  test("should detect the extended variant based on the props", () => {
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
