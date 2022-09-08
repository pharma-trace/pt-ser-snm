package io.pharmatrace.ptsersnm.context.config;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
@Slf4j
public class CustomSerializer extends JsonSerializer<Json> {

    @Override
    public void serialize(Json value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        var text = value.asString();
        log.info("The raw json value from PostgresSQL JSON type:{}", text);
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(text);
        var node = gen.getCodec().readTree(parser);
        serializers.defaultSerializeValue(node, gen);
    }

}
