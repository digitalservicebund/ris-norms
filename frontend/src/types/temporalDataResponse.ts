/** Describes temporal data (e.g. for life cycle events) or a norm. */
export interface TemporalDataResponse {
  /** ISO-formatted date string. */
  date: string

  /** ID of the event ref in the LDML document. */
  eventRefEid: string
}
