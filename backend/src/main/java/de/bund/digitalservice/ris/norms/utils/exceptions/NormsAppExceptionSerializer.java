package de.bund.digitalservice.ris.norms.utils.exceptions;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectSerializer;

/**
 * Custom json serializer for {@link NormsAppException} that only includes fields that are safe to publish.
 * This creates the same structure a serialized {@link org.springframework.http.ProblemDetail} creates.
 */
@JsonComponent
@Slf4j
public class NormsAppExceptionSerializer extends JsonObjectSerializer<NormsAppException> {

  @Override
  protected void serializeObject(
    NormsAppException normsAppException,
    JsonGenerator jgen,
    SerializerProvider provider
  ) throws IOException {
    for (Map.Entry<String, Object> entry : normsAppException.getProperties().entrySet()) {
      jgen.writeFieldName(entry.getKey());
      provider
        .findValueSerializer(entry.getValue().getClass())
        .serialize(entry.getValue(), jgen, provider);
    }

    jgen.writeObjectField("detail", normsAppException.getMessage());
    jgen.writeObjectField("title", normsAppException.getTitle());
    jgen.writeObjectField("type", normsAppException.getType());
  }
}
