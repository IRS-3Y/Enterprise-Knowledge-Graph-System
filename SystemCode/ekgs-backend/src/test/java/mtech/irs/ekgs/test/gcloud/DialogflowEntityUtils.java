package mtech.irs.ekgs.test.gcloud;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import mtech.irs.ekgs.EkgsBackendApplication;
import mtech.irs.ekgs.util.GraphUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EkgsBackendApplication.class)
public class DialogflowEntityUtils {
	
	private static final String outputFolder = "/Users/tanshyi/Workspace/test/";
	
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void generateNodeNameEntityTypes() throws IOException {
		generateNodeNameEntityType("Technology");
		generateNodeNameEntityType("People");
		generateNodeNameEntityType("Process");
	}
	
	private void generateNodeNameEntityType(String label) throws IOException {
		// generate json from graph nodes
		final String name = label + "NodeName";
		DialogflowEntityType entityType = new DialogflowEntityType();
		entityType.setName(name);
		GraphUtils.findNodePropValues(label, "longName", null, false).forEach(n -> {
			entityType.addEntry(n, n.toLowerCase());
		});
		String json = objectMapper
				.writerWithDefaultPrettyPrinter()
				.writeValueAsString(entityType);
		
		// write json to file
		final String filename = outputFolder + name + "_" + UUID.randomUUID().toString() + ".json";
		File file = new File(filename);
		file.createNewFile();
		try(FileWriter writer = new FileWriter(file)){
			writer.write(json);
			writer.flush();
		}
	}
	
}
