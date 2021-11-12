import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MOTD {
    public static void main(String[] args) throws IOException {
        System.out.println(GetMOTD());
    }

    public static String GetMOTD() throws IOException {
        String urlString = "http://cswebcat.swansea.ac.uk/puzzle";
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return reader.readLine();
    }
}
