import java.net.URL;
import java.util.List;

public interface RequestHandler {

    List<Category> sendRequest(URL url, String outputFormat, String targetDir);

}
