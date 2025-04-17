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
      props: { textContent: "No verkuendungen available" },
      slots: { recommendedAction: "Add new verkuendungen" },
    })

    expect(screen.getByText("No verkuendungen available")).toBeInTheDocument()
    expect(screen.getByText("Add new verkuendungen")).toBeInTheDocument()
  })

  it("should render the icon", () => {
    render(RisEmptyState, {
      props: {
        textContent: "No verkuendungen available",
        icon: markRaw(UploadFileOutlineRounded),
      },
    })

    expect(screen.getByTestId("empty-state-icon")).toBeInTheDocument()
  })

  it("should detect the simple variant based on the props", () => {
    render(RisEmptyState, {
      props: { textContent: "No verkuendungen available" },
    })

    expect(screen.getByTestId("empty-state")).toHaveAttribute(
      "data-variant",
      "simple",
    )
  })

  it("should detect the extended variant based on the props", () => {
    render(RisEmptyState, {
      props: {
        textContent: "No verkuendungen available",
        icon: markRaw(UploadFileOutlineRounded),
      },
      slots: { recommendedAction: "Add new verkuendungen" },
    })

    expect(screen.getByTestId("empty-state")).toHaveAttribute(
      "data-variant",
      "extended",
    )
  })
})
