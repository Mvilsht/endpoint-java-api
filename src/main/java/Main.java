public class Main {

    public static void main(String[] args) {

        final RequestHandlerBasic requestHandler = new RequestHandlerBasic();
        final OutputWriter outputWriter = new OutputWriter();
        int run = new ApiRunner(requestHandler, outputWriter).run(args);
        System.exit(run);

    }
}
