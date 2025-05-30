package de.bund.digitalservice.ris.norms.domain.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.w3c.dom.Element;

/**
 * Metadata element indicating a norm is gegenstandlos
 */
@AllArgsConstructor
public class Gegenstandlos {

  public static final Namespace NAMESPACE = Namespace.METADATEN_RECHTSETZUNGSDOKUMENT;
  public static final String TAG_NAME = "gegenstandlos";

  private final Element element;

  LocalDate getSinceDate() {
    return LocalDate.parse(element.getAttribute("seit"));
  }

  void setSinceDate(final String date) {
    element.setAttribute("seit", date);
  }
}
