package mtech.irs.ekgs.gcloud;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

/**
 * Mapping GOOGLE_APPLICATION_CREDENTIALS json file
 * 
 * @author tanshyi
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GoogleAppCredential {
	
	@JsonProperty("project_id")
	private String projectId;
	
	public static final String ENV_VAR = "GOOGLE_APPLICATION_CREDENTIALS";
	
	public static GoogleAppCredential load() {
		try {
			String jsonPath = System.getenv(ENV_VAR);
			Assert.hasLength(jsonPath, "Undefined environment property " + ENV_VAR);
			
			File jsonFile = new File(jsonPath);
			if(!jsonFile.isFile()) {
				throw new IllegalArgumentException("Credential file not found: " + jsonPath);
			}
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(jsonFile, GoogleAppCredential.class);
		} 
		catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
