import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ApiRunner {

    private final static List<String> SUPPORTED_HOSTS = Arrays.asList("wix.wixanswers.com","help.wixanswers.com");
    private final static List<String> SUPPORTED_LOCALES = Arrays.asList("en","es","fr","pt","de","it","ru","ja","ko");
    private final static List<String> SUPPORTED_FILE_FORMATS = Arrays.asList("html","json");
    private final static String CAT_CONTEXT = "/api/v1/categories";
    private final static List<String> SUPPORTED_PROTOCOLS = Arrays.asList("https://"); //first index is default
    //todo handle locale not available in only some locale - what to return when requested locale legal but not available
    private final RequestHandler requestHandler;
    private final OutputWriter outputWriter;
    private final Mapper mapper;


    public ApiRunner(RequestHandler requestHandler, OutputWriter outputWriter, Mapper mapper) {
        this.requestHandler = requestHandler;
        this.outputWriter = outputWriter;
        this.mapper = mapper;
    }

    public int run(String[] args) {
        // args - answers_url(i.e - wix.wixanswers.com), reqLocale(en|es|fr|pt|de|it|ru|ja|ko),
        // outFormat(html|json), destDir(local dir to store responses)
        // Http GET -  https://wix.wixanswers.com/api/v1/categories?locale=en

        Validator validator = new Validator(SUPPORTED_HOSTS, SUPPORTED_LOCALES, SUPPORTED_FILE_FORMATS);

        if(!validator.validateArgs(args)) {
            System.err.println("Invalid args. Failed validation with following args: " + Arrays.toString(args));
            return 1;
        }

        final String host = args[0];
        final String locale = args[1].toLowerCase();
        final String outputFormat = args[2].toLowerCase();
        final String targetDir = args[3];

        //List<Category> categories = null;

        final URL url;
        try {
            url = new URL(SUPPORTED_PROTOCOLS.get(0) + host + CAT_CONTEXT +"?locale=" + locale);
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL format requested: " + e.getMessage() ); //should not happened
            return 1;
        }

        final String response;
        try {
            //categories = requestHandler.sendRequest(url);
            response = requestHandler.sendRequest(url);
        } catch (Exception e) {
            System.err.println("Failed to get response");
            e.printStackTrace();
            return 1;
        }

        if (response == null) {
             System.err.println("Invalid response, String/categories are expected !!!!");
             return 1;
        }

        //MAP TO JSON OR HTML response to categories
//        final ObjectMapper MAPPER = new ObjectMapper();
//        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        System.out.println("---");
        System.out.println(response);
        System.out.println("---");

        final List<Category> categories;
        try {
             categories = Arrays.asList(mapper.readValue(response, Category[].class));
        } catch (IOException e) {
            System.err.println("Failed to map response");
            e.printStackTrace();
            return 1;
        }

        try {
            outputWriter.writeFile(outputFormat, targetDir, categories, mapper);
        } catch (IOException e) {
            System.err.println("Failed writing to file");
            e.printStackTrace();
            return 1;
        }

        System.out.println("successful");
        return 0;
    }
}