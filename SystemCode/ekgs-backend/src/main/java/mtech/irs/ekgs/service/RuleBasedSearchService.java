package mtech.irs.ekgs.service;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mtech.irs.ekgs.gcloud.DialogflowClient;
import mtech.irs.ekgs.kie.KieSystem;
import mtech.irs.ekgs.model.DialogAction;
import mtech.irs.ekgs.model.SearchInput;
import mtech.irs.ekgs.model.SearchResults;

/**
 * Implements {@link SearchService} which submits user inputs as facts to underlying rule engine.
 * Search results are generated based on various rules configured in knowledge base.
 * 
 * @author tanshyi
 */
@Service
public class RuleBasedSearchService implements SearchService{

	@Autowired
	private KieSystem kie;
	
	@Autowired
	private DialogflowClient dialogflow;
	
	@Override
	public SearchResults search(SearchInput input) {
		SearchResults results = new SearchResults();
		StatelessKieSession session = kie.newStatelessSession();
		session.execute(Arrays.asList(results, input));
		return results;
	}

	@Override
	public SearchResults searchDialog(SearchInput input) {
		SearchResults results = new SearchResults();
		if(StringUtils.isEmpty(input.getValue())) {
			return results;
		}
		DialogAction action = DialogAction.of(dialogflow.detectIntent(input.getValue(), null));
		StatelessKieSession session = kie.newStatelessSession();
		session.execute(Arrays.asList(results, action));
		return results;
	}

}
