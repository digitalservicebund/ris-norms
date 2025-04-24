package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.Map;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectSerializer;

/**
 * Json Serializer for {@link VerkuendungStatusErrorResponseSchema}
 */
@JsonComponent
public class VerkuendungStatusErrorResponseSchemaSerializer
  extends JsonObjectSerializer<VerkuendungStatusErrorResponseSchema> {

  @Override
  protected void serializeObject(
    VerkuendungStatusErrorResponseSchema value,
    JsonGenerator gen,
    SerializerProvider provider
  ) throws IOException {
    // first write all the data from the error details
    JsonElement element = JsonParser.parseString(value.detail());
    for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().asMap().entrySet()) {
      if (entry.getValue().isJsonPrimitive()) {
        gen.writeStringField(entry.getKey(), entry.getValue().getAsString());
      } else {
        gen.writeObjectField(entry.getKey(), entry.getValue());
      }
    }

    // and then also add the status, so it overwrites any fields that might have also been called status
    gen.writeObjectField("status", value.status());
  }
}
