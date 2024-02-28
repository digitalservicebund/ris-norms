import { createRouter, createWebHistory } from "vue-router"

const routes = [
  {
    path: "/",
    name: "Home",
    redirect: { name: "AmendingLaws" },
  },
  {
    path: "/amending-laws",
    children: [
      {
        path: "",
        name: "AmendingLaws",
        component: () => import("@/views/AmendingLaws.vue"),
      },
      {
        /**
         * The regular expressions for the parts of the eli are based on the definitions from LDML.de 1.6 for elis
         * for "Verkündungsfassungen und Neufassungen"
         *
         * It was not possible to put the eli in just one combined path parameter as vue-router replaces slashes in
         * path parameters automatically by %2F when routing to a new route.
         */
        path: `eli/:eliJurisdiction(bund)/:eliAgent(bgbl-1|bgbl-2|banz-at)/:eliYear([12][0-9]{3})/:eliNaturalIdentifier(s[0-9]+[a-zäöüß]*|[0-9]+)/:eliPointInTime([12][0-9]{3}-[0-9]{2}-[0-9]{2})/:eliVersion([0-9]+)/:eliLanguage(deu)/:eliSubtype(regelungstext-[0-9]+|offenestruktur-[0-9]+|vereinbarung-[0-9]+|bekanntmachungstext-[0-9]+|externesdokument-[0-9]+|rechtsetzungsdokument-[0-9]+)`,
        component: () => import("@/views/AmendingLaw.vue"),
        children: [
          {
            path: "",
            name: "AmendingLaw",
            redirect: { name: "AmendingLawArticles" },
          },
          {
            path: "affected-documents",
            name: "AmendingLawAffectedDocuments",
            component: () => import("@/views/AffectedDocuments.vue"),
          },
          {
            path: "affected-documents/:eid",
            children: [
              {
                path: "",
                name: "AmendingLawAffectedDocument",
                redirect: { name: "AmendingLawAffectedDocumentEdit" },
              },
              {
                path: "edit",
                name: "AmendingLawAffectedDocumentEdit",
                component: () => import("@/views/EditAffectedDocument.vue"),
              },
            ],
          },
          {
            path: "articles",
            children: [
              {
                path: "",
                name: "AmendingLawArticles",
                component: () => import("@/views/Articles.vue"),
              },
              {
                /**
                 * The regular expressions for the eId is based on the definitions from LDML.de 1.6 (Section 9.2.11.63, eIdLiterals.einzelvorschrift)
                 *
                 * The expression only matches eIds that represent articles or paragraphs.
                 *
                 * All groups have been converted to non-capturing groups and all closing ) have been escaped. This is as the vue-router otherwise has problems parsing the RegEx.
                 */
                path: ":eid((?:(?:[a-zäöüß0-9]+-[a-zäöüß0-9]+(?:\\.[azäöüß0-9]+\\)*\\)_\\)*(?:(?:para|art\\)+-[a-zäöüß0-9]+(?:\\.[azäöüß0-9]+\\)*\\))",
                children: [
                  {
                    path: "edit",
                    name: "AmendingLawArticleEditor",
                    component: () =>
                      import("@/views/AmendingLawArticleEditor.vue"),
                  },
                ],
              },
            ],
          },
        ],
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
