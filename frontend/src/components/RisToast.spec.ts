import type { ErrorToastPayload } from "@/composables/useToast"
import { userEvent } from "@testing-library/user-event"
import { render, screen } from "@testing-library/vue"
import { describe, expect, it, vi } from "vitest"
import RisToast from "./RisToast.vue"

function renderWithError(message: Partial<ErrorToastPayload>) {
  return render(RisToast, {
    global: {
      stubs: {
        Toast: {
          setup() {
            return { message }
          },
          template: `<div><slot name="message" v-bind="{ message }" /></div>`,
        },
      },
    },
  })
}

describe("risToast", () => {
  it("renders the title", () => {
    renderWithError({ summary: "test title" })
    expect(screen.getByText("test title")).toBeInTheDocument()
  })

  it("renders the message", () => {
    renderWithError({
      summary: "test title",
      detail: { title: "test title", message: "test message" },
    })

    expect(screen.getByText("test message")).toBeInTheDocument()
  })

  it("renders the suggestion", () => {
    renderWithError({
      summary: "test title",
      detail: { title: "test title", suggestion: "test suggestion" },
    })

    expect(screen.getByText("test suggestion")).toBeInTheDocument()
  })

  it("renders the trace ID", () => {
    renderWithError({
      summary: "test title",
      detail: { title: "test title", traceId: "4711" },
    })

    expect(screen.getByText("Trace-ID kopieren")).toBeInTheDocument()
  })

  it("sets the trace ID value", async () => {
    const user = userEvent.setup()
    const spy = vi.spyOn(navigator.clipboard, "writeText")
    renderWithError({
      summary: "test title",
      detail: { title: "test title", traceId: "4711" },
    })

    await user.click(
      screen.getByRole("button", {
        name: "Trace-ID in die Zwischenablage kopieren",
      }),
    )

    expect(spy).toHaveBeenCalledExactlyOnceWith("4711")
  })
})
