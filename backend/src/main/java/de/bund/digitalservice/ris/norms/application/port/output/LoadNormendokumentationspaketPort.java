package de.bund.digitalservice.ris.norms.application.port.output;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * Interface representing a port for loading the Normendokumentationspaket files.
 */
public interface LoadNormendokumentationspaketPort {
  /**
   * Loads a Normendokumentationspaket
   *
   * @param options with the processId for the Normendokumentationspaket
   * @return the data about the Normendokumentationspaket
   */
  Result loadNormendokumentationspaket(final Options options) throws IOException;

  /**
   * A record representing the command for loading of a Normendokumentationspaket.
   *
   * @param processId identifier for the Normendokumentationspaket
   */
  record Options(UUID processId) {}

  /**
   * A record representing the files of the Normendokumentationspaket.
   *
   * @param file as zip
   * @param signature as sig
   */
  record Result(byte[] file, byte[] signature) {
    @Override
    public boolean equals(Object o) {
      if (o == null || getClass() != o.getClass()) return false;
      Result result = (Result) o;
      return (
        Objects.deepEquals(file, result.file) && Objects.deepEquals(signature, result.signature)
      );
    }

    @Override
    public int hashCode() {
      return Objects.hash(Arrays.hashCode(file), Arrays.hashCode(signature));
    }

    @Override
    public String toString() {
      return (
        "Result{" + "file.length=" + file.length + ", signature.length=" + signature.length + '}'
      );
    }
  }
}
