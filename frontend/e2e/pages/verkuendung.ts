import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import type { Zeitgrenze } from "@/types/zeitgrenze"
import type { ZielnormReference } from "@/types/zielnormReference"
import type { APIRequestContext } from "playwright/test"

/**
 * Sets the Zeitgrenzen for the specified Dokument and returns the list of
 * IDs of all Zeitgrenzen.
 *
 * @param eli Dokument to set the Zeitgrenzen on
 * @param zeitgrenzen new Zeitgrenzen
 * @param request API context used for submitting the request
 * @returns List of Zeitgrenzen IDs
 */
export async function setZeitgrenzen(
  eli: DokumentExpressionEli,
  zeitgrenzen: Zeitgrenze[],
  request: APIRequestContext,
): Promise<string[]> {
  return await request
    .put(`/api/v1/norms/${eli}/zeitgrenzen`, { data: zeitgrenzen })
    .then((response) => response.json())
    .then((zeitgrenzen: Zeitgrenze[]) => zeitgrenzen.map((i) => i.id))
}

/**
 * Delete all current Zielnormen references. Optionally replace them
 * with a list of new references.
 *
 * @param eli Norm to set the Zielnormen references on
 * @param zielnormReferences New Zielnormen references (if any)
 * @param request API context used for submitting the request
 */
export async function setZielnormReferences(
  eli: NormExpressionEli,
  zielnormReferences: ZielnormReference[] | null,
  request: APIRequestContext,
): Promise<void> {
  const existingReferences = await request
    .get(`/api/v1/norms/${eli}/zielnorm-references`)
    .then((response) => response.json())
    .then((refs: ZielnormReference[]) => refs.map((i) => i.eId))

  await request.delete(`/api/v1/norms/${eli}/zielnorm-references`, {
    data: existingReferences,
  })

  if (zielnormReferences?.length) {
    await request.post(`/api/v1/norms/${eli}/zielnorm-references`, {
      data: zielnormReferences,
    })
  }
}
