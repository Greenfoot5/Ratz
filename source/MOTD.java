import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class MOTD {
    public static void main(String[] args) throws IOException {
        System.out.println(getPuzzle());
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

    public static String solvePuzzle(String puzzleInput) {
        char[] puzzleChars = puzzleInput.toCharArray();

        for (int i = 0; i < puzzleChars.length; i++) {
            int position = ;
            if (position % 2 == 1)
            {
                puzzleChars[i] = alphabet.get(5);
            }
        }
        return Arrays.toString(puzzleChars) + "CS-230";
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
