<div>
  <div>
    <h1>Boyer-Moore String Search Algorithm</h1>
    <p>
      These are Java programs implementing the Boyer-Moore string search algorithm, specifically for preparing and using a skip table for pattern matching in a text file.
    </p>
  </div>
  <hr>
  <div>
    <h3>BMSearch.java:</h3>
    <ol>
      <li>Reads a skip table and a pattern from a file.</li>
      <li>Searches for the pattern in a given text file using the Boyer-Moore algorithm with the skip table.</li>
      <li>Outputs lines containing the pattern along with their line numbers.</li>
    </ol>
    <p>Key methods:</p>
    <ul>
      <li><b>main:</b> Reads the skip table and pattern, initializes BMSearch, and starts the search.</li>
      <li><b>readSkipTable:</b> Parses the skip table from a file.</li>
      <li><b>searchPattern:</b> Searches for the pattern in the text file.</li>
      <li><b>containsPattern:</b> Implements the Boyer-Moore search algorithm.</li>
      <li><b>returnString:</b> Extracts the pattern from the skip table file.</li>
    </ul>
  </div>
  <hr>
  <div>
    <h3>MakeBMTable.java:</h3>
    <ol>
      <li>Generates a Boyer-Moore skip table for a given pattern and writes it to a file.</li>
    </ol>
    <p>Key methods:</p>
    <ul>
      <li><b>main:</b> Validates input, builds the skip table, and writes it to a file.</li>
      <li><b>writeTable:</b> Writes the skip table to a file.</li>
      <li><b>calculateDistanceSkipped:</b> Calculates the skip distance for the Boyer-Moore algorithm.</li>
    </ul>
    <p>
      These programs together allow you to create a skip table for a pattern and use it to efficiently search for the pattern in a text file.
    </p>
    <p>
      This program was a collaboration with another software engineer with sections commented on which parts I did, and the other person did.
    </p>
  </div>
</div>
