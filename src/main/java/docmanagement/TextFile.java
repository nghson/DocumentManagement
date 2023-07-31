package docmanagement;

import java.util.Map;
import java.util.List;
import java.util.function.Predicate;

class TextFile {
    private final Map<String, String> attributes;
    private final List<String> lines;

    int addLines(
            final int start,
            final Predicate<String> isEnd,
            final String attributeName
            ) {
        final StringBuilder accumulator = new StringBuilder();
        int lineNumber;
        for (lineNumber = start; lineNumber < lines.size(); lineNumber++) {
            final String line = lines.get(lineNumber);
            if (isEnd.test(line)) {
                break;
            }

            accumulator.append(line);
            accumulator.append("\n");
        }
        attributes.put(attributeName, accumulator.toString().trim());
        return lineNumber;
    }
}
