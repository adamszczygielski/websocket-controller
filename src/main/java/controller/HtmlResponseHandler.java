package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class HtmlResponseHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String htmlPage = createHtmlPage();
        exchange.sendResponseHeaders(200, htmlPage.length());
        OutputStream os = exchange.getResponseBody();
        os.write(htmlPage.getBytes());
        os.close();
    }

    private String createHtmlPage() {
        InputStream inputStream = getFileAsStream("index.html");
        String fileContent = readFromStream(inputStream);
        return fileContent.replace("${port}", String.valueOf(Main.WEBSOCKET_SERVER_PORT));
    }

    private InputStream getFileAsStream(final String filename) {
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(filename);
        if (inputStream == null) {
            throw new IllegalArgumentException("File " + filename + " not found!");
        }
        return inputStream;
    }

    private String readFromStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        String line;
        try (InputStreamReader isr = new InputStreamReader(is); BufferedReader br = new BufferedReader(isr)) {
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "Could not read file content!";
        }
        return sb.toString();
    }
}
