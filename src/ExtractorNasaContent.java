import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtractorNasaContent implements ContentExtractor {
	
	public List<Content> extractContent(String json) {

		JsonParser parser = new JsonParser();
		List<Map<String, String>> attributeList = parser.parse(json);
		List<Content> contents = new ArrayList<Content>();

		attributeList.stream()
			.forEach(attribute ->
				contents.add(new Content(attribute.get("title"), attribute.get("url"))));
		return contents;
	}

}
