import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisEmptyState from "./RisEmptyState.vue"

describe("RisEmptyState", () => {
  test("should render text content for simple variant", () => {
    render(RisEmptyState, {
      props: { textContent: "Hello, world!" },
    })
    expect(screen.getByText("Hello, world!")).toBeInTheDocument()
  })

  test("should render text content and recommended action for extended variant", () => {
    render(RisEmptyState, {
      props: {
        textContent: "No announcements available",
        variant: "extended",
      },
      slots: {
        "recommended-action": "Add new announcements",
      },
    })
    expect(screen.getByText("No announcements available")).toBeInTheDocument()
    expect(screen.queryByText("Add new announcements")).toBeInTheDocument()
  })

  test("should not render recommended action slot if not provided", () => {
    render(RisEmptyState, {
      props: {
        textContent: "No announcements available",
        variant: "extended",
      },
    })
    expect(screen.queryByText("Add new announcements")).not.toBeInTheDocument()
  })
})
