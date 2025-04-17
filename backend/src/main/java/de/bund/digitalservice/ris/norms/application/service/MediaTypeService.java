package de.bund.digitalservice.ris.norms.application.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;

/**
 * Service for MediaTypes
 */
@Slf4j
@Service
public class MediaTypeService {

  TikaConfig tika;

  public MediaTypeService() throws TikaException, IOException {
    this.tika = new TikaConfig();
  }

  /**
   * Detect the MediaType of the file based on the content.
   * @param inputStream file-content to use for detecting the MediaType
   * @return the detected MediaType
   * @throws IOException when something fails while reading the InputStream
   */
  public Optional<MediaType> detectMediaType(InputStream inputStream) throws IOException {
    var type = tika.getDetector().detect(inputStream, new Metadata());
    log.debug("Detected MediaType: {}", type);
    return Optional.of(MediaType.parseMediaType(type.toString()));
  }

  /**
   * Detect the MediaType based on the filename
   * @param fileName the filename to use for the detection
   * @return the detected MediaType
   */
  public Optional<MediaType> detectMediaType(String fileName) {
    return MediaTypeFactory.getMediaType(fileName);
  }
}
