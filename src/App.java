import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {
        String customReset = "\u001b[m";
        String customBold = "\u001b[1m";
        String customBackground = "\u001b[97m\u001b[104m";
        // fazer uma conexão HTTP e buscar os top 250 filmes
        String url = getUrl();
        URI address = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(address).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // Extrair só os dados que interessam (titutlo, poster, classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> movieList = parser.parse(body);

        //exibir emanipular os dados
        String imdbRating;
        int rating;
        for (Map<String,String> movie : movieList) {
            System.out.println("Título: " + customBold + movie.get("title") + customReset);
            System.out.println("Poster: " + customBold + movie.get("image") + customReset);
            imdbRating = movie.get("imDbRating");
            rating = getImdbRating(imdbRating);
            System.out.println(customBackground + "Classificação: " + customBold + imdbRating + customReset);
            for (int i = 0; i < rating; i++) {
                System.out.print("⭐");
            }
            System.out.println("\n");
        }
    }

    private static int getImdbRating(String imdbRating) {
       return (int)Math.round(Double.parseDouble(imdbRating == "" ? "0" : imdbRating));
    }

    private static String getUrl() {
        String url = "";
        String apiKey = System.getenv("API_KEY");
        Scanner in = new Scanner(System.in);
        System.out.println("APIS Available:");
        System.out.println("1 - Top 250 Movies");
        System.out.println("2 - Top 250 Tvs");
        System.out.println("3 - Most Popular Movies");
        System.out.println("4 - Most Popular Tvs");
        System.out.print("> ");
        int option;
        do {
            option = in.nextInt();
            switch (option) {
                case 1:
                    url = "https://imdb-api.com/en/API/Top250Movies/" + apiKey;
                    break;
                case 2:
                    url = "https://imdb-api.com/en/API/Top250TVs/" + apiKey;
                    break;
                case 3:
                    url = "https://imdb-api.com/en/API/MostPopularMovies/" + apiKey;
                    break;
                case 4:
                    url = "https://imdb-api.com/en/API/MostPopularTVs/" + apiKey;

                default:
                    System.out.println("Wrong option!");
                    System.out.print("> ");
                    break;
            }
        }
        while (url.isEmpty());
        in.close();
        return url;
    }
}
