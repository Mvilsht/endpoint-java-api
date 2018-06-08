import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Validator {

    private final List<String> hosts;
    private final List<String> locales;
    private final List<String> fileFormats;

    public Validator(List<String> hosts, List<String> locales, List<String> fileFormats) {
        this.hosts = hosts;
        this.locales = locales;
        this.fileFormats = fileFormats;
    }

    boolean validateArgs(String[] args) {
        //http://help.wixanswers.com/api/v1/categories?locale=en || https://wix.wixanswers.com/api/v1/categories?locale=en
        if (args.length != 4) {
            System.err.println("Invalid input. wrong number of input params");
            return false;
        }

        if (!hosts.contains(args[0])) {
            System.err.format("Invalid answers host. %s not supported\n",args[0]);
            return false;
        }

        if (!locales.contains(args[1].toLowerCase())) {  //locales both LOWERCASE & UPPERCASE considered valid
            System.err.format("Invalid locale. %s not supported\n",args[1]);
            return false;
        }

        if (!fileFormats.contains(args[2].toLowerCase())) {  //fileFormats both LOWERCASE & UPPERCASE considered valid
            System.err.format("Invalid file format. %s not supported\n ",args[2]);
            return false;
        }

        if(!Paths.get(args[3]).toFile().isDirectory()) {    //target dir must exist and be directory
            System.err.format("Invalid target directory. %s not accessible directory\n", args[3]);
            return false;
        }


//        if(protocols == null || protocols.isEmpty()) {
//            System.err.println("NO supported protocols.");
//            return false;
//        }


        return true;
    }
}