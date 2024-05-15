import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisEmptyState from "./RisEmptyState.vue"

describe("RisEmptyState", () => {
  test("should render text content", () => {
    render(RisEmptyState, {
      props: { textContent: "Hello, world!" },
    })
    expect(screen.getByText("Hello, world!")).toBeInTheDocument()
  })
})
