// This file is for global initializations in preparation for running vitest
// unit tests.

import "@testing-library/jest-dom"
import { vi } from "vitest"
import { useNamespaces } from "xpath"

// The DOM implementation used by our unit tests (jsdom) does not have a good enough xpath support for our tests.
// Therefore, we need to use a different library to evaluate the xpath.
vi.mock("@/services/xmlService", async (importOriginal) => ({
  ...(await importOriginal<typeof import("@/services/xmlService")>()),
  evaluateXPath: (xpath: string, node: Node) =>
    useNamespaces({
      akn: "http://Inhaltsdaten.LegalDocML.de/1.6/",
    })(xpath, node, true),
}))
