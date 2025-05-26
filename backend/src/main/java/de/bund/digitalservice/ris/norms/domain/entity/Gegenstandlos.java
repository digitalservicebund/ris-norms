package de.bund.digitalservice.ris.norms.domain.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.w3c.dom.Element;

/**
 * Metadata element indicating a norm is gegenstandlos
 */
@AllArgsConstructor
public class Gegenstandlos {

  // TODO: (Malte Laukötter, 2025-05-13) ldml_de 1.8 -> the namespace will change
  public static final Namespace NAMESPACE = Namespace.METADATEN;
  public static final String TAG_NAME = "gegenstandlos";

  private final Element element;

  LocalDate getSinceDate() {
    return LocalDate.parse(element.getAttribute("seit"));
  }

  void updateSinceDate(final String date) {
    element.setAttribute("seit", date);
  }
}
