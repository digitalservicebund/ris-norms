package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import java.io.IOException;
import org.apache.tika.exception.TikaException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class MediaTypeServiceTest {

  MediaTypeService mediaTypeService = new MediaTypeService();

  MediaTypeServiceTest() throws TikaException, IOException {}

  @Test
  void detectMediaTypeInputStream() throws IOException {
    var mediaType = mediaTypeService.detectMediaType(
      Fixtures
        .getResource("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/1964-08-05/regelungstext-1.xml")
        .openStream()
    );
    assertThat(mediaType).contains(MediaType.APPLICATION_XML);
  }

  @Test
  void detectMediaTypeFileName() {
    var mediaType = mediaTypeService.detectMediaType("regelungstext-1.xml");
    assertThat(mediaType).contains(MediaType.APPLICATION_XML);
  }
}
