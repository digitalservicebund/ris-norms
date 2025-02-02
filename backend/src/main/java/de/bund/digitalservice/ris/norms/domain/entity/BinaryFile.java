package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import java.util.Arrays;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a binary file of a norm in LDML.de.
 */
@Getter
@AllArgsConstructor
public class BinaryFile {

  private final DokumentManifestationEli dokumentManifestationEli;
  private final byte[] content;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BinaryFile that = (BinaryFile) o;
    return (
      Objects.equals(dokumentManifestationEli, that.dokumentManifestationEli) &&
      Arrays.equals(content, that.content)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(dokumentManifestationEli, Arrays.hashCode(content));
  }
}
