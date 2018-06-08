import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class RequestHandlerBasic implements RequestHandler {

    private final static int MAX_REQ_TIMEOUT= 5000;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    //public List<Category> sendRequest(URL url) throws Exception{
    public String sendRequest(URL url) throws IOException{
        //try {
            List<Category> categories;
            final HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(MAX_REQ_TIMEOUT);
            urlConnection.setReadTimeout(MAX_REQ_TIMEOUT);

            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"))) {
                String currLine;
                while ((currLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(currLine);
                }

                //TODO see what returned when specific locale not supported and maybe return specific msg to user or retry handler
                if(urlConnection.getResponseCode() != 200){
                    System.err.println("Invalid response code: " + urlConnection.getResponseCode());
                    return null;
                }

                return stringBuilder.toString();
                //return Arrays.asList(MAPPER.readValue(stringBuilder.toString(), Category[].class));
            }
//        } catch (Exception e) {
//            System.err.format("failed connecting to url: %s\n with ex:%s\n ", url, e.getMessage());
//            return null;
//        }
    }
}
