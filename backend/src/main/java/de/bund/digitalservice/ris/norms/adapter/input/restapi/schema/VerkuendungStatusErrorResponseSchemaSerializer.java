package de.bund.digitalservice.ris.norms.adapter.input.restapi.schema;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;

/**
 * Json Serializer for {@link VerkuendungStatusErrorResponseSchema}
 */
@JsonComponent
public class VerkuendungStatusErrorResponseSchemaSerializer
  extends JsonSerializer<VerkuendungStatusErrorResponseSchema> {

  @Override
  public void serialize(
    VerkuendungStatusErrorResponseSchema value,
    JsonGenerator gen,
    SerializerProvider serializers
  ) throws IOException {
    // we want to add the status field to the json object string already stored in the details
    JsonElement element = JsonParser.parseString(value.detail());
    JsonObject jsonObject = element.getAsJsonObject();

    jsonObject.addProperty("status", value.status());

    gen.writeRaw(jsonObject.toString());
  }
}
