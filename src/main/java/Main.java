public class Main {

    public static void main(String[] args) {

        //final RequestHandlerBasic requestHandler = new RequestHandlerBasic();
        //final RequestHandlerHttpClient requestHandler = new RequestHandlerHttpClient();
        final RequestHandlerUnirest requestHandler = new RequestHandlerUnirest();
        final OutputWriter outputWriter = new OutputWriter();
        final Mapper myJacksonMAPPER = MapperFactory.getJacksonMapper();
        //final JacksonMapper myJacksonMAPPER = new JacksonMapper();

        int run = new ApiRunner(requestHandler, outputWriter, myJacksonMAPPER).run(args);
        System.exit(run);

    }
}
