import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// Mahaki Leach


class MakeBMTable{
  public static void main(String[] args){
    // Input validation
    if(args.length != 2 || !(args[0] instanceof String)
    || !(args[1] instanceof String) ){
      System.err.println("Usage: MakeBMTable <pattern> <filename>");
      return;
    }

    if(args[0].trim().compareTo("") == 0){
      System.err.println("Error: Cannot have blank pattern");
      return;
    }

    // Looping through string and adding to unique dictionary

    Set<String> set = new HashSet<String>();
    for (int i = 0; i < args[0].length(); i++) {
      char c = args[0].charAt(i);
      set.add(c + "");
    }

    // Sorting dictionary
    Set<String> sortedSet = new TreeSet<>(set);

    int width = args[0].length() + 1;
    int height = sortedSet.size() + 2;

    // Initialising multidimensional array
    String[][] output = new String[height][width];

    // Adding top row
    output[0][0] = "*";
    for (int i = 0; i < args[0].length(); i++) {
      String c = args[0].charAt(i) + "";
      output[0][i+1] = c;
    }

    // Adding first column
    int counter = 1;
    for (String c : sortedSet) {
      output[counter][0] = c + "";
      counter++;
    }
    output[counter][0] = "*";

    // Starting from second column
    for(int j = 1; j < height; j++){
      // Starting from end of row loops through
      for(int i = width-1; i > 0; i--){
        // output[j][i] = current position
        // Checking if current char is the one looking for
        String currChar = output[j][0];
        String charMatch = output[0][i];
        if (currChar == charMatch){
            output[j][i] = "0";
        }else{
          // Need to calculate distance skipped
          int res = calculateDistanceSkipped(args[0], i, currChar);
          output[j][i] = Integer.toString(res);
        }
      }
    }
    writeTable(output, args[1]);
  }

  public static void writeTable(String[][] in, String filename){
    /*
     * Outputs two dimensional array into a well formatted output
     */
    try (BufferedWriter writer = new BufferedWriter(
      new FileWriter(filename))) {
      for (int i = 0; i < in.length; i++) {
          for (int j = 0; j < in[0].length; j++) {
              writer.write(in[i][j]);
              if (j + 1 != in[0].length) {
                  writer.write(",");
              }
          }
          writer.newLine();
      }
      System.out.println("Data has been written to file successfully.");
    } catch (IOException e) {
      System.err.println("Error writing to file: " + e.getMessage());
      return;
    }
  }

  public static int calculateDistanceSkipped(
    String currentString,
    int position,
    String swappedChar){
    /*
    * Method calculates the next nearest match
    * based on the currentString, and swapped character
    */

    // Creating string segment to match
    String stringSegment = swappedChar + currentString.substring(position);

    // Starting comparison (so segment touches end) (not including first column)
    int start = currentString.length() - stringSegment.length();
    // Final possible ending position
    int end = stringSegment.length() * -1 + 1;

    for(int offset = start; offset >= end; offset--){
      // Check if there is a match
      // Looping through segment

      Boolean match = true;

      for(int i = 0; i < stringSegment.length();i++){
        // Calculating offset position
        int newPosition = offset+i;
        if(newPosition >= 0){
          if(currentString.charAt(newPosition) != stringSegment.charAt(i)){
            match = false;
          }
        }
      }

      if(match){
        return start - offset;
      }
    }
    return currentString.length();
  }
}