import java.io.*;

// JAYDEN WOODS,


public class BMSearch {

    private final int[][] skip;

    public BMSearch(int[][] skip) {
        this.skip = skip;
    }
    /*
     *  Reads the skip table from a file,
     *  Reads the pattern from the skip file, removing asterisks and commas,
     *  Initializes BMSearch with the skip table and searches for the pattern in the text file
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java BMSearch <table_txt> <plain_text_file>");
        } else {
            String tableFilename = args[0];
            String textFilename = args[1];

            // Contains the pattern we're searching for
            String pattern = returnString(tableFilename);

            if (pattern == null || pattern.isEmpty()) {
                System.out.println("Error: The pattern is empty or the file is invalid.");
                return;
            }

            try {
                int[][] skipTable = readSkipTable(tableFilename);

                // Check if the skip table is empty
                if (skipTable == null) {
                    System.out.println("Error: The skip table file is empty or invalid.");
                    return;
                }

                BMSearch bmSearch = new BMSearch(skipTable);
                bmSearch.searchPattern(pattern, textFilename);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*
     * Reads the skip table into a 2D array from a file,
     * Handles '*' as -1 and parses integers
     */
    private static int[][] readSkipTable(String tableFilename) throws IOException {
        int[][] skipTable = new int[256][256];
        try (BufferedReader skipTableReader = new BufferedReader(new FileReader(tableFilename))) {
            String skipTableLine;
            int row = 0;
    
            // Skip the first line (pattern line)
            skipTableLine = skipTableReader.readLine();

            if (skipTableLine == null) {
                return null; // Return null if the file is empty
            }
            
            while ((skipTableLine = skipTableReader.readLine()) != null) {
                String[] parts = skipTableLine.split(",");
                int col = 0;
                for (int i = 1; i < parts.length; i++) {
                    String part = parts[i];
    
                    if (!part.isEmpty()) {
                        if (part.equals("*")) {
                            skipTable[row][col++] = -1;
                        } else {
                            try {
                                skipTable[row][col++] = Integer.parseInt(part.trim());
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid number format at row " + row + ", column " + col + ": " + part);
                            }
                        }
                    }
                }
                row++;
            }
        }
        return skipTable;
    }
    /*
     * Reads lines from the text file, checks if each line contains the pattern using
     * 'containsPattern', and writes matching lines to a temporary file.
     */
    public void searchPattern(String pattern, String filename) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int lineNumber = 1;
    
            while ((line = reader.readLine()) != null) {
                if (containsPattern(line, pattern)) {
                    System.out.println(lineNumber + ":  " + line + "\n");
                }
                lineNumber++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
     * Implements the boyer-Moore algorithm using the skip table to determine how far to jump on a mismatch,
     * uses the skip value from the table for mismatched characters.
     */
    public boolean containsPattern(String text, String pattern) {
        int p = pattern.length();
        int t = text.length();
        int i = 0;
    
        while (i <= t - p) {
            int j = p - 1;
            while (j >= 0 && pattern.charAt(j) == text.charAt(i + j)) {
                j--;
            }
            if (j < 0) {
                return true; // Pattern found
            } else {
                // Use the skip array to determine how far to jump
                char mismatchChar = text.charAt(i + j);
                int skipValue = skip[mismatchChar][j];
                if (skipValue == -1) {
                    i += 1; // Handle cases where skip value is -1 (e.g., '*')
                } else {
                    i += Math.max(1, skipValue);
                }
            }
        }
        return false; // Pattern not found
    }
    /*
     * Reads the first line of the skip table to extract the pattern, removing '*' and commas.
     */
    public static String returnString(String tableFilename) {
        String stringValue = "";
        try (BufferedReader br = new BufferedReader(new FileReader(tableFilename))) {
            if ((stringValue = br.readLine()) != null) {
                stringValue = stringValue.replaceAll("[*,]", "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringValue;
    }
}