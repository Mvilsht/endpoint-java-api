//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.exceptions.UnirestException;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.Arrays;
//import java.util.List;
//
//public class RequestHandlerUnirest implements RequestHandler {
//
//    static {
//        final ObjectMapper MAPPER = new ObjectMapper();
//        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        Unirest.setObjectMapper(new com.mashape.unirest.http.ObjectMapper() {
//            public <T> T readValue(String value, Class<T> valueType) {
//                try {
//                    return MAPPER.readValue(value, valueType);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            public String writeValue(Object value) {
//                try {
//                    return MAPPER.writeValueAsString(value);
//                } catch (JsonProcessingException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//    }
//
//
//    public List<Category> sendRequest(URL url) {
//        try {
//            final HttpResponse<Category[]> httpResponse = Unirest.get(url.toString()).getBody().toString().asObject(Category[].class);
//            if (httpResponse.getStatus() != 200) {
//                System.err.println("BAD request. status: " + httpResponse.getStatus());
//                return null;
//            }
//            else {
//                //return Arrays.asList(httpResponse.getBody());
//
//            }
//        } catch (UnirestException e) {
//            System.err.println("fail;asdasdasda: " + e);
//            return null;
//        }
//    }
//}
