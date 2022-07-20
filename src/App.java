import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    
    public static String customReset = "\u001b[m";
    public static String customBold = "\u001b[1m";
    public static void main(String[] args) throws Exception {
        // fazer uma conexão HTTP e buscar os top 250 filmes
        String urlNasa = "https://api.mocki.io/v2/549a5d8b/NASA-APOD";
        String urlIMDB = "https://api.mocki.io/v2/549a5d8b/Top250Movies";

        var http = new ClientHttp();
        String jsonNasa = http.getData(urlNasa);
        String jsonIMDB = http.getData(urlIMDB);

        // Extrair só os dados que interessam (titutlo, poster, classificação)
        ContentExtractor nasaExtractor = new ExtractorNasaContent();
        ContentExtractor imdbExtractor = new ExtractorContentIMDB();
        List<Content> nasaContents = nasaExtractor.extractContent(jsonNasa);
        List<Content> imdbContents = imdbExtractor.extractContent(jsonIMDB);

        //exibir e manipular os dados
        var generateSticker = new StickersGenerate();
        printContent(generateSticker, nasaContents, 3);
        printContent(generateSticker, imdbContents, 5);
    }

    public static void printContent(StickersGenerate generate, List<Content> contents, int count) throws Exception {
        for (int i = 0; i < count; i++) {
            Content content = contents.get(i);
            InputStream inputStream = new URL(content.urlImage()).openStream();
            String fileName = "assets/output/" + content.title() + ".png";
            generate.make(inputStream, fileName);
            System.out.println("Título: " + customBold + content.title() + customReset);
            System.out.println("Poster: " + customBold + content.urlImage() + customReset);
            System.out.println("\n");
        }
    }

}
