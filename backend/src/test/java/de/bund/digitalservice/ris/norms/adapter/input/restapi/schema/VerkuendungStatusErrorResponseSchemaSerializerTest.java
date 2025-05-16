package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

class VerkuendungStatusErrorResponseSchemaSerializerTest {

  @Test
  void itShouldSerialize() throws JsonProcessingException {
    var schema = new VerkuendungStatusErrorResponseSchema(
      "ERROR",
      """
      {
        "type": "/errors/job-run-failed",
        "title": "Tried to import a Normendokumentationspacket the max amount of times but failed",
        "detail": "detail message",
        "additionalProperty": "some-value",
        "additionalObjectProperty": {
          "some-key": "some-value",
          "some-key2": {
            "some-key": "some-value2"
          }
        },
        "additionalArrayProperty": [
          {
            "some-key": "some-value"
          },
          123
        ]
      }
      """
    );
    SimpleModule module = new SimpleModule();
    module.addSerializer(
      VerkuendungStatusErrorResponseSchema.class,
      new VerkuendungStatusErrorResponseSchemaSerializer()
    );
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(module);

    var result = mapper.writeValueAsString(schema);

    assertThat(result).isEqualToIgnoringWhitespace(
      """
      {
        "type": "/errors/job-run-failed",
        "title": "Tried to import a Normendokumentationspacket the max amount of times but failed",
        "detail": "detail message",
        "additionalProperty": "some-value",
        "additionalObjectProperty": {
          "some-key": "some-value",
          "some-key2": {
            "some-key": "some-value2"
          }
        },
        "additionalArrayProperty": [
          {
            "some-key": "some-value"
          },
          123
        ],
        "status": "ERROR"
      }
      """
    );
  }
}
