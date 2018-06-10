import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;

public class RequestHandlerUnirest implements RequestHandler {

    public String sendRequest(URL url) throws Exception{

        HttpResponse<com.mashape.unirest.http.JsonNode> response;
        try {
             response = Unirest
                    .get(url.toString())
                    .header("Accept","application/json")
                    .asJson();
        } catch (UnirestException e) {
            System.err.println("Fatal Unirest sendRequest violation: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        if (response.getStatus() != 200) {
            System.err.println("BAD Unirest request. status: " + response.getStatus());
            return null;
        }
        else {
            return response.getBody().getArray().toString();
            //return Arrays.asList(httpResponse.getBody();
        }
    }
}
