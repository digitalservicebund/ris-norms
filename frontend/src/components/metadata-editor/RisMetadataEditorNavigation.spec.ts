import { beforeEach, describe, expect, it, vi } from "vitest"
import { render, screen, within } from "@testing-library/vue"
import { defineComponent, ref } from "vue"
import type { TocItem } from "@/types/toc"
import { createRouter, createWebHashHistory, type Router } from "vue-router"
import { userEvent } from "@testing-library/user-event"
import { NormExpressionEli } from "@/lib/eli/NormExpressionEli"

const tocTestData = [
  {
    id: "hauptteil-n1_buch-n2",
    marker: "2. Buch",
    heading: "Beispiele für Strukturen",
    type: "book",
    hasEingebundeneStammform: false,
    children: [
      {
        id: "hauptteil-n1_buch-n2_kapitel-n1",
        marker: "",
        heading: "Implementierung",
        type: "chapter",
        hasEingebundeneStammform: false,
        children: [
          {
            id: "art-z3",
            marker: "§ 3",
            heading: "Implementierung der neuen Strukturen",
            type: "article",
            hasEingebundeneStammform: false,
            children: [],
          },
          {
            id: "art-z4",
            marker: "§ 4",
            heading: "Finanzielle Unterstützung",
            type: "article",
            hasEingebundeneStammform: false,
            children: [],
          },
          {
            id: "art-z5",
            marker: "§ 5",
            heading: "Evaluierung und Anpassung",
            type: "article",
            hasEingebundeneStammform: false,
            children: [],
          },
          {
            id: "art-z5a",
            marker: "§ 5a",
            heading: "Stammverweis",
            type: "article",
            hasEingebundeneStammform: false,
            children: [],
          },
        ],
      },
    ],
  },
  {
    id: "hauptteil-n1_buch-n3",
    marker: "3. Buch",
    heading: "Beispiele Teil I",
    type: "book",
    hasEingebundeneStammform: false,
    children: [
      {
        id: "art-z6",
        marker: "§ 6",
        heading: "Beispielartikel",
        type: "article",
        hasEingebundeneStammform: false,
        children: [],
      },
    ],
  },
]

describe("risMetadataEditorNavigation", () => {
  let global = {}
  let router: Router

  beforeEach(async () => {
    vi.resetModules()

    const component = defineComponent({ template: "<div></div>" })

    router = createRouter({
      history: createWebHashHistory(),
      routes: [
        { path: "/rahmen/:eid", name: "RahmenEditor", component },
        { path: "/element/:eid", name: "ElementEditor", component },
        {
          path: "/outline-element/:eid",
          name: "OutlineElementEditor",
          component,
        },
      ],
    })

    await router.push({ name: "RahmenEditor", params: { eid: "art-z3" } })
    await router.isReady()

    global = { plugins: [router] }
  })

  it("should render", async () => {
    vi.doMock("@/services/tocService", () => ({
      useGetNormToc: () => ({
        data: ref<TocItem[]>(tocTestData),
        error: ref(null),
        isFetching: ref(false),
      }),
    }))

    const { default: RisMetadataEditorNavigation } = await import(
      "./RisMetadataEditorNavigation.vue"
    )

    render(RisMetadataEditorNavigation, {
      global,
      props: {
        routeNameEditorElement: "ElementEditor",
        routeNameEditorOutlineElement: "OutlineElementEditor",
        routeNameEditorRahmen: "RahmenEditor",
        normExpressionEli: NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu",
        ),
        selectedEId: "art-z3",
      },
    })

    const toc = screen.getByRole("tree")

    expect(
      within(toc).getByRole("treeitem", {
        name: "2. Buch",
      }),
    ).toBeInTheDocument()

    expect(
      within(toc).getByRole("treeitem", {
        name: "3. Buch",
      }),
    ).toBeInTheDocument()

    expect(
      within(toc).getByRole("treeitem", {
        name: "§ 3",
      }),
    ).toBeInTheDocument()

    // still collapsed so not rendered
    expect(
      within(toc).queryByRole("treeitem", {
        name: "§ 6",
      }),
    ).not.toBeInTheDocument()
  })

  it("should navigate", async () => {
    const user = userEvent.setup()

    vi.doMock("@/services/tocService", () => ({
      useGetNormToc: () => ({
        data: ref<TocItem[]>(tocTestData),
        error: ref(null),
        isFetching: ref(false),
      }),
    }))

    const { default: RisMetadataEditorNavigation } = await import(
      "./RisMetadataEditorNavigation.vue"
    )

    render(RisMetadataEditorNavigation, {
      global,
      props: {
        routeNameEditorElement: "ElementEditor",
        routeNameEditorOutlineElement: "OutlineElementEditor",
        routeNameEditorRahmen: "RahmenEditor",
        normExpressionEli: NormExpressionEli.fromString(
          "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu",
        ),
        selectedEId: "art-z3",
      },
    })

    await user.click(
      screen.getByRole("link", {
        name: "2. Buch",
      }),
    )
    expect(router.currentRoute.value.name).toBe("OutlineElementEditor")

    await user.click(
      screen.getByRole("link", {
        name: "Beispiele für Strukturen",
      }),
    )
    expect(router.currentRoute.value.name).toBe("OutlineElementEditor")

    await user.click(
      screen.getByRole("link", {
        name: "§ 3",
      }),
    )
    expect(router.currentRoute.value.name).toBe("ElementEditor")

    await user.click(
      screen.getByRole("link", {
        name: "Rahmen",
      }),
    )
    expect(router.currentRoute.value.name).toBe("RahmenEditor")
  })
})
