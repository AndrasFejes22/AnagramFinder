package anagramFinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class AnagramFinderWithStreamAPI {

    public static void main(String[] args) throws IOException {

        // ötlet a letterstatistics helyett; abc sorrend, mert az is egyértelmű megfeleltetés:
        // [beér, bére, éber] ---> beér (betűk abc sorrendben, ezt nyilvan pl. a sorted() tudja majd)

        // beér -> ["beér", "bére", "éber"]

        System.out.println("Anagrammafiinder wit Stream API:");

        Files.lines(Paths.get("hungarian_words/magyar_szavak_listaja_hosszabb.txt"))
                .collect(Collectors.groupingBy(w -> Arrays.stream(w.split("")).sorted().collect(Collectors.joining()), Collectors.toUnmodifiableList()))
                .entrySet().stream().filter(e -> e.getValue().size() > 1)// csak a min 2 elemü listák maradhatnak, ui. azok anagrammák
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size())) // a nagyobb listák legynek elöl
                .map(e -> e.getValue()) // lemap-eljük csak az értékekre
                .forEach(System.out::println);

    }
}
