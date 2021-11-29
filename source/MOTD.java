import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MOTD {
    public static void main(String[] args) throws IOException {
        String puzzle = getPuzzle();
        puzzle = solvePuzzle(puzzle);
        System.out.println(postSolution(puzzle));
    }

    /**
     * Uses a HTTP GET request to obtain the puzzle
     * @return The puzzle to solve
     * @throws IOException There was an issue with sending the request
     */
    public static String getPuzzle() throws IOException {
        // Url to send it to
        String urlString = "http://cswebcat.swansea.ac.uk/puzzle";
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return reader.readLine();
    }

    /**
     * Solves the puzzle according to the puzzle specification
     * @param puzzleInput The input from the puzzle
     * @return The solved puzzle
     */
    public static String solvePuzzle(String puzzleInput) {
        // Covert the input into it's ascii values
        char[] puzzleArray = puzzleInput.toCharArray();
        int[] puzzleNumbers = new int[puzzleArray.length];
        for (int i = 0; i < puzzleArray.length; i++) {
            puzzleNumbers[i] = puzzleArray[i];
        }

        // Translate the characters one forward, two backward, etc.
        int base = 'A';
        for (int i = 0; i < puzzleNumbers.length; i++)
        {
            puzzleNumbers[i] -= base;
            switch(i % 2)
            {
                // Translate forwards
                case 1:
                    puzzleNumbers[i] += (i + 1);
                    while (puzzleNumbers[i] >= 26) {
                        puzzleNumbers[i] -= 26;
                    }
                    break;
                // Translate backwards
                case 0:
                    puzzleNumbers[i] -= (i + 1);
                    while (puzzleNumbers[i] < 0) {
                        puzzleNumbers[i] += 26;
                    }
                    break;
            }
            puzzleNumbers[i] += base;
        }

        // Convert back to characters
        StringBuilder output = new StringBuilder();
        for (int puzzleNumber : puzzleNumbers) {
            output.append((char) puzzleNumber);
        }
        output.append("CS-230");

        return Integer.toString(output.length()) + output;
    }

    public static String postSolution(String solution) throws IOException {
        String solutionUrl = "http://cswebcat.swansea.ac.uk/message?solution=" + solution;
        URL url = new URL(solutionUrl);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return reader.readLine();
    }
}
