package docmanagement;

interface Importer {
    Document importFile(File file) throws IOException;
}