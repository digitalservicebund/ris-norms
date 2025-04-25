// This file is for global initializations in preparation for running vitest
// unit tests.

import "@testing-library/jest-dom"
import { config } from "@vue/test-utils"
import PrimeVue from "primevue/config"
import { vi } from "vitest"
import { useNamespaces } from "xpath"

// The DOM implementation used by our unit tests (jsdom) does not have a good enough xpath support for our tests.
// Therefore, we need to use a different library to evaluate the xpath.
vi.mock("@/lib/xml", async (importOriginal) => ({
  /* eslint-disable-next-line @typescript-eslint/consistent-type-imports -- No other way of getting the type of the entire module */
  ...(await importOriginal<typeof import("@/lib/xml")>()),
  evaluateXPathOnce: (xpath: string, node: Node) =>
    useNamespaces({
      akn: "http://Inhaltsdaten.LegalDocML.de/1.7.2/",
    })(xpath, node, true),
  evaluateXPath: (xpath: string, node: Node) =>
    useNamespaces({
      akn: "http://Inhaltsdaten.LegalDocML.de/1.7.2/",
    })(xpath, node, false),
}))

// JSDom doesn't implement layout-related functionality such as scrollIntoView. This
// is used in many places (e.g. wherever the Preview is used), so mocking it globally.
Element.prototype.scrollIntoView ??= vi.fn()

// Enable PrimeVue plugin because we need that in many tests
config.global.plugins = [PrimeVue]

// window.matchMedia API not available in vitest but needed by PrimeVue Components (select)
window.matchMedia =
  window.matchMedia ||
  function () {
    return {
      addEventListener: vi.fn(),
      removeEventListener: vi.fn(),
    }
  }
