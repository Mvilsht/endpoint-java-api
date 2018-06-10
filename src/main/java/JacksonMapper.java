import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonMapper implements Mapper{

     private final static ObjectMapper MAPPER = new ObjectMapper();

     static {
         MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
     }

    public String writeValueAsString(Object value) throws JsonProcessingException {
         return MAPPER.writeValueAsString(value);
    }

    public <T> T readValue(String content, Class<T> valueType) throws IOException{
        return MAPPER.readValue(content, valueType);
    }

}
