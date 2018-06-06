import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class RequestHandlerBasic implements RequestHandler {

    private final static ObjectMapper MAPPER = new ObjectMapper();
    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<Category> sendRequest(URL url, String outputFormat, String targetDir) {

        try {
            //List<Category> categories;
            final HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                String currLine;
                StringBuilder stringBuilder = new StringBuilder();
                while ((currLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(currLine);
                }

                if(urlConnection.getResponseCode() != 200){
                    System.err.println("BAD response status: " + urlConnection.getResponseCode());
                    return null;
                }

                return Arrays.asList(MAPPER.readValue(stringBuilder.toString(), Category[].class));
            }
        } catch (Exception e) {
            System.out.println("failed to send req: " + e);
            return null;
        }
    }
}
