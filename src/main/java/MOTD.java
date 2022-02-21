import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * A class to obtain the motd.
 * It does this by getting the puzzle, solving it and
 * posting the solution through http requests to get the motd.
 *
 * @author harvey
 * @version 1.0
 */
public class MOTD {

    public static final int ALPHABET_LENGTH = 26;

    /**
     * A public method to return the MOTD for the day
     *
     * @return The Message of the Day, or null if the motd is incorrectly solved
     */
    public static String GETMotd() {
        MOTD messageOfTheDay = new MOTD();
        try {
            String puzzle = messageOfTheDay.getPuzzle();
            puzzle = messageOfTheDay.solvePuzzle(puzzle);
            return messageOfTheDay.postSolution(puzzle);
        }
        catch (IOException e) {
            System.out.println("Error with solving MOTD puzzle");
            return null;
        }
    }

    /**
     * Uses a HTTP GET request to obtain the puzzle
     *
     * @return The puzzle to solve
     * @throws IOException There was an issue with sending the request
     */
    private String getPuzzle() throws IOException {
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
     *
     * @param puzzleInput The input from the puzzle
     * @return The solved puzzle
     */
    private String solvePuzzle(String puzzleInput) {
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
                    while (puzzleNumbers[i] >= ALPHABET_LENGTH) {
                        puzzleNumbers[i] -= ALPHABET_LENGTH;
                    }
                    break;
                // Translate backwards
                case 0:
                    puzzleNumbers[i] -= (i + 1);
                    while (puzzleNumbers[i] < 0) {
                        puzzleNumbers[i] += ALPHABET_LENGTH;
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

    private String postSolution(String solution) throws IOException {
        String solutionUrl = "http://cswebcat.swansea.ac.uk/message" +
                "?solution=" + solution;
        URL url = new URL(solutionUrl);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return reader.readLine();
    }
}
