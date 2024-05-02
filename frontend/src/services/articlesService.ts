import { apiFetch } from "@/services/apiService"
import { Article } from "@/types/article"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"

/**
 * Load the list of articles within a specific law.
 *
 * @param eli ELI of the law we want to get the articles from
 */
export async function getArticlesByEli(eli: string): Promise<Article[]> {
  return apiFetch<Article[]>(`/norms/${eli}/articles`)
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
 */
export async function getArticleRenderByEliAndEid({
  eli,
  eid,
}: LawElementIdentifier): Promise<string> {
  return apiFetch<string>(`/norms/${eli}/articles/${eid}`, {
    headers: {
      Accept: "text/html",
    },
  })
}
