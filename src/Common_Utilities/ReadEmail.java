package Common_Utilities;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.UserCredentials;
import com.google.auth.http.HttpCredentialsAdapter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.io.FileInputStream;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

public class ReadEmail {

	private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String USER_ID = "me";

	public static String fetchEmailBody() throws IOException, GeneralSecurityException {
		// Load client secrets.
		GoogleCredentials credentials = loadCredentials();

		// Build a new authorized API client service.
		HttpTransport httpTransport = new NetHttpTransport();
		Gmail service = new Gmail.Builder(httpTransport, JSON_FACTORY, new HttpCredentialsAdapter(credentials))
				.setApplicationName(APPLICATION_NAME).build();

		// Retrieve messages
		ListMessagesResponse messagesResponse = service.users().messages().list(USER_ID).execute();
		List<Message> messages = messagesResponse.getMessages();

		String messagebody = "";
		if (messages == null || messages.isEmpty()) {
			System.out.println("No messages found.");
		} else {
			System.out.println("Messages:");
			for (Message message : messages) {
				Message fullMessage = service.users().messages().get(USER_ID, message.getId()).execute();
				System.out.printf("- Message ID: %s\n", fullMessage.getId());
				System.out.printf("  Snippet: %s\n", fullMessage.getSnippet());
			}
		}
		return messagebody;

	}

	private static GoogleCredentials loadCredentials() throws IOException {
		// Load your credentials from the JSON key file downloaded from the Google
		// Developer Console.
		FileInputStream credentialsStream = new FileInputStream("path/to/your/credentials.json");
		return UserCredentials.fromStream(credentialsStream);
	}

}
