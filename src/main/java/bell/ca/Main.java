package bell.ca;

import java.io.File;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;

public class Main {
	// Base URI the Grizzly HTTP server will listen on
	public static final String BASE_URI = "http://localhost:8082/myapp/";

	/**
	 * Main method.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException {

		String username = "858465bd-555e-4a4b-9ed4-5a15ba5fdd0a";
		String password = "pPcbE1hs0aTx";
		SpeechResults output = null;
		SpeechToText service = new SpeechToText();
		service.setUsernameAndPassword(username, password);

		RecognizeOptions options = new RecognizeOptions.Builder().contentType("audio/flac").timestamps(true)
				.wordAlternativesThreshold(0.9).keywords(new String[] { "colorado", "tornado", "tornadoes" })
				.keywordsThreshold(0.5).build();

		String[] files = { "/Users/sarra/audio-file.flac", "/Users/sarra/audio-file.flac" };
		for (String file : files) {
			output = service.recognize(new File(file), options).execute();
			System.out.println(output);
			System.out.println("Server response .... \n");
			JSONParser parser = new JSONParser();

			// JSON-SIMPLE
			Object obj = parser.parse(output.toString());
			JSONObject jsonObject = (JSONObject) obj;
			// System.out.println(jsonObject);

			JSONArray results = (JSONArray) jsonObject.get("results");

			for (Object rs : results) {
				JSONObject jsonAlternatives = (JSONObject) rs;
				JSONArray alternatives = (JSONArray) jsonAlternatives.get("alternatives");
				for (Object tr : alternatives) {
					JSONObject transcript = (JSONObject) tr;
					System.out.println(transcript.get("transcript"));
				}
			}
		}

	}
}
