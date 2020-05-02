package mtech.irs.ekgs.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;
import mtech.irs.ekgs.gcloud.DialogflowClient;
import mtech.irs.ekgs.util.AppContextUtils;
import mtech.irs.ekgs.util.GraphUtils;

@Data
public class AppStatus {

	private static final Logger logger = LoggerFactory.getLogger(AppStatus.class);
	
	private Boolean graphReady;
	
	private Boolean dialogflowConnected;
	
	public AppStatus load() {
		setGraphReady(checkGraphReady());
		setDialogflowConnected(checkDialogflowConnected());
		return this;
	}
	
	public static boolean checkGraphReady() {
		try {
			return GraphUtils.service()
					.query("MATCH(n) RETURN count(n) as r", null, "r", Long.class)
					.findFirst()
					.orElse(0l) > 0;
		}catch(Exception e) {
			logger.error("Graph service not connected", e);
			return false;
		}
	}
	
	public static boolean checkDialogflowConnected() {
		try {
			return AppContextUtils.getBean(DialogflowClient.class)
					.detectIntent("test connected", null) != null;
		}catch(Exception e) {
			logger.error("Dialogflow service not connected", e);
			return false;
		}
	}
}
