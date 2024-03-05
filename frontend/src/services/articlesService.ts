import { apiFetch } from "@/services/apiService"
import { AmendingLawArticle } from "@/types/domain"
import { LawElementIdentifier } from "@/types/lawElementIdentifier"

/**
 * Load an article within a specific law from the API.
 *
 * @param identifier The information required for identifying the article
 */
export async function getArticleByEliAndEid({
  eli,
  eid,
}: LawElementIdentifier): Promise<AmendingLawArticle> {
  return apiFetch<AmendingLawArticle>(`/amending-laws/${eli}/articles/${eid}`)
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
  return apiFetch<string>(`/amending-laws/${eli}/articles/${eid}`, {
    headers: {
      Accept: "application/xml",
    },
  })
}
