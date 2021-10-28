package handler;

import com.sun.net.httpserver.HttpExchange;
import service.result.Result;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Locale;

/**
 * A class of useful functions for handlers
 */
public final class HttpUtil {
    // Shouldn't be instantiated.
    private HttpUtil(){}

    /**
     * Read all the input from a stream and return it as a String.
     * @param is the input stream to be read.
     * @return the String with all the input from the input stream.
     */
    public static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /**
     * Read all the input from a reader and return it as a String.
     * @param r the reader to be read.
     * @return the String with all the input from the input stream.
     */
    public static String readString(Reader r) throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[1024];
        int len;
        while ((len = r.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /**
     * Checks whether the request used a certain method.
     * @param exchange HttpExchange to check.
     * @param method Http method to look for.
     * @return whether the exchange used the specified method.
     */
    public static boolean usedMethod(HttpExchange exchange, String method) {
        return exchange.getRequestMethod().toLowerCase(Locale.ROOT).equals(method.toLowerCase(Locale.ROOT));
    }

    /**
     * Writes an object to an HttpExchange response body.
     * @param result the object to write to the response body.
     * @param exchange the exchange to write to.
     */
    public static void writeSuccessfulResult(Result result, HttpExchange exchange) throws IOException {
        if (result.isSuccess())
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        else
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
        JSONHandler.objectToJsonWriter(result, resBody);
        resBody.close();
    }

    /**
     * Responds to Http Bad Requests by sending the response headers and closing.
     * @param exchange the exchange to respond to.
     */
    public static void handleBadMethod(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        exchange.getResponseBody().close();
    }

    /**
     * Reports to the exchange there was a server error.
     * @param exchange the exchange to respond to.
     */
    public static void handleServerError(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
        exchange.getResponseBody().close();
    }

    /**
     * Gets the authorization ID from the exchange request headers.
     * @param exchange the exchange from which to grab the authorization ID.
     * @return the authorization ID.
     */
    public static String getAuthorization(HttpExchange exchange) throws IOException {
        return exchange.getRequestHeaders().get("authorization").get(0);
    }
}
