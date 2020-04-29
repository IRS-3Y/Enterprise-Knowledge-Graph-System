package mtech.irs.ekgs.gcloud;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;

/**
 * Calling Google Dialogflow APIs
 * 
 * @author tanshyi
 */
@Service
public class DialogflowClient {
	
	private static final Logger logger = LoggerFactory.getLogger(DialogflowClient.class);

	private String projectId = "ekgs-default";
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public QueryResult detectIntent(String text, String sessionId) {
		final String languageCode = "en";
		
		if(StringUtils.isEmpty(sessionId)) {
			sessionId = UUID.randomUUID().toString();
		}
		// Instantiates a client
		try (SessionsClient sessionsClient = SessionsClient.create()) {
			
			// Set the session name using the sessionId (UUID) and projectID (my-project-id)
			SessionName session = SessionName.of(projectId, sessionId);
			logger.debug("Session Path: " + session.toString());

			// Set the text (hello) and language code (en-US) for the query
			TextInput.Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode(languageCode);

			// Build the query with the TextInput
			QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

			// Performs the detect intent request
			DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

			// Display the query result
			QueryResult result = response.getQueryResult();
			logger.debug("Query: {}, Intent: {} (confidence: {}, parameters: {}), Fulfillment: {}", 
					result.getQueryText(), result.getIntent().getDisplayName(), 
					result.getIntentDetectionConfidence(), result.getParameters().toString(),
					result.getFulfillmentText());

			return result;
			
		}catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
