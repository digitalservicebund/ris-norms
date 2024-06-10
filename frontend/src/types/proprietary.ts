/**
 * Encapsulates metadata that is found in the proprietary section of the
 * document. This includes both metadata that is proprietary to the LDML.de
 * standard and to DigitalService.
 */
export type Proprietary = {
  /** FNA (Fundstellennachweis A) of a norm. */
  fna: string
}
