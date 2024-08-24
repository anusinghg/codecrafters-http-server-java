package common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FileReaderUtil {
    private final Path path;

    /**
     * Initializes the FileReader with the file path.
     *
     * @param filePath the path to the file
     */
    public FileReaderUtil(String filePath) {
        this.path = Paths.get(filePath);
    }

    /**
     * Reads the entire content of the file as a single string.
     *
     * @return the content of the file as a string
     * @throws IOException if an I/O error occurs reading from the file
     */
    public String readFileAsString() throws IOException {
        return new String(Files.readAllBytes(this.path));
    }

    /**
     * Reads the content of the file as a list of strings, where each string is a line in the file.
     *
     * @return a list of strings, where each string is a line in the file
     * @throws IOException if an I/O error occurs reading from the file
     */
    public List<String> readFileAsLines() throws IOException {
        return Files.readAllLines(this.path);
    }

    /**
     * Reads the content of the file as a byte array.
     *
     * @return the content of the file as a byte array
     * @throws IOException if an I/O error occurs reading from the file
     */
    public byte[] readFileAsBytes() throws IOException {
        return Files.readAllBytes(this.path);
    }


    /**
     * Reads the content of the file as a Stream.
     *
     * @return the number of words in the file
     * @throws IOException if an I/O error occurs reading from the file
     */
    public Stream<String> readFileAsStream() throws IOException {
        return Files.lines(this.path);

    }

    /**
     * Gets the size of the file in bytes.
     *
     * @return the size of the file in bytes
     * @throws IOException if an I/O error occurs reading from the file
     */
    public long getFileSize() throws IOException {
        return Files.size(this.path);
    }

    /**
     * Counts the number of lines in the file.
     *
     * @return the number of lines in the file
     * @throws IOException if an I/O error occurs reading from the file
     */
    public long countLines() throws IOException {
        return Files.lines(this.path).count();
    }

    /**
     * Counts the number of characters in the file.
     *
     * @return the number of characters in the file
     * @throws IOException if an I/O error occurs reading from the file
     */
    public long countCharacters() throws IOException {
        return Files.lines(this.path)
                .mapToLong(String::length)
                .sum();
    }

    /**
     * Counts the number of words in the file.
     *
     * @return the number of words in the file
     * @throws IOException if an I/O error occurs reading from the file
     */
    public long countWords() throws IOException {
        return Files.lines(this.path)
                .flatMap(line -> Arrays.stream(line.trim().split("\\s+")))
                .count();
    }
}
