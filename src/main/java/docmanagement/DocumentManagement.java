package docmanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DocumentManagement {

    private final List<Document> documents = new ArrayList<>();
    private final List<Document> documentsView = Collections.unmodifiableList(documents);
    private final Map<String, Importer> ext2imp = new HashMap<>();

    public DocumentManagement() {
        ext2imp.put("letter", new LetterImporter());
        ext2imp.put("image", new ImageImporter());
        ext2imp.put("report", new ReportImporter());
        ext2imp.put("invoice", new InvoiceImporter());
    }

    public void importFile(String path) throws IOException {
        final File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException(path);
        }

        final int sepInd = path.lastIndexOf('.');
        if (sepInd == -1 || sepInd == path.length()) {
            throw new UnknownFileTypeException("No extension found for " + path);
        }
        final String ext = path.substring(sepInd + 1);
        final Importer importer = ext2imp.get(ext);
        if (importer == null) {
            throw new UnknownFileTypeException("No extension found for " + path);
        }
        final Document document = importer.importFile(file);
        documents.add(document);
    }

    public List<Document> contents() {
        return documentsView;
    }

    public List<Document> search(final String query) {
        return documents.stream()
                .filter(Query.parse(query))
                .collect(Collectors.toList());
    }
}