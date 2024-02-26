import { createRouter, createWebHistory } from "vue-router"

const routes = [
  {
    path: "/",
    name: "Home",
    redirect: { name: "AmendingLaws" },
  },
  {
    path: "/amendinglaws",
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
            redirect: { name: "AmendingLawArticleOverview" },
          },
          {
            path: "article-overview",
            name: "AmendingLawArticleOverview",
            component: () => import("@/views/ArticleOverview.vue"),
          },
          {
            path: "affected-standards",
            name: "AmendingLawAffectedStandards",
            component: () => import("@/views/AffectedStandards.vue"),
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
