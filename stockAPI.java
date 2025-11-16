import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.*;

//currently gives out current price of apple's stock, update later
public class stockAPI { 

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String apiKey = "3debf4dac7cb46daa8351151d229dda7";

    //change later to return price, right now its void prints the data in json -> parse it and return a double.
    public static double getPrice(String symbol) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.twelvedata.com/price?symbol=" + symbol + "&apikey=" + apiKey))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            return json.get("price").getAsDouble();
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void main(String[] args) {
        System.out.println(getPrice("AAPL")); // test
    }
}