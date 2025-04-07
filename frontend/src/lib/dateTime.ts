function format(date: Date | string, opts: Intl.DateTimeFormatOptions): string {
  const dateObj = typeof date === "string" ? new Date(date) : date
  if (!Number.isFinite(dateObj.getTime())) return "Ungültiges Datum"
  return dateObj.toLocaleDateString("de-DE", opts)
}

/**
 * Takes a date or a string that can be parsed as a date and formats it as
 * a German date (DD.MM.YYYY).
 *
 * @param date Input date or string
 * @returns Formatted date
 */
export function formatDate(date: Date | string): string {
  return format(date, { day: "2-digit", month: "2-digit", year: "numeric" })
}

/**
 * Takes a date or a string that can be parsed as a date and formats it as
 * a German date and time (DD.MM.YYYY HH:MM).
 *
 * @param date Input date or string
 * @returns Formatted date and time
 */
export function formatDateTime(date: Date | string): string {
  return format(date, {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  })
}
