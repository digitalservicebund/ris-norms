import type { ErrorResponse } from "@/types/errorResponse"
import { render, screen } from "@testing-library/vue"
import { afterEach, describe, expect, it, vi } from "vitest"
import { nextTick } from "vue"
import RisViewLayout from "./RisViewLayout.vue"

const { replaceMock } = vi.hoisted(() => ({
  replaceMock: vi.fn(),
}))

vi.mock("vue-router", () => ({
  useRouter: () => ({ replace: replaceMock }),
}))

vi.mock("@/lib/errorMessages", () => ({
  errorMessages: {
    __fallback__: () => "The fallback message",

    "/errors/foo": (e: ErrorResponse) => `Error of type ${e.type}`,

    "/errors/bar": (e: ErrorResponse<{ example: string }>) => ({
      title: "Bar",
      message: `Example: ${e.example}`,
      suggestion: "Try again",
    }),
  },
}))

describe("risViewLayout", () => {
  afterEach(() => {
    vi.resetAllMocks()
  })

  it("renders the slot content", () => {
    render(RisViewLayout, {
      slots: {
        default: "<div>Example</div>",
      },
    })

    expect(screen.getByText("Example")).toBeInTheDocument()
  })

  it("does not render a header if breadcrumbs and back destination are falsy", () => {
    render(RisViewLayout, {
      props: {
        breadcrumbs: undefined,
        headerBackDestination: undefined,
      },
      slots: {
        default: "<div>Example</div>",
      },
    })

    expect(screen.queryByRole("banner")).not.toBeInTheDocument()
  })

  it("renders a header if a back destination is set", () => {
    render(RisViewLayout, {
      props: {
        breadcrumbs: undefined,
        headerBackDestination: "history-back",
      },
      slots: {
        default: "<div>Example</div>",
      },
      global: {
        stubs: {
          RisHeader: { template: "<header></header>" },
        },
      },
    })

    expect(screen.getByRole("banner")).toBeInTheDocument()
  })

  it("renders breadcrumbs", () => {
    render(RisViewLayout, {
      props: {
        breadcrumbs: [],
        headerBackDestination: undefined,
      },
      slots: {
        default: "<div>Example</div>",
      },
      global: {
        stubs: {
          RisHeader: { template: "<header></header>" },
        },
      },
    })

    expect(screen.getByRole("banner")).toBeInTheDocument()
  })

  it("shows a loading state", () => {
    render(RisViewLayout, {
      props: {
        loading: true,
      },
      slots: {
        default: "<div>Example</div>",
      },
    })

    expect(screen.getByRole("status", { name: "Lädt..." })).toBeInTheDocument()
  })

  it("does not show the slot content while loading", () => {
    render(RisViewLayout, {
      props: {
        loading: true,
      },
      slots: {
        default: "<div>Example</div>",
      },
    })

    expect(screen.queryByText("Example")).not.toBeInTheDocument()
  })

  it("shows an error state", () => {
    render(RisViewLayout, {
      props: {
        errors: [{ type: "/errors/foo" }],
      },
      slots: {
        default: "<div>Example</div>",
      },
    })

    expect(screen.getByText("Error of type /errors/foo")).toBeInTheDocument()
  })

  it("does not show the slot content when an error state is visible", () => {
    render(RisViewLayout, {
      props: {
        errors: [{ type: "/errors/foo" }],
      },
      slots: {
        default: "<div>Example</div>",
      },
    })

    expect(screen.queryByText("Example")).not.toBeInTheDocument()
  })

  it("only shows the first of multiple errors", () => {
    render(RisViewLayout, {
      props: {
        errors: [{ type: "/errors/foo" }, { type: "/errors/bar" }],
      },
      slots: {
        default: "<div>Example</div>",
      },
    })

    expect(screen.getByText("Error of type /errors/foo")).toBeInTheDocument()
  })

  it("redirects when receiving a 404 error", async () => {
    render(RisViewLayout, {
      props: {
        errors: [{ status: 404 }],
      },
      slots: {
        default: "<div>Example</div>",
      },
    })

    await nextTick()
    expect(replaceMock).toHaveBeenCalled()
  })

  it("does not redirect on 404 if the behavior is disabled", async () => {
    render(RisViewLayout, {
      props: {
        errors: [{ status: 404 }],
        redirectOn404: false,
      },
      slots: {
        default: "<div>Example</div>",
      },
    })

    await nextTick()
    expect(replaceMock).not.toHaveBeenCalled()
  })
})
