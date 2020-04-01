package mtech.irs.ekgs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mtech.irs.ekgs.model.SearchInput;
import mtech.irs.ekgs.model.SearchResults;
import mtech.irs.ekgs.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	private SearchService service;

	@PostMapping
	public SearchResults search(@RequestBody SearchInput input) {
		return service.search(input);
	}
}
