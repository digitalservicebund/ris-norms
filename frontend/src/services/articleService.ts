import { apiFetch } from "@/services/apiService"
import { Article } from "@/types/article"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"

/**
 * Load the list of articles within a specific law. By default, this returns
 * the list of all articles. You can use the options to narrow this list based
 * on pending changes in the law, e.g. to only return articles that are going
 * to be changed by a specific amending law, or only return articles changed
 * at a specific time boundary.
 *
 * @param eli ELI of the law we want to get the articles from
 * @param options Optional additional filters and queries
 */
export async function getArticlesByEli(
  eli: string,
  options?: {
    /** The eId of the time boundary at which the article was changed. */
    amendedAt?: string
    /** The ELI of the amending law changing the article. */
    amendedBy?: string
  },
): Promise<Article[]> {
  return apiFetch<Article[]>(`/norms/${eli}/articles`, {
    query: { ...options },
  })
}

/**
 * Load an article within a specific law from the API.
 *
 * @param identifier The information required for identifying the article
 */
export async function getArticleByEliAndEid({
  eli,
  eid,
}: LawElementIdentifier): Promise<Article> {
  return apiFetch<Article>(`/norms/${eli}/articles/${eid}`)
}

/**
 * Load the xml version of an article within a specific law from the API.
 *
 * @param identifier The information required for identifying the article
 */
export async function getArticleXmlByEliAndEid({
  eli,
  eid,
}: LawElementIdentifier): Promise<string> {
  return apiFetch<string>(`/norms/${eli}/articles/${eid}`, {
    headers: {
      Accept: "application/xml",
    },
  })
}

/**
 * Load the rendered html version of an article within a specific law from the API.
 *
 * @param identifier The information required for identifying the article
 * @param at Date indicating which modifications should be applied before the HTML gets rendered and returned
 */
export async function getArticleRenderByEliAndEid(
  { eli, eid }: LawElementIdentifier,
  at?: Date,
): Promise<string> {
  return apiFetch<string>(`/norms/${eli}/articles/${eid}`, {
    query: {
      atIsoDate: at?.toISOString(),
    },
    headers: {
      Accept: "text/html",
    },
  })
}
