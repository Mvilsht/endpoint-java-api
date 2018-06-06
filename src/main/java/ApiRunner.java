import org.apache.commons.validator.routines.UrlValidator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ApiRunner {

    private final static String ANSWERS_CAT = "/api/v1/categories";
    private final static String ANSWERS_DEF_PROTOCOL = "http://";
    private final static String ANSWERS_DEF_LOCALE = "en";
    private final static List<String> SUPPORTED_LOCALE = Arrays.asList("en","es","fr","pt","de","it","ru","ja","ko");

    private final RequestHandler requestHandler;
    private final OutputWriter outputWriter;

    public ApiRunner(RequestHandler requestHandler, OutputWriter outputWriter) {
        this.requestHandler = requestHandler;
        this.outputWriter = outputWriter;
    }

    public int run(String[] args) {
        // args - answers_url(i.e - wix.wixanswers.com), reqLocale(en|es|fr|pt|de|it|ru|ja|ko),
        // outFormat(html|json), destDir(local dir to store responses)
        // Http GET -  https://wix.wixanswers.com/api/v1/categories?locale=en

        if(!validataArgs(args)) {
            System.err.println("sdfdfsdf");
            return 1;
        }

        final String host = args[0];
        final String locale = args[1];
        final String outputFormat = args[2];
        final String targetDir = args[3];
        final List<Category> categories;
        try {
            categories = requestHandler.sendRequest(new URL("https://" + host + "/api/v1/categories?locale=" + locale), outputFormat, targetDir);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return 1;
        }
        if (categories == null) {
             System.err.println("MAIN FAILED TO SEND");
             return 1;
         }

        try {
            outputWriter.writeFile(outputFormat, targetDir, categories);
        } catch (IOException e) {
            System.out.println("failed to write response: " + e);
            return 1;
        }
        System.out.println("successful");

        return 0;
    }

    private static boolean validataArgs(String[] args) {
        if(args.length < 4) {
            System.err.println("Invalid input. too few input params");
            return false;
        }

        //http://help.wixanswers.com/api/v1/categories?locale=en || https://wix.wixanswers.com/api/v1/categories?locale=en
        if(!SUPPORTED_LOCALE.contains(args[1])){
            System.err.println("Invalid locale. locale not supported");
            return false;
        }


        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (urlValidator.isValid(ANSWERS_DEF_PROTOCOL + args[0] + ANSWERS_CAT)) {
            System.out.println("url is valid");
        } else {
            System.out.println("url is invalid");
            return false;
        }

        return true;
    }
}
