package de.bund.digitalservice.ris.norms.application.exception;

import de.bund.digitalservice.ris.norms.utils.exceptions.NormsAppException;
import java.net.URI;
import java.util.Map;
import lombok.Getter;

/** The given file is not a xml file, while a xml file was expected. */
@Getter
public class NotAXmlFileException extends RuntimeException implements NormsAppException {

  private final String fileName;
  private final String contentType;

  public NotAXmlFileException(String fileName, String contentType) {
    super(
      "The file %s is not a xml file but has the content type \"%s\"".formatted(
        fileName,
        contentType
      )
    );
    this.fileName = fileName;
    this.contentType = contentType;
  }

  @Override
  public URI getType() {
    return URI.create("/errors/not-a-xml-file");
  }

  @Override
  public String getTitle() {
    return "The provided file is not a xml file";
  }

  @Override
  public Map<String, Object> getProperties() {
    return Map.of("fileName", fileName, "contentType", contentType);
  }
}
