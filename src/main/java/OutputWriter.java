import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class OutputWriter {

    void writeFile(String outputFormat, String targetDir, List<Category> categories, Mapper mapper) throws IOException {
        final Path path = Paths.get(targetDir + "/outputFile." + outputFormat);

        if (outputFormat.equals("json")) {
            writeJsonFile(path, categories, mapper);
        } else { //html
            writeHtmlFile(path, categories
                    .stream()
                    .map(Category::getName)
                    .collect(Collectors.toList())
            );
        }
    }
    //TODO make name to be link to category public html
    private void writeHtmlFile(Path path, List<String> names) throws IOException {
        StringJoiner stringJoiner = new StringJoiner("\n\n");
        for (String name : names) {
            stringJoiner.add(name);
        }

        Files.write(path, stringJoiner.toString().getBytes(), StandardOpenOption.CREATE);
    }

    private void writeJsonFile(Path path, List<Category> categories, Mapper mapper) throws IOException {
        final String categoriesAsJson = mapper.writeValueAsString(categories);
        Files.write(path, categoriesAsJson.getBytes(), StandardOpenOption.CREATE);
    }
}