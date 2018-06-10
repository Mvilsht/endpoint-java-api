import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;

public interface Mapper {
    String writeValueAsString(Object value) throws JsonProcessingException;
    <T> T readValue(String content, Class<T> valueType) throws IOException, JsonParseException,JsonMappingException;
}
